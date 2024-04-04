package com.example.malang.dto;

import com.example.malang.domain.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
public class PlaceResponseDto {

    @Builder @Getter
    public static class PlaceResponse {
        private Long id;
        private String name;
        private String x;
        private String y;
        private String city;
        private String street;
        private String zipcode;

        public static PlaceResponse from(Place place) {
            return PlaceResponse.builder()
                    .id(place.getId())
                    .name(place.getName())
                    .x(place.getX())
                    .y(place.getY())
                    .city(place.getAddress().getCity())
                    .street(place.getAddress().getStreet())
                    .zipcode(place.getAddress().getZipcode())
                    .build();
        }
    }
}
