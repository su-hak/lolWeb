package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.repository.CommentLikeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentLikeServiceImpl implements CommentLikeService {

    private final CommentLikeRepository commentLikeRepository;

    @Autowired
    public CommentLikeServiceImpl(CommentLikeRepository commentLikeRepository) {
        this.commentLikeRepository = commentLikeRepository;
    }

    @Override
    public boolean isCommentLikedByIp(Long commentId, String ipAddress) {
        return commentLikeRepository.existsByCommentIdAndIpAddress(commentId, ipAddress);
    }

}
