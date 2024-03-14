package com.simulation.LoLItemSimulation.controller;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.CommentLikeInfo;
import com.simulation.LoLItemSimulation.domain.Post;
import com.simulation.LoLItemSimulation.domain.PostLike;
import com.simulation.LoLItemSimulation.dto.CommentDto;
import com.simulation.LoLItemSimulation.dto.PostDto;
import com.simulation.LoLItemSimulation.repository.CommentRepository;
import com.simulation.LoLItemSimulation.repository.PostLikeRepository;
import com.simulation.LoLItemSimulation.repository.PostRepository;
import com.simulation.LoLItemSimulation.service.CommentLikeService;
import com.simulation.LoLItemSimulation.service.CommentService;
import com.simulation.LoLItemSimulation.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

// PostController.java
@Controller
@RequestMapping("/post")
@Log4j2
public class PostController {
  @Autowired
  private PostService postService;

  @Autowired
  private HttpServletRequest request;
  @Autowired
  private PostRepository postRepository;

  @Autowired
  private CommentRepository commentRepository;
  @Autowired
  private CommentService commentService;
  @Autowired
  private CommentLikeService commentLikeService;

  @Autowired
  private PostLikeRepository postLikeRepository;


  //    @PostMapping("/create")
  //    public ResponseEntity<String> createPost(@RequestBody PostDto postDto) {
  //        // DTO를 엔터티로 변환 후 저장 로직
  //        Post post = convertDtoToEntity(postDto);
  //
  //        // 비밀번호, 닉네임, IP 주소 설정
  //        post.setPassword(postDto.getPassword());
  //        post.setNickname(postDto.getNickname());
  //        post.setIpAddress(request.getRemoteAddr());
  //
  //        postService.savePost(post);
  //        return new ResponseEntity<>("게시글이 생성되었습니다.", HttpStatus.CREATED);
  //    }
  @PostMapping("/submitForm")
  public String submitForm(@ModelAttribute("post") Post post, HttpServletRequest request) {
    // 닉네임, 비밀번호 설정
    post.setNickname(post.getNickname());
    post.setPassword(post.getPassword());

    // IP 주소 설정
    String ipAddress = getClientIP(request);
    post.setIpAddress(ipAddress);

    // 글 작성일
    LocalDateTime createtime = LocalDateTime.now();
    post.setCreatetime(createtime);

    // title과 content는 HTML 폼에서의 매핑을 기다립니다.

    // 저장
    postRepository.save(post);

    // 다른 로직 또는 리다이렉션 등...
    // 글 작성이 성공하면 readPost 페이지로 리다이렉션
    return "redirect:/post/read/" + post.getId();
  }
  @GetMapping("/create")
  public String showForm(Model model) {
    model.addAttribute("post", new Post());
    return "postForm";
  }

  @GetMapping("/list")
  public String getPostList(@RequestParam(defaultValue = "0") int page, Model model) {
    Page<Post> paging = this.postService.getList(page);

    model.addAttribute("paging", paging);
    return "postList";
  }

  @GetMapping("/read/{postId}")
  public String readPost(@PathVariable Long postId, Model model, HttpServletRequest request) {
    // 조회수 증가
    postService.incrementViews(postId);

    // postId에 해당하는 게시글 정보를 가져옴
    PostDto postDto = postService.getPostDtoById(postId);

    if (postDto == null) {
      // 게시글이 없을 경우 예외처리
      // 여기서는 단순하게 "게시글이 없습니다."를 반환하도록 하겠습니다.
      return "게시글이 없습니다.";
    }



    List<Comment> commentDtoList = commentService.getCommentsByPostId(postId);
    List<CommentLikeInfo> commentLikeInfoList = new ArrayList<>();

    String clientIpAddress = getClientIP(request);
    log.info("client IP " +  clientIpAddress);
    for (Comment comment : commentDtoList) {
      boolean isLiked = commentLikeService.isCommentLikedByIp(comment.getId(), clientIpAddress);
      log.info("is LIKE" + isLiked);
      commentLikeInfoList.add(new CommentLikeInfo(comment, isLiked));
    }
    model.addAttribute("comment", commentLikeInfoList);

    model.addAttribute("post", postDto);
    return "readPost";
  }

