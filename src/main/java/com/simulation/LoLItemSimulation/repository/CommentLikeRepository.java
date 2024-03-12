package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.CommentLike;
import com.simulation.LoLItemSimulation.domain.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
  Optional<CommentLike> findByIpAddressAndCommentId(String ipAddress, Long commentId);

  void deleteByIpAddressAndCommentId(String ipAddress, Long commentId);

  Optional<CommentLike> findById(CommentLikeId id);

  void deleteById(CommentLikeId commentLikeId);
  CommentLike findByCommentIdAndIpAddress(Long commentId, String ipAddress);

  @Query("SELECT cl FROM CommentLike cl WHERE cl.comment.post.id = :postId")
  List<CommentLike> findByCommentPostId(@Param("postId") Long postId);
}