package com.example.movie.movie.repository;

import com.example.movie.movie.entity.Genre;
import com.example.movie.movie.entity.RoleEntity;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.management.relation.Role;

@Repository
public interface RoleRepository extends CrudRepository<RoleEntity, Long> {
}
