package com.ganzithon.Hexfarming.domain.experience;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ExperienceRepository extends JpaRepository<Experience, Integer> {
    Optional<List<Experience>> findAllByUserId(int userId);
}
