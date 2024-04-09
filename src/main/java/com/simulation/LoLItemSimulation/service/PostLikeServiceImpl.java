package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.repository.PostLikeRepository;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    private final PostLikeRepository postLikeRepository;

    public PostLikeServiceImpl(PostLikeRepository postLikeRepository) {
        this.postLikeRepository = postLikeRepository;
    }

    @Override
    public boolean isPostLikedByIp(Long postId, String ipAddress) {
        return postLikeRepository.existsByPostIdAndIpAddress(postId, ipAddress);
    }
}
