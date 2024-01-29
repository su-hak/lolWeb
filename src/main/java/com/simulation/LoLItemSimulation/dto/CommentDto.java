package com.simulation.LoLItemSimulation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

// CommentDto.java
@NoArgsConstructor
@Setter
@Getter
@ToString
public class CommentDto {
  private String content;

  private String password;

  private String ipAddress;

  private String nickname;

  private Long postId;

  private LocalDateTime createTime;

  private LocalDateTime updateTime;

  //    public Long getPostId() {
  //        return postId;
  //    }
  //
  //    public void setPostId(Long postId) {
  //        this.postId = postId;
  //    }
  //
  //
  //    public String getIpAddress() {
  //        return ipAddress;
  //    }
  //
  //    public void setIpAddress(String ipAddress) {
  //        this.ipAddress = ipAddress;
  //    }
  //
  ////    public CommentDto(String content, String password, String ipAddress, String nickname) {
  ////        this.content = content;
  ////        this.password = password;
  ////        this.ipAddress = ipAddress;
  ////        this.nickname = nickname;
  ////    }
  //
  //    public String getContent() {
  //        return content;
  //    }
  //
  //    public void setContent(String content) {
  //        this.content = content;
  //    }
  //
  //    public String getPassword() {
  //        return password;
  //    }
  //
  //    public void setPassword(String password) {
  //        this.password = password;
  //    }
  //// 댓글과 관련된 다른 필드들 추가
  //    // Getter, Setter 메서드 추가
  //    public String getNickname() {
  //        return nickname;
  //}
  //
  //    public void setNickname(String nickname) {
  //        this.nickname = nickname;
  //    }
}