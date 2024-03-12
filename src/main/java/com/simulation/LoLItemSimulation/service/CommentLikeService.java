package com.simulation.LoLItemSimulation.service;

public interface CommentLikeService {

    boolean isCommentLikedByIp(Long commentId, String ipAddress);

}