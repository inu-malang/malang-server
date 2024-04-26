package com.example.malang.service;

import com.example.malang.domain.member.Member;
import com.example.malang.dto.MemberResponseDto;
import com.example.malang.exception.BaseException;
import com.example.malang.exception.ErrorCode;
import com.example.malang.jwt.JwtService;
import com.example.malang.jwt.TokenMapping;
import com.example.malang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.example.malang.dto.MemberRequestDto.*;
import static com.example.malang.dto.MemberResponseDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;


    @Transactional
    public LoginAuthenticationMember signUp(OauthLoginMember oAuthLoginMember) {
        Member member = memberRepository.save(Member.from(oAuthLoginMember));
        TokenMapping tokenMapping = getToken(member);
        return LoginAuthenticationMember.from(tokenMapping,member.getId());
    }

    public MyPage getMyPage(Long memberId) {
        Member member = memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_MEMBER));
        return MyPage.to(member.getId() , member.getName() , member.getEmail());
    }

    /**
     * 인증에 성공한 Member 에게는 JWT 를 만들어서 반환합니다.
     * 해당 서비스에서는 TokenMapping 를 반환합니다.
     */
    private TokenMapping getToken(Member member) {
        String email = member.getEmail();
        /**
         * createToken() 에서 반환된 TokenMapping 의 토큰은 모두 "Bearer " 의 포맷을 가진다.
         * -------> 하지만 요청 헤더에 담아서 넣는게 아니니까 그냥 "TOKEN_VALUE" 형식으로 return
         */
        TokenMapping token = jwtService.createToken(email);
        member.updateRefreshToken(token.getRefreshToken());
        return token;
    }
}
