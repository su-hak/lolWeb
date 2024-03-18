package com.simulation.LoLItemSimulation.controller;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.CommentLike;
import com.simulation.LoLItemSimulation.domain.CommentLikeId;
import com.simulation.LoLItemSimulation.dto.CommentDto;
import com.simulation.LoLItemSimulation.repository.CommentLikeRepository;
import com.simulation.LoLItemSimulation.repository.CommentRepository;
import com.simulation.LoLItemSimulation.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

// CommentController.java
@Controller
@RequestMapping("/comment")
public class CommentController {
  @Autowired
  private CommentService commentService;

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private CommentLikeRepository commentLikeRepository;

  @Autowired
  private HttpServletRequest request;
  //    private CommentLikeRepository commentLikeRepository;

  public CommentController(CommentService commentService) {
    this.commentService = commentService;
  }

  //    @PostMapping("/save")
  //    public ResponseEntity<String> saveComment(@RequestBody CommentDto commentDto, HttpServletRequest request) {
  //        // 클라이언트의 IP 주소 가져오기
  //        String ipAddress = request.getRemoteAddr();
  //
  //        // CommentDto와 IP 주소를 함께 전달하여 댓글 저장
  //        commentService.saveComment(commentDto, ipAddress);
  //
  //        return new ResponseEntity<>("댓글이 저장되었습니다.", HttpStatus.CREATED);
  //    }

  private Comment convertDtoToEntity(CommentDto commentDto) {
    Comment comment = new Comment();
    comment.setNickname(commentDto.getNickname());
    comment.setContent(commentDto.getContent());
    comment.setPassword(commentDto.getPassword());
    comment.setIpAddress(commentDto.getIpAddress());
    // 다른 필요한 변환 로직 추가
    return comment;
  }

  private String getClientIpAddress(HttpServletRequest request) {
    String xForwardedForHeader = request.getHeader("X-Forwarded-For");
    if (xForwardedForHeader == null) {
      return request.getRemoteAddr();
    }
    return xForwardedForHeader.split(",")[0];
  }
  //    @PostMapping("/post/createComment")
  //    @ResponseBody
  //    public String createComment(@RequestBody CommentDto commentDto) {
  //        // Implement logic to save the comment in the database
  //        // You can use commentDto.getPostId(), commentDto.getNickname(), commentDto.getPassword(), commentDto.getContent()
  //
  //        // Return a success message or an appropriate response
  //        return "Comment created successfully";
  //    }



  private String getClientIP(HttpServletRequest request) {
    String ipAddress = request.getHeader("X-Forwarded-For");
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("WL-Proxy-Client-IP");
    }
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getHeader("HTTP_X_FORWARDED_FOR");
    }
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)) {
      ipAddress = request.getRemoteAddr();
    }
    if (ipAddress == null || ipAddress.isEmpty() || "unknown".equalsIgnoreCase(ipAddress)){
      ipAddress = request.getHeader("HTTP_CLIENT_IP");
    }
    return ipAddress;
  }
  // 댓글에 좋아요 추가
  // 댓글에 좋아요 추가
  @PostMapping("/{commentId}/like")
  public ResponseEntity<Boolean> addLike(@PathVariable Long commentId, @RequestBody Map<String, String> requestBody) {
    String ipAddress = getClientIP(request);

    Comment comment = commentRepository.findById(commentId).orElse(null);

    if (comment == null) {
      return ResponseEntity.notFound().build();
    }

    CommentLike existingLike = commentLikeRepository.findByCommentIdAndIpAddress(commentId, ipAddress);

    if (existingLike != null) {
      // 이미 좋아요를 눌렀으면 취소
      commentLikeRepository.delete(existingLike);
      return ResponseEntity.ok(false);
    } else {
      // 좋아요 추가
      CommentLike newLike = new CommentLike();
      newLike.setComment(comment);
      newLike.setIpAddress(ipAddress);
      commentLikeRepository.save(newLike);
      return ResponseEntity.ok(true); // Like added
    }
  }

  // 댓글 좋아요 갯수
  @GetMapping("/{commentId}/like/count")
  public ResponseEntity<Long> getLikeCount(@PathVariable Long commentId) {
    long likeCount = commentLikeRepository.countLikesByCommentId(commentId);
    return ResponseEntity.ok(likeCount);
  }

  // 댓글 생성
  @PostMapping(value = "/createComment", consumes = "application/json")
  public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto, HttpServletRequest request) {
    // 댓글 생성 로직...
//    log.info("v파라미터 확인 :  " + commentDto.toString());
    Comment comment = new Comment();
    comment.setPostId(commentDto.getPostId());
    comment.setNickname(commentDto.getNickname());
    comment.setPassword(commentDto.getPassword());
    comment.setContent(commentDto.getContent());

    // 클라이언트의 실제 IP 주소 가져오기
    String ipAddress = getClientIP(request);
    comment.setIpAddress(ipAddress);
//    log.info("댓글생성 확인 : " + comment);
    // 댓글을 저장
    commentRepository.save(comment);

    return ResponseEntity.ok(comment);
  }

  // 댓글 삭제시 비밀번호 확인
  @PostMapping("/checkCommentPassword")
  @ResponseBody
  public String checkCommentPassword(@RequestBody CommentPasswordRequest request) {
    Long commentId = request.getCommentId(); // 댓글 ID
    String password = request.getPassword(); // 입력된 비밀번호

    // commentId와 password를 이용하여 댓글의 비밀번호를 확인하는 로직 작성
    boolean passwordMatch = commentService.checkCommentPassword(commentId, password);

    return String.valueOf(passwordMatch);
  }
  @Getter
  @Setter
  static class CommentPasswordRequest {
    private Long commentId;
    private String password;
  }

  // 댓글 삭제
  @DeleteMapping("/deleteComment/{commentId}")
  @ResponseBody
  public String deleteComment(@PathVariable Long commentId) {
    boolean deleted = commentService.deleteComment(commentId);
    if (deleted) {
      return "댓글이 성공적으로 삭제되었습니다.";
    } else {
      return "댓글을 삭제하는 중 오류가 발생했습니다.";
    }
  }

  // 게시글의 총 댓글 수를 반환하는 엔드포인트
  @GetMapping("/{postId}/commentCount")
  public ResponseEntity<Integer> getCommentCount(@PathVariable Long postId) {
    int commentCount = commentService.countCommentsByPostId(postId);
    return ResponseEntity.ok(commentCount);
  }


}