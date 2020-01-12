package com.lhmai.funnytube.repository;

import com.lhmai.funnytube.domain.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
    Optional<MovieEntity> findByUrl(String youtubeUrl);
}
