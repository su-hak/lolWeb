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

  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  public Page<Post> getList(int page) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("id"));

    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
    return this.postRepository.findAll(pageable);
  }

  public void savePost(Post post) {
    // 게시글 저장 로직
    postRepository.save(post);
  }

  public List<Post> delete(long postId, String password) {
    // 해당 ID에 해당하는 게시글을 찾습니다.
    Optional<Post> optionalPost = postRepository.findById(postId);

    // 해당 ID에 해당하는 게시글이 존재하는지 확인합니다.
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();

      // 입력된 비밀번호와 게시글의 비밀번호를 비교합니다.
      if (post.getPassword().equals(password)) {
        // 비밀번호가 일치하면 게시글을 삭제하고 postList를 반환합니다.
        postRepository.delete(post);
        // postList 반환
        return postRepository.findAll();
      } else {
        // 비밀번호가 일치하지 않은 경우에 대한 예외 처리를 할 수 있습니다.
        throw new IllegalArgumentException("비밀번호가 일치하지 않습니다.");
      }
    } else {
      // 해당 ID에 해당하는 게시글이 존재하지 않는 경우에 대한 예외 처리를 할 수 있습니다.
      throw new IllegalArgumentException("게시글이 존재하지 않습니다.");
    }
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
    postDto.setCreatetime(post.getCreatetime());

    // 다른 필요한 변환 로직 추가
    return postDto;
  }
}