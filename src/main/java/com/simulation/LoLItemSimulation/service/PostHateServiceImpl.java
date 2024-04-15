package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.repository.PostHateRepository;
import org.springframework.stereotype.Service;

@Service
public class PostHateServiceImpl implements PostHateService {

    private final PostHateRepository postHateRepository;

    public PostHateServiceImpl(PostHateRepository postHateRepository){
        this.postHateRepository = postHateRepository;
    }

    @Override
    public boolean isPostHatedByIp(Long postId, String ipAddress) {
        return postHateRepository.existsByPostIdAndIpAddress(postId, ipAddress);
    }
}
