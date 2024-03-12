package com.simulation.LoLItemSimulation.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

// CommentLikeId.java
@Embeddable
public class CommentLikeId implements Serializable {
  @Column(name = "comment_id")
  private Long commentId;

  // 생성자, 게터, 세터 등 필요한 메서드 추가

  public Long getCommentId() {
    return commentId;
  }

  public void setCommentId(Long commentId) {
    this.commentId = commentId;
  }
}