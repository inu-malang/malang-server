package com.example.malang.domain;

import com.example.malang.config.BaseEntity;
import com.example.malang.dto.PostRequestDto;
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

//    @Embedded
//    private Address address;

    @OneToMany(mappedBy = "place")
    private List<Post> posts = new ArrayList<>();

    @Builder
    public Place(String name, String x, String y) {
        this.name = name;
        this.x = x;
        this.y = y;
    }

    public static Place from(PostRequestDto.PostRequest postRequest) {
        return Place.builder()
                .name(postRequest.getPlaceName())
                .x(postRequest.getX())
                .y(postRequest.getY())
                .build();
    }
}
