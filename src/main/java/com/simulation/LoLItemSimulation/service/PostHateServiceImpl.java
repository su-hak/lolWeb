package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.repository.PostHateRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PostHateServiceImpl implements PostLikeService {

    @Autowired
    private PostHateRepository postHateRepository;

    @Override
    public boolean isPostLikedByIp(Long postId, String ipAddress) {
        return postHateRepository.existsByPostIdAndIpAddress(postId, ipAddress);
    }
}
