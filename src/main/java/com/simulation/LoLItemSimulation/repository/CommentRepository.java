package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// CommentRepository.java
public interface CommentRepository extends JpaRepository<Comment, Long> {
  // 필요한 쿼리 메서드 추가

  // 게시글 id로 댓글 리스트
  List<Comment> findByPostId(Long postId);

  // 총 댓글 리스트
  List<Comment> findAll();

  // 게시글의 총 댓글 수 조회
  @Query("SELECT COUNT(c) FROM Comment c WHERE c.post.id = :postId")
  int countCommentsByPostId(@Param("postId") Long postId);
}