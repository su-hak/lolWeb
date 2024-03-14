package com.simulation.LoLItemSimulation.service;

public interface PostLikeService {

    boolean isPostLikedByIp(Long postId, String ipAddress);

}
