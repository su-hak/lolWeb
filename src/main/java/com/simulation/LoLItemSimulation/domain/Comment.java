package com.simulation.LoLItemSimulation.domain;

import jakarta.persistence.*;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

// Comment.java
@Entity
public class Comment {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String content;
  @Column(name = "nickname")
  private String nickname;

  @Column(name = "password")
  private String password;

  @Column(name = "ip_address")
  private String ipAddress;
  @Column(name = "post_id")
  private Long postId;

  @Column(name = "createtime")
  private LocalDateTime createTime;

  @Column(name = "updatetime")
  private LocalDateTime updateTime;

  @ManyToOne
  @JoinColumn(name = "post_id", insertable = false, updatable = false)
  private Post post;

  @OneToMany(mappedBy = "comment", cascade = CascadeType.ALL, orphanRemoval = true)
  private Set<CommentLike> likes = new HashSet<>();

  public Set<CommentLike> getLikes() {
    return likes;
  }

  public void setLikes(Set<CommentLike> likes) {
    this.likes = likes;
  }

  public LocalDateTime getCreateTime() {
    return createTime;
  }

  public void setCreateTime(LocalDateTime createTime) {
    this.createTime = createTime;
  }

  public LocalDateTime getUpdateTime() {
    return updateTime;
  }

  public void setUpdateTime(LocalDateTime updateTime) {
    this.updateTime = updateTime;
  }

  public Long getPostId() {
    return postId;
  }

  public void setPostId(Long postId) {
    this.postId = postId;
  }

  // 다른 필요한 댓글 관련 필드들 추가

  // 댓글과 게시글 관계 설정


  public void setContent(String content) {
    this.content = content;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getContent() {
    return content;
  }

  public String getPassword() {
    return password;
  }

  public Post getPost() {
    return post;
  }

  public void setPost(Post post) {
    this.post = post;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  // 생성자, 게터, 세터 등 필요한 메서드 추가
  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }
  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
}