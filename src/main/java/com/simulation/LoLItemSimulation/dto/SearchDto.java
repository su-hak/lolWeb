package com.simulation.LoLItemSimulation.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SearchDto {

  private int page; // 현재 페이지 번호
  private int postSize; // 한 페이지 당 출력할 게시물 수
  private int pageSize; // 화면에 출력할 페이지 수
  private String keyword;  // 검색 키워드
  private String searchType;  // 검색 유형


  public SearchDto(){
    this.page = 1;        // 현재 페이지를 1로
    this.postSize = 10;   // 한 페이지 당 게시물 10개
    this.pageSize = 5;    // 출력 페이지 5개
  }

  public int getOffset(){
    return (page - 1) * postSize;
  }

}
