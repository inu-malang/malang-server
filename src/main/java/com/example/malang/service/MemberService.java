package com.example.malang.service;

import com.example.malang.domain.Post;
import com.example.malang.domain.member.Member;
import com.example.malang.dto.PostResponseDto;
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
import static com.example.malang.dto.PostResponseDto.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private static int NICKNAME_NUMBER = 1;


    @Transactional
    public LoginAuthenticationMember signUp(OauthLoginMember oAuthLoginMember) {
        Member member = memberRepository.save(Member.from(oAuthLoginMember,NICKNAME_NUMBER++));
        TokenMapping tokenMapping = getToken(member);
        return LoginAuthenticationMember.from(tokenMapping,member.getId());
    }

    public MyPage getMyPage(Long memberId) {
        Member member = findMemberFromMemberId(memberId);
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

    public PostDetailResponseDTO getMyPostFromMyPage(Long memberId) {
        // 내가 작성한 게시물 노출시키기
        Member member = findMemberFromMemberId(memberId);
        Post post = member.getPost();

        if (post == null) {
            // 작성한 글이 없다면 에러 발생
            throw new BaseException(ErrorCode.NOT_EXIST_WRITE_POST);
        }

        return new PostDetailResponseDTO(post);
    }

    /**
     * memberId 로 부터 member 찾는 로직 자주 사용하기 때문에 메서드로 추출
     */
    private Member findMemberFromMemberId(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_MEMBER));
    }
}
