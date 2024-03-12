package com.simulation.LoLItemSimulation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Setter
@Getter
@ToString
// PostDto.java
public class PostDto {
  private Long id;

  private String title;

  private String content;

  private String nickname;

  private String password;

  private String ipAddress;

  private LocalDateTime createtime;

}