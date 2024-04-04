package com.example.malang.domain;

import com.example.malang.config.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Place extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    private String name;

    private String x;

    private String y;

    @Embedded
    private Address address;

    @OneToMany(mappedBy = "place")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Place(String name, String x, String y, Address address) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.address = address;
    }
}
