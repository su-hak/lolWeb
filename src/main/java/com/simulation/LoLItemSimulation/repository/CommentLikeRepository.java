package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.CommentLike;
import com.simulation.LoLItemSimulation.domain.CommentLikeId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface CommentLikeRepository extends JpaRepository<CommentLike, Long> {
    Optional<CommentLike> findByIpAddressAndCommentId(String ipAddress, Long commentId);

    void deleteByIpAddressAndCommentId(String ipAddress, Long commentId);

    Optional<CommentLike> findById(CommentLikeId id);

    void deleteById(CommentLikeId commentLikeId);

    // 특정 댓글(commentId)에 대한 좋아요 수 조회
    long countByCommentId(Long commentId);

    // 특정 댓글(commentId)에 대한 특정 IP 주소(ipAddress)의 좋아요 조회
    CommentLike findByCommentIdAndIpAddress(Long commentId, String ipAddress);

    @Query("SELECT COUNT(cl) FROM CommentLike cl WHERE cl.comment.id = :commentId")
    long countLikesByCommentId(@Param("commentId") Long commentId);


}
