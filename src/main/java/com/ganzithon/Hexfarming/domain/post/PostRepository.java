package com.ganzithon.Hexfarming.domain.post;

import com.ganzithon.Hexfarming.global.enumeration.Ability;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface PostRepository extends CrudRepository<Post, Long> {
    @Override
    List<Post> findAll();

    Optional<List<Post>> findByTimerBeforeAndIsTimerOverFalse(LocalDateTime dateTime);
    Optional<List<Post>> findByTitleContaining(String titleContains);
    Optional<List<Post>> findByTitleContainingAndAbility(String titleContains, Ability ability);
}
