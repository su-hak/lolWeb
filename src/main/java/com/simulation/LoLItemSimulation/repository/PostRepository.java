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
//  @Query(value = "SELECT * FROM post p WHERE CAST(p.content AS CHAR) LIKE %:keyword%", nativeQuery = true)
//  Page<Post> findByContentContainingKeyword(@Param("keyword") String keyword, Pageable pageable);
// content의 타입이 BLOB이라서 위와같은 형태로 CAST해준다음 질의를 해야함.
//Page<Post> findByContentContainingIgnoreCase(String keyword, Pageable pageable);
  // 나중에 이미지 문제 해결하면 다시 사용할 코드
  Page<Post> findByTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String titleKeyword, String contentKeyword,Pageable pageable);
  Page<Post> findByNicknameContainingIgnoreCase(String keyword, Pageable pageable);

  Page<Post> findByTypeAndTitleContainingIgnoreCase(String type, String keyword, Pageable pageable);
//  @Query(value = "SELECT p FROM Post p WHERE p.type = :type AND CAST(p.content AS CHAR) LIKE %:keyword%", nativeQuery = true)
  @Query("SELECT p FROM Post p WHERE p.type = :type AND p.content LIKE %:keyword%")
  Page<Post> findByTypeAndContentContainingKeyword(@Param("type") String type, @Param("keyword") String keyword, Pageable pageable);

  Page<Post> findByTypeAndTitleContainingIgnoreCaseOrContentContainingIgnoreCase(String type, String keyword1, String keyword2, Pageable pageable);
  Page<Post> findByTypeAndNicknameContainingIgnoreCase(String type, String keyword, Pageable pageable);

  //게시글 정렬을 위한 코드
  @Query("SELECT p, COUNT(c) AS commentCount " +
          "FROM Post p LEFT JOIN Comment c ON p.id = c.post.id " +
          "GROUP BY p " +
          "ORDER BY commentCount DESC")
  Page<Object[]> findAllSortedByCommentCount(Pageable pageable);

//  @Query("SELECT p, COUNT(c) AS commentCount " +
//          "FROM Post p LEFT JOIN Comment c ON p.id = c.post.id " +
//          "WHERE p.type = :type " + // 타입에 해당하는 게시물만 가져오도록 조건 추가
//          "GROUP BY p " +
//          "ORDER BY commentCount DESC")
//  Page<Object[]> findByTypeSortedByCommentCount(@Param("type") String type, Pageable pageable);

  // 타입이 일치하고, 댓글수 순으로 정렬한 title에 키워드가 포함된 결과
  @Query("SELECT p, COUNT(c) AS commentCount " +
        "FROM Post p LEFT JOIN Comment c ON p.id = c.post.id " +
        "WHERE p.type = :type AND p.title LIKE %:keyword% " + // 타입과 키워드에 해당하는 게시물만 가져오도록 조건 추가
        "GROUP BY p.id " + // 그룹화 기준을 게시물의 id로 수정
        "ORDER BY commentCount DESC")
Page<Object[]> findByTypeAndTitleContainingSortedByCommentCount(@Param("type") String type, @Param("keyword") String keyword, Pageable pageable);

  // 타입이 일치하고, 댓글수 순으로 정렬한 content에 키워드가 포함된 결과
  @Query("SELECT p, COUNT(c) AS commentCount " +
          "FROM Post p LEFT JOIN Comment c ON p.id = c.post.id " +
          "WHERE p.type = :type AND p.content LIKE %:keyword% " + // 타입과 키워드에 해당하는 게시물만 가져오도록 조건 추가
          "GROUP BY p.id " + // 그룹화 기준을 게시물의 id로 수정
          "ORDER BY commentCount DESC")
  Page<Object[]> findByTypeAndContentContainingSortedByCommentCount(@Param("type") String type, @Param("keyword") String keyword, Pageable pageable);

  // 타입이 일치하고, 댓글수 순으로 정렬한 title과 content에 키워드가 포함된 결과
  @Query("SELECT p, COUNT(c) AS commentCount " +
          "FROM Post p LEFT JOIN Comment c ON p.id = c.post.id " +
          "WHERE p.type = :type " + // 타입에 해당하는 게시물만 가져오도록 조건 추가
          "AND (p.title LIKE %:keyword% OR p.content LIKE %:keyword%) " + // 타이틀이나 컨텐트 중에 키워드가 포함된 경우
          "GROUP BY p.id " + // 그룹화 기준을 게시물의 id로 수정
          "ORDER BY commentCount DESC")
  Page<Object[]> findByTypeAndTitleContainingContentContainingSortedByCommentCount(@Param("type") String type, @Param("keyword") String keyword, Pageable pageable);

  // 타입이 일치하고, 댓글수 순으로 정렬한 nickname에 키워드가 포함된 결과
  @Query("SELECT p, COUNT(c) AS commentCount " +
          "FROM Post p LEFT JOIN Comment c ON p.id = c.post.id " +
          "WHERE p.type = :type AND p.nickname LIKE %:keyword% " + // 타입과 키워드에 해당하는 게시물만 가져오도록 조건 추가
          "GROUP BY p.id " + // 그룹화 기준을 게시물의 id로 수정
          "ORDER BY commentCount DESC")
  Page<Object[]> findByTypeAndNicknameContainingSortedByCommentCount(@Param("type") String type, @Param("keyword") String keyword, Pageable pageable);


  default Page<Post> findAllPostsSortedByCommentCount(Pageable pageable) {
    Page<Object[]> result = findAllSortedByCommentCount(pageable);
    return result.map(array -> (Post) array[0]);
  }

  Page<Post> findByType(String type, Pageable pageable);
} 