package com.example.malang.service;

import com.example.malang.domain.member.Member;
import com.example.malang.dto.MemberRequestDto;
import com.example.malang.dto.MemberResponseDto;
import com.example.malang.oauth.common.TokenMapping;
import com.example.malang.oauth.service.JwtService;
import com.example.malang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;


    @Transactional
    public MemberResponseDto.LoginAuthenticationMember signUp(MemberRequestDto.OAuthLoginMember oAuthLoginMember) {
        Member member = memberRepository.save(Member.from(oAuthLoginMember));
        TokenMapping tokenMapping = getToken(member);
        return MemberResponseDto.LoginAuthenticationMember.from(tokenMapping,member.getId());
    }

    /**
     * 인증에 성공한 Member 에게는 JWT 를 만들어서 반환합니다.
     * 해당 서비스에서는 TokenMapping 를 반환합니다.
     */
    private TokenMapping getToken(Member member) {
        String email = member.getEmail();
        /**
         * createToken() 에서 반환된 TokenMapping 의 토큰은 모두 "Bearer " 의 포맷을 가진다.
         */
        TokenMapping token = jwtService.createToken(email);
        member.updateRefreshToken(token.getRefreshToken());
        return token;
    }
}
