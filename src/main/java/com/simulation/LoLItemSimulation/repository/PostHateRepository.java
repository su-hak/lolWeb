package com.simulation.LoLItemSimulation.repository;


import com.simulation.LoLItemSimulation.domain.PostHate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface PostHateRepository extends JpaRepository<PostHate, Long> {
    PostHate findByPostIdAndIpAddress(Long postId, String ipAddress);

    boolean existsByPostIdAndIpAddress(Long postId, String ipAddress);

    @Query("SELECT COUNT(pl) FROM PostHate pl WHERE pl.post.id = :postId")
    long countHatesByPostId(@Param("postId") Long postId);
}