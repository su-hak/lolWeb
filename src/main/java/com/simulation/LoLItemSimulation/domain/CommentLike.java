package com.simulation.LoLItemSimulation.domain;

import jakarta.persistence.*;

// CommentLike.java
@Entity
public class CommentLike {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne
  @JoinColumn(name = "comment_id")
  private Comment comment;

  @Column(name = "ip_address")
  private String ipAddress;


  // 생성자, 게터, 세터 등 필요한 메서드 추가

  public Comment getComment() {
    return comment;
  }

  public void setComment(Comment comment) {
    this.comment = comment;
  }

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
}