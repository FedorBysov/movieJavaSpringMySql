package com.example.movie.movie.repository;

import com.example.movie.movie.entity.Genre;
import com.example.movie.movie.entity.UserEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserEntityRepository extends CrudRepository<UserEntity, Long> {


    Optional<UserEntity> findByUsername(String username);
    Optional<UserEntity> findByEmail(String email);
}
