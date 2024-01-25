package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// PostRepository.java
public interface PostRepository extends JpaRepository<Post, Long> {
  // 필요한 쿼리 메서드 추가
  List<Post> findAll();
}