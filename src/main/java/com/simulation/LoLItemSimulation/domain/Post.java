package com.simulation.LoLItemSimulation.domain;

import com.simulation.LoLItemSimulation.domain.Comment;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
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



  // 'comment' 필드와 관련된 매핑이 있다면, insertable과 updatable 속성을 추가합니다.
  @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Comment> comments;

  // 생성자, 게터, 세터 등 필요한 메서드 추가

}