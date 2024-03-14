package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// PostRepository.java
public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findAll(Pageable pageable);

  // 게시글 좋아요 갯수
  @Query("SELECT COUNT(p) FROM Post p")
  long countAllPosts();

  // 게시글 좋아요 갯수 반환
  @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.id = :postId")
  int getPostLikeCount(Long postId);
} 