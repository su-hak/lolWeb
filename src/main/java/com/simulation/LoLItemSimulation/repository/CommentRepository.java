package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

// CommentRepository.java
public interface CommentRepository extends JpaRepository<Comment, Long> {
  // 필요한 쿼리 메서드 추가
  List<Comment> findByPostId(Long postId);
  List<Comment> findAll();
}