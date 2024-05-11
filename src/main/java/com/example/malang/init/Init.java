package com.example.malang.init;

import com.example.malang.domain.member.Member;
import com.example.malang.repository.MemberRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 개발중에 데이터가 잘 들어가는지 확인하려면
 * init() 메서드에 도메인 만들고 필요한 Repo 주입 받고 save() 하면 됩니다.
 */
@Component
@RequiredArgsConstructor
public class Init {

    private final InitData initData;

    @PostConstruct
    public void init() {
        initData.init();
    }

    @Component
    @RequiredArgsConstructor
    @Transactional
    static class InitData {
        private final MemberRepository memberRepository;

        public void init() {
            Member member = Member.builder()
                    .name("malang")
                    .username("malng@inu.ac.kr")
                    .build();
            memberRepository.save(member);
        }
    }
}
