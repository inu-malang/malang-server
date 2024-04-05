package com.example.malang.domain.member;

import com.example.malang.domain.ChatParticipation;
import com.example.malang.domain.Post;
import com.example.malang.domain.Request;
import com.example.malang.dto.MemberRequestDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import static com.example.malang.dto.MemberRequestDto.*;
import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter @Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;

    // main 화면 보여줄 닉네임
    private String nickname;

    private String name;

    private String email;

    @OneToOne(mappedBy = "member", fetch = LAZY)
    private Post post;

    @OneToMany(mappedBy = "member")
    private List<Request> requests = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ChatParticipation> chatParticipationList = new ArrayList<>();

    private String refreshToken;

    /**
     * 생성 메서드
     */
    public static Member from(OauthLoginMember oAuthLoginMember, int number) {
        return Member.builder()
                .email(oAuthLoginMember.getEmail())
                .name(oAuthLoginMember.getMemberName())
                .nickname("말랑 " + String.valueOf(number))
                .build();
    }

    public void updateRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken.replace("Bearer ","");
    }

}
