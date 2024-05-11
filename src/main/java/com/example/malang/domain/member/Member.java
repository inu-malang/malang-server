package com.example.malang.domain.member;

import com.example.malang.domain.ChatParticipation;
import com.example.malang.domain.Post;
import com.example.malang.domain.Request;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.example.malang.dto.MemberRequestDto.OauthLoginMember;
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

    private String name;

    private String username;

    private String password;

    private String role;

    @OneToOne(mappedBy = "member", fetch = LAZY)
    private Post post;

    @OneToMany(mappedBy = "member")
    private List<Request> requests = new ArrayList<>();

    @OneToMany(mappedBy = "member")
    private List<ChatParticipation> chatParticipationList = new ArrayList<>();

    public void setRole(String role) {
        this.role = role;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * 생성 메서드
     */
    public static Member createMember(String name, String username, String password) {
        Member member = new Member();
        member.name = name;
        member.username = username;
        member.password = password;
        member.role = "ROLE_ADMIN";

        return member;
    }
}
