package com.simulation.LoLItemSimulation.controller;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.CommentLike;
import com.simulation.LoLItemSimulation.domain.CommentLikeId;
import com.simulation.LoLItemSimulation.dto.CommentDto;
import com.simulation.LoLItemSimulation.repository.CommentLikeRepository;
import com.simulation.LoLItemSimulation.repository.CommentRepository;
import com.simulation.LoLItemSimulation.service.CommentService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

// CommentController.java
@Controller
//@RequestMapping("/comment")
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
  @PostMapping("/{commentId}/like")
  public ResponseEntity<String> addLike(@PathVariable Long commentId, @RequestParam String ipAddress) {
    Comment comment = commentRepository.findById(commentId).orElse(null);

    if (comment == null) {
      return ResponseEntity.notFound().build();
    }

    CommentLike existingLike = commentLikeRepository.findByCommentIdAndIpAddress(commentId, ipAddress);

    if (existingLike != null) {
      // 이미 좋아요를 눌렀으면 취소
      commentLikeRepository.delete(existingLike);
      return ResponseEntity.ok("Like canceled");
    } else {
      // 좋아요 추가
      CommentLike newLike = new CommentLike();
      newLike.setComment(comment);
      newLike.setIpAddress(ipAddress);
      commentLikeRepository.save(newLike);
      return ResponseEntity.ok("Like added");
    }
  }
}