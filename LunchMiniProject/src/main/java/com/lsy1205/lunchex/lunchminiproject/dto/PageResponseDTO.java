package com.lsy1205.lunchex.lunchminiproject.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Getter
@ToString
public class PageResponseDTO<E> {

  private int page;
  private int size;
  private int total;

  private  int start;
  private  int end;

  private  boolean prev;
  private  boolean next;

  // db에서 나눠서 가지고오는 lunch 의 목록 리스트
  private List<E> dtoList;

  // 생성자 만들기.
  // PageResponseDTO pageResponseDTO = PageResponseDTO.builder().build()
  // 변경
  // PageResponseDTO pageResponseDTO = PageResponseDTO.withAll<E>.build()
  @Builder(builderMethodName = "withAll")
  public PageResponseDTO(PageRequestDTO pageRequestDTO, List<E>dtoList, int total) {
    this.page = pageRequestDTO.getPage();
    this.size = pageRequestDTO.getSize();

    // 디비에서 조회하고 새로 받아온 내용을 교체하는 작업.
    this.total = total;
    this.dtoList = dtoList;

    this.end = (int)(Math.ceil(this.page/10.0)) * 10;
    this.start = this.end - 9;

    int last = (int)(Math.ceil(total/(double)size));

    this.end = end > last ? last : end;

    this.prev = this.start > 1;
    this.next = total > this.end * this.size;


  }




}







