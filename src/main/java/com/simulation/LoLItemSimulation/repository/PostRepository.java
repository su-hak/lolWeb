package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// PostRepository.java
public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findAll(Pageable pageable);
} 