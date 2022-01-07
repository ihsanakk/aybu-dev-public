package com.example.aybudev.repo;

import com.example.aybudev.entity.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer>{


    User findByUsername(String username);

    boolean existsByUsername(String username);

}
