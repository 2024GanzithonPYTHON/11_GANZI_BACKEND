package com.ganzithon.Hexfarming.domain.post;

import org.springframework.data.repository.CrudRepository;

import java.util.List;


public interface PostRepository extends CrudRepository<Post, Long> {
    @Override
    List<Post> findAll();
}
