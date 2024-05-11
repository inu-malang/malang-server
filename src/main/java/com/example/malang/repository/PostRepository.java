package com.example.malang.repository;

import com.example.malang.domain.Place;
import com.example.malang.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByTitle(String title);

    List<Post> findByPlace(Place place);

    List<Post> findAllByPlaceName(String placeName);
}
