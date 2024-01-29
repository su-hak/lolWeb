package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.CommentLike;
import com.simulation.LoLItemSimulation.domain.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
  Optional<CommentLike> findByIpAddressAndCommentId(String ipAddress, Long commentId);

  void deleteByIpAddressAndCommentId(String ipAddress, Long commentId);

  Optional<CommentLike> findById(CommentLikeId id);

  void deleteById(CommentLikeId commentLikeId);
  CommentLike findByCommentIdAndIpAddress(Long commentId, String ipAddress);
}