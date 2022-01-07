package com.example.aybudev.repo;

import java.util.Set;

import com.example.aybudev.entity.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Integer>{
    

    Set<Post> findByUser_Username(String username);

    Set<Post> findByPostAbout(String postAbout);

}
