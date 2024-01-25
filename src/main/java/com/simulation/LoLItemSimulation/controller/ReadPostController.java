package com.simulation.LoLItemSimulation.controller;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.repository.CommentRepository;
import com.simulation.LoLItemSimulation.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@RequestMapping("/post")
public class ReadPostController {

  @Autowired
  private CommentRepository commentRepository;

  @Autowired
  private CommentService commentService;

  // 다른 메서드들...

  //    @GetMapping("/read/{postId}")
  //    public String readPost(@PathVariable Long postId, Model model) {
  //        // 게시글 정보 조회 로직...
  //
  //        // 게시글과 연관된 댓글 조회
  //        List<Comment> comments = commentRepository.findByPostId(postId);
  //
  //        // 모델에 댓글 리스트 추가
  //        model.addAttribute("comments", comments);
  //
  //        // 다른 모델 속성들 추가...
  //
  //        return "readPost";
  //    }
//  @GetMapping("/post/getComments/{postId}")
//  @ResponseBody
//  public List<Comment> getCommentsByPostId(@PathVariable Long postId) {
//    // postId에 해당하는 게시물의 댓글 목록을 가져오는 로직
//    List<Comment> comments = commentService.getCommentsByPostId(postId);
//    return comments;
//  }
}