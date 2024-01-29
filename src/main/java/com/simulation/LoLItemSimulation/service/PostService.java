package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.domain.Post;
import com.simulation.LoLItemSimulation.dto.PostDto;
import com.simulation.LoLItemSimulation.repository.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// PostService.java
@Service
@RequiredArgsConstructor
public class PostService {
  @Autowired
  private PostRepository postRepository;

  public void savePost(Post post) {
    // 게시글 저장 로직
    postRepository.save(post);
  }
  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  public Page<Post> getList(int page) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("id"));

    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
    return this.postRepository.findAll(pageable);
  }

  public Optional<Post> getPostById(Long id) {
    return postRepository.findById(id);
  }

  // 다른 필요한 메서드들 추가
  public PostDto getPostDtoById(Long postId) {
    // postId에 해당하는 게시글 정보를 불러옴
    Post post = postRepository.findById(postId).orElse(null);

    if (post == null) {
      // 게시글이 없을 경우 예외처리
      return null;
    }

    // Post 엔터티를 PostDto로 변환
    return convertEntityToDto(post);
  }
  public PostDto convertEntityToDto(Post post) {
    PostDto postDto = new PostDto();
    postDto.setId(post.getId());
    postDto.setTitle(post.getTitle());
    postDto.setContent(post.getContent());
    postDto.setNickname(post.getNickname());
    postDto.setIpAddress(post.getIpAddress());


    // 다른 필요한 변환 로직 추가
    return postDto;
  }
}