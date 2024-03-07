package com.simulation.LoLItemSimulation.domain;

import com.simulation.LoLItemSimulation.domain.Comment;
import jakarta.persistence.*;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

// Post.java
@Entity
public class Post {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String content;
  @Column(name = "nickname")
  private String nickname;

  @Column(name = "password")
  private String password;

  @Column(name = "ip_address")
  private String ipAddress;

  @Column(name = "createtime")
  private LocalDateTime createtime;

  public LocalDateTime getCreatetime() {
    return createtime;
  }

  public void setCreatetime(LocalDateTime createtime) {
    this.createtime = createtime;
  }

  //    public Post(Long id, String title, String content, String nickname, String password, String ipAddress, String password1, List<Comment> comments) {
  //        this.id = id;
  //        this.title = title;
  //        this.content = content;
  //        this.nickname = nickname;
  //        this.password = password;
  //        this.ipAddress = ipAddress;
  //        this.password = password1;
  //        this.comments = comments;
  //    }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getTitle() {
    return title;
  }

  public String getContent() {
    return content;
  }

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

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public List<Comment> getComments() {
    return comments;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }


  // 다른 필요한 게시글 관련 필드들 추가

  // 'comment' 필드와 관련된 매핑이 있다면, insertable과 updatable 속성을 추가합니다.
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  // 생성자, 게터, 세터 등 필요한 메서드 추가

  public void setTitle(String title) {
    this.title = title;
  }

  public void setContent(String content) {
    this.content = content;
  }
}