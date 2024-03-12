package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.CommentLike;
import com.simulation.LoLItemSimulation.domain.CommentLikeId;
import com.simulation.LoLItemSimulation.dto.CommentDto;
import com.simulation.LoLItemSimulation.repository.CommentLikeRepository;
import com.simulation.LoLItemSimulation.repository.CommentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

// CommentService.java
@Service
public class CommentService {
  private final CommentRepository commentRepository;
  private CommentLikeRepository commentLikeRepository;

  @Autowired
  public CommentService(CommentRepository commentRepository) {
    this.commentRepository = commentRepository;
  }

  public void saveComment(CommentDto commentDto, String ipAddress) {
    // CommentDto를 Comment 엔터티로 변환
    Comment comment = convertDtoToEntity(commentDto);

    // Comment 엔터티에 IP 주소 추가
    comment.setIpAddress(ipAddress);

    // 다른 필요한 로직 추가 가능

    // Comment 엔터티를 저장
    commentRepository.save(comment);
  }

  // 다른 메서드들은 여기에 추가 가능

  // CommentDto를 Comment 엔터티로 변환하는 메서드
  private Comment convertDtoToEntity(CommentDto commentDto) {
    Comment comment = new Comment();
    comment.setContent(commentDto.getContent());
    // 다른 필요한 변환 로직 추가
    return comment;
  }
  // 게시글 번호와 일치하는 댓글만 보이게
  public List<Comment> getCommentsByPostId(Long postId) {
    // CommentRepository를 사용하여 postId에 해당하는 댓글을 조회
    return commentRepository.findByPostId(postId);
  }

  public List<Comment> findAll(Long postId) {
    return commentRepository.findAll();
  }

  public boolean hasLikedComment(CommentLikeId commentLikeId) {
    Optional<CommentLike> like = commentLikeRepository.findById(commentLikeId);
    return like.isPresent();
  }
  public Comment getCommentById(Long commentId) {
    return commentRepository.findById(commentId)
            .orElseThrow(() -> new EntityNotFoundException("Comment not found with id: " + commentId));
  }
  public void saveComment(Comment comment) {
    // Comment 엔터티를 저장 또는 업데이트
    commentRepository.save(comment);
  }
}