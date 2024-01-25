package com.simulation.LoLItemSimulation.dto;

// PostDto.java
public class PostDto {
  private Long id;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  private String title;
  private String content;

  private String nickname;
  private String password;
  private String ipAddress;

  public String getIpAddress() {
    return ipAddress;
  }

  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }

  public PostDto() {
    this.id = id;
    this.title = title;
    this.content = content;
    this.nickname = nickname;
    this.password = password;
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
  // 게시글과 관련된 다른 필드들 추가

  public String getTitle() {
    return title;
  }

  public void setTitle(String title) {
    this.title = title;
  }

  public String getContent() {
    return content;
  }

  public void setContent(String content) {
    this.content = content;
  }
  // Getter, Setter 메서드 추가
}