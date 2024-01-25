package com.simulation.LoLItemSimulation.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentRequest {


  private Long postId;


  private String nickname;


  private String password;


  private String content;
}