  // 게시글 수정 페이지로 이동하는 요청 처리 메소드
  @GetMapping("/modify/{postId}")
  public ModelAndView getModifyPostPage(@PathVariable("postId") Long postId, Model model, HttpServletRequest request) {
    // 인터넷 주소창에서 직접 입력한 요청인지 확인
    String referer = request.getHeader("referer");
    if (referer == null || referer.isEmpty()) {
      // 인터넷 주소창에서 직접 입력한 요청일 경우에만 404 페이지로 리디렉션
      return new ModelAndView("404");
    } else {

      // 게시글 정보를 가져와서 모델에 담기
      Optional<Post> postOptional = postService.getPostById(postId);

      if (postOptional.isPresent()) {
        Post post = postOptional.get();
        model.addAttribute("post", post);
        return new ModelAndView("modifyPost"); // 수정 페이지로 이동
      } else {
        // 게시글이 없을 경우 예외처리
        // 여기서는 단순하게 "게시글이 없습니다."를 반환하도록 하겠습니다.
        return new ModelAndView("게시글이 없습니다.");
      }
    }
  }

  // 게시글 비밀번호 확인
  @PostMapping("/checkPassword")
  @ResponseBody
  public String checkPassword(@RequestBody PasswordRequest request) {
    Long postId = request.getPostId();
    String password = request.getPassword();
    // postId와 password를 이용하여 게시글의 비밀번호를 확인하는 로직 작성
    boolean passwordMatch = postService.checkPassword(postId, password);
    return String.valueOf(passwordMatch);
  }

  // 비밀번호 확인 요청의 요청 본문을 받기 위한 클래스
  @Getter
  @Setter
  static class PasswordRequest {
    private Long postId;
    private String password;
    private String title;
    private String nickname;
  }


  // 게시글 수정 처리 메소드
  @PostMapping("/updatePost/{postId}")
  public ResponseEntity<String> updatePost(@PathVariable Long postId, @RequestBody PostDto postDto) {
    postService.updatePost(postId, postDto);
    return ResponseEntity.ok("게시글이 성공적으로 업데이트되었습니다.");
    //Todo: 게시글 업데이트 후 해당 게시글 read 페이지 이동하기
  }


  // 삭제 요청 처리하는 메소드
  @DeleteMapping("/delete/{postId}")
  public ResponseEntity<String> deleteArticle(@PathVariable long postId, @RequestBody Map<String, String> requestBody) {
    String password = requestBody.get("password");

      // 게시글을 삭제
      postService.deletePost(postId, password);
      // 삭제가 성공적으로 이루어지면 HTTP 상태 코드 200 OK를 반환합니다.
      return ResponseEntity.ok("게시글이 성공적으로 삭제되었습니다.");
  }

  // 게시글 좋아요
  @PostMapping("/{postId}/like")
  public ResponseEntity<Boolean> addOrRemoveLike(@PathVariable Long postId, @RequestBody Map<String, String> requestBody) {
    String ipAddress = getClientIP(request);

    Post post = postRepository.findById(postId).orElse(null);

    if (post == null) {
      return ResponseEntity.notFound().build();
    }

    PostLike existingLike = postLikeRepository.findByPostIdAndIpAddress(postId, ipAddress);

    if (existingLike != null) {
      // 이미 좋아요를 눌렀으면 삭제
      postLikeRepository.delete(existingLike);
      return ResponseEntity.ok(false); // Like removed
    } else {
      // 좋아요 추가
      PostLike newLike = new PostLike();
      newLike.setPost(post);
      newLike.setIpAddress(ipAddress);
      postLikeRepository.save(newLike);
      return ResponseEntity.ok(true); // Like added
    }
  }

  // 게시글 좋아요 수 카운트
  @GetMapping("/{postId}/like/count")
  public ResponseEntity<Integer> getPostLikeCount(@PathVariable Long postId) {
    int likeCount = postService.getPostLikeCount(postId);
    return ResponseEntity.ok(likeCount);
  }



  private Post convertDtoToEntity(PostDto postDto){
    Post post = new Post();
    post.setTitle(postDto.getTitle());
    post.setContent(postDto.getContent());
    return post;
  }
  @PostMapping(value = "/createComment", consumes = "application/json")
  public ResponseEntity<Comment> createComment(@RequestBody CommentDto commentDto, HttpServletRequest request) {
    // 댓글 생성 로직...
    log.info("v파라미터 확인 :  " + commentDto.toString());
    Comment comment = new Comment();
    comment.setPostId(commentDto.getPostId());
    comment.setNickname(commentDto.getNickname());
    comment.setPassword(commentDto.getPassword());
    comment.setContent(commentDto.getContent());

    // 클라이언트의 실제 IP 주소 가져오기
    String ipAddress = getClientIP(request);
    comment.setIpAddress(ipAddress);
    log.info("댓글생성 확인 : " + comment);
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




  // 클라이언트의 실제 IP 주소를 가져오는 메서드
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

}