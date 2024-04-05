package com.example.malang.domain;

import com.example.malang.config.base.BaseEntity;
import com.example.malang.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    private String title;

    private String content;

    private int age;

    private int maleMembers;

    private int femaleMembers;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    // TODO: 나이, 인원 수, 게시글 상태

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "place_id")
    private Place place;

    @OneToMany(mappedBy = "post")
    private List<Request> requests = new ArrayList<>();

    private String uploadFileName;

    private String storeFileName;

    @Builder
    public Post(String title, String content, Member member, Place place, String uploadFileName, String storeFileName, int age, int maleMembers, int femaleMembers) {
        this.title = title;
        this.content = content;
        this.member = member;
        this.place = place;
        this.uploadFileName = uploadFileName;
        this.storeFileName = storeFileName;
        this.age = age;
        this.maleMembers = maleMembers;
        this.femaleMembers = femaleMembers;
    }


}
