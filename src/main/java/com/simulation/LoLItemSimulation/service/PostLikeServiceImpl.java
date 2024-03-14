package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.repository.PostLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostLikeServiceImpl implements PostLikeService {

    @Autowired
    private PostLikeRepository postLikeRepository;

    @Override
    public boolean isPostLikedByIp(Long postId, String ipAddress) {
        return postLikeRepository.existsByPostIdAndIpAddress(postId, ipAddress);
    }
}
