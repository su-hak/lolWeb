package com.simulation.LoLItemSimulation.service;

import com.simulation.LoLItemSimulation.domain.*;
import com.simulation.LoLItemSimulation.dto.PostDto;
import com.simulation.LoLItemSimulation.repository.*;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

// PostService.java
@Service
@RequiredArgsConstructor
public class PostService {
  @Autowired
  private PostRepository postRepository;

  @Autowired
  private PostLikeRepository postLikeRepository;

  @Autowired
  private PostHateRepository postHateRepository;

  @Autowired
  private final CommentRepository commentRepository;

  @Autowired
  private CommentLikeRepository commentLikeRepository;


  public List<Post> getAllPosts() {
    return postRepository.findAll();
  }

  public Page<Post> getList(int page, String sort) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("id"));

    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
    return this.postRepository.findAll(pageable);
  }

  public Page<Post> getSortedPosts(int page, String sortBy) {
    Sort sort = Sort.by(sortBy);

    if (sortBy.equals("id")) {
      sort = Sort.by(Sort.Direction.DESC, "id");
    } else if (sortBy.equals("date")) {
      sort = Sort.by(Sort.Direction.DESC, "createtime");
    }else if (sortBy.equals("replyCount")) {
      return getPostsSortedByCommentCount(page);
    }else if (sortBy.equals("nickname")) {
      sort = Sort.by(Sort.Direction.DESC, "nickname");
    }else if (sortBy.equals("viewCount")) {
      sort = Sort.by(Sort.Direction.DESC, "views");
    }

    Pageable pageable = PageRequest.of(page, 10, sort);

    return postRepository.findAll(pageable);
  }

  public Page<Post> getPostsSortedByCommentCount(int page) {
    Pageable pageable = PageRequest.of(page, 10);
    return postRepository.findAllPostsSortedByCommentCount(pageable);
  }

  public void savePost(Post post) {
    // 게시글 저장 로직
    postRepository.save(post);
  }

  // 게시글 수정 메서드
  public void updatePost(Long postId, PostDto postDto) {
    Post post = postRepository.findById(postId)
            .orElseThrow(() -> new EntityNotFoundException("게시글을 찾을 수 없습니다."));
    // 업데이트할 필드 설정
    post.setTitle(postDto.getTitle());
    post.setContent(postDto.getContent());
    post.setPassword(postDto.getPassword());
    post.setCreatetime(postDto.getCreatetime());
    // 업데이트된 게시글 저장
    postRepository.save(post);
  }

  // 게시글의 비밀번호가 일치하는지 확인하는 메소드
  public boolean checkPassword(Long postId, String password) {
    Optional<Post> postOptional = postRepository.findById(postId);
    if (postOptional.isPresent()) {
      Post post = postOptional.get();
      // DB에서 가져온 게시글의 비밀번호와 입력된 비밀번호 비교
      return post.getPassword().equals(password);
    }
    return false; // 게시글이 존재하지 않는 경우
  }

  // 게시글 삭제 메서드
  public void deletePost(Long postId, String password) {
    // 게시글을 찾습니다.
    Optional<Post> optionalPost = postRepository.findById(postId);

    // 게시글이 존재하는지 확인합니다.
    if (optionalPost.isPresent()) {
      Post post = optionalPost.get();

      // 게시글의 비밀번호가 올바른지 확인합니다.
      if (post.getPassword().equals(password)) {

        // 게시글에 연결된 댓글 좋아요 정보도 모두 삭제합니다.
        List<CommentLike> likes = commentLikeRepository.findByCommentPostId(postId);
        commentLikeRepository.deleteAll(likes);

        // 게시글에 연결된 댓글을 모두 삭제합니다.
        List<Comment> comments = commentRepository.findByPostId(postId);
        commentRepository.deleteAll(comments);

        // 게시글에 연결된 좋아요를 삭제합니다.
        List<PostLike> postLikes = postLikeRepository.findByPostId(postId); // 수정된 부분
        postLikeRepository.deleteAll(postLikes);

        // 게시글에 연결된 싫어요를 삭제합니다.
        List<PostHate> hates = postHateRepository.findByPostId(postId); // 수정된 부분
        postHateRepository.deleteAll(hates);


        // 게시글을 삭제합니다.
        postRepository.delete(post);
      } else {
        throw new IllegalArgumentException("게시글 비밀번호가 일치하지 않습니다.");
      }
    } else {
      throw new IllegalArgumentException("게시글을 찾을 수 없습니다.");
    }
  }


  public Optional<Post> getPostById(Long id) {
    return postRepository.findById(id);
  }


  // 조회수 증가 메서드
  public void incrementViews(Long postId) {
    Optional<Post> postOptional = postRepository.findById(postId);
    if (postOptional.isPresent()) {
      Post post = postOptional.get();
      post.setViews(post.getViews() + 1);
      postRepository.save(post);
    }
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
    postDto.setViews(post.getViews());

    // 다른 필요한 변환 로직 추가
    return postDto;
  }

  // 게시글 좋아요 수 카운트
  @Autowired
  public PostService(PostRepository postRepository, CommentRepository commentRepository) {
    this.postRepository = postRepository;
    this.commentRepository = commentRepository;
  }

  private static final int PAGE_SIZE = 10; //

  public int getPostLikeCount(Long postId) {
    return postRepository.getPostLikeCount(postId);
  }

  public int getPostHateCount(Long postId) {
    return postRepository.getPostHateCount(postId);
  }

//  public Page<Post> searchPosts(String type, String keyword, int page) {
//    List<Sort.Order> sorts = new ArrayList<>();
//    sorts.add(Sort.Order.desc("createtime")); // createdAt 필드를 기준으로 내림차순 정렬
//
//    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));
//
//    if (type.equals("title")) {
//      return postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
//    } else if (type.equals("content")) {
//      return postRepository.findByContentContainingIgnoreCase(keyword, pageable);
//    } else if (type.equals("titleContent")) {
//      return postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable);
//    } else if (type.equals("nickname")) {
//      return postRepository.findByNicknameContainingIgnoreCase(keyword, pageable);
//    } else {
//      return null;
//
//
//    }
//  }
  public Page<Post> searchPosts(String type, String keyword, int page) {
    List<Sort.Order> sorts = new ArrayList<>();
    sorts.add(Sort.Order.desc("createtime")); // createdAt 필드를 기준으로 내림차순 정렬

    Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

    Page<Post> posts;

    if (type.equals("title")) {
      posts = postRepository.findByTitleContainingIgnoreCase(keyword, pageable);
    } else if (type.equals("content")) {
      posts = postRepository.findByContentContainingKeyword(keyword, pageable);
    } else if (type.equals("titleContent")) {
      posts = postRepository.findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(keyword, keyword, pageable);
    } else if (type.equals("nickname")) {
      posts = postRepository.findByNicknameContainingIgnoreCase(keyword, pageable);
    } else {
      posts = Page.empty(); // 빈 페이지 반환
    }

    // 검색 결과가 없을 때의 추가 작업은 필요하지 않습니다.

    return posts;
  }
}