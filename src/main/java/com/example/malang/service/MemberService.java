package com.example.malang.service;

import com.example.malang.domain.member.Member;
import com.example.malang.dto.JoinDTO;
import com.example.malang.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;


    @Transactional
    public void join(JoinDTO joinDTO) {

        // db에 이미 동일한 username을 가진 회원이 존재하는지?
        if (memberRepository.existsByUsername(joinDTO.getEmail())) {
            log.info("[join] 이미 회원가입한 회원");
            return;
        }

        String password = joinDTO.getName() + " " + joinDTO.getEmail();
        Member member = Member.createMember(joinDTO.getName(), joinDTO.getEmail(), bCryptPasswordEncoder.encode(password));
        log.info("[join] 회원가입 성공");
        memberRepository.save(member);
    }

//    public MyPage getMyPage(Long memberId) {
//        Member member = memberRepository.findById(memberId)
//                .orElseThrow(() -> new BaseException(ErrorCode.NOT_EXIST_MEMBER));
//        return MyPage.to(member.getId() , member.getName() , member.getEmail());
//    }
//
//    /**
//     * 인증에 성공한 Member 에게는 JWT 를 만들어서 반환합니다.
//     * 해당 서비스에서는 TokenMapping 를 반환합니다.
//     */
//    private TokenMapping getToken(Member member) {
//        String email = member.getEmail();
//        /**
//         * createToken() 에서 반환된 TokenMapping 의 토큰은 모두 "Bearer " 의 포맷을 가진다.
//         * -------> 하지만 요청 헤더에 담아서 넣는게 아니니까 그냥 "TOKEN_VALUE" 형식으로 return
//         */
//        TokenMapping token = jwtService.createToken(email);
//        member.updateRefreshToken(token.getRefreshToken());
//        return token;
//    }
}
