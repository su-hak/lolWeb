package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.PostLike;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PostLikeRepository extends JpaRepository<PostLike, Long> {
    PostLike findByPostIdAndIpAddress(Long postId, String ipAddress);
    List<PostLike> findByPostId(Long postId);

    boolean existsByPostIdAndIpAddress(Long postId, String ipAddress);

    @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.id = :postId")
    long countLikesByPostId(@Param("postId") Long postId);
}
