package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import java.util.List;

// PostRepository.java
public interface PostRepository extends JpaRepository<Post, Long>, PagingAndSortingRepository<Post, Long> {
  // 필요한 쿼리 메서드 추가
  List<Post> findAllByOrderByIdDesc(); // 일단 리스트 역순정렬

  Page<Post> findAllByOrderByIdDesc(Pageable pageable); // 페이징 처리 역순정렬
}