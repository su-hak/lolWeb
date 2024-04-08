package com.simulation.LoLItemSimulation.repository;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

// PostRepository.java
public interface PostRepository extends JpaRepository<Post, Long> {

  Page<Post> findAll(Pageable pageable);

  // 게시글 좋아요 갯수
  @Query("SELECT COUNT(p) FROM Post p")
  long countAllPosts();

  // 게시글 좋아요 갯수 반환
  @Query("SELECT COUNT(pl) FROM PostLike pl WHERE pl.post.id = :postId")
  int getPostLikeCount(Long postId);

  // 게시글 싫어요 갯수 반환
  @Query("SELECT COUNT(pl) FROM PostHate pl WHERE pl.post.id = :postId")
  int getPostHateCount(Long postId);

  // 제목으로 검색

  Page<Post> findByTitleContainingIgnoreCase(String keyword, Pageable pageable);
  @Query(value = "SELECT * FROM post p WHERE CAST(p.content AS CHAR) LIKE %:keyword%", nativeQuery = true)
  Page<Post> findByContentContainingKeyword(@Param("keyword") String keyword, Pageable pageable);
// content의 타입이 BLOB이라서 위와같은 형태로 CAST해준다음 질의를 해야함.
//Page<Post> findByContentContainingIgnoreCase(String keyword, Pageable pageable);
  // 나중에 이미지 문제 해결하면 다시 사용할 코드
  Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String titleKeyword, String contentKeyword,Pageable pageable);
  Page<Post> findByNicknameContainingIgnoreCase(String keyword, Pageable pageable);

  //게시글 정렬을 위한 코드
  @Query("SELECT p, COUNT(c) AS commentCount " +
          "FROM Post p LEFT JOIN Comment c ON p.id = c.post.id " +
          "GROUP BY p " +
          "ORDER BY commentCount DESC")
  Page<Object[]> findAllSortedByCommentCount(Pageable pageable);

  default Page<Post> findAllPostsSortedByCommentCount(Pageable pageable) {
    Page<Object[]> result = findAllSortedByCommentCount(pageable);
    return result.map(array -> (Post) array[0]);
  }

  Page<Post> findByType(String type, Pageable pageable);
} 