package com.simulation.LoLItemSimulation.controller;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.dto.CommentDto;
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
    private HttpServletRequest request;

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
}
