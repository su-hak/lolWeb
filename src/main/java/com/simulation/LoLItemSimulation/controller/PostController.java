package com.simulation.LoLItemSimulation.controller;

import com.simulation.LoLItemSimulation.domain.Comment;
import com.simulation.LoLItemSimulation.domain.CommentLikeInfo;
import com.simulation.LoLItemSimulation.domain.Post;
import com.simulation.LoLItemSimulation.dto.CommentDto;
import com.simulation.LoLItemSimulation.dto.PostDto;
import com.simulation.LoLItemSimulation.repository.CommentRepository;
import com.simulation.LoLItemSimulation.repository.PostRepository;
import com.simulation.LoLItemSimulation.service.CommentService;
import com.simulation.LoLItemSimulation.service.PostService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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
    public String getPostList(Model model) {
        List<Post> posts = postService.getAllPosts();
        model.addAttribute("posts", posts);
        return "postList";
    }

    @GetMapping("/read/{postId}")
    public String readPost(@PathVariable Long postId, Model model, HttpServletRequest request) {
        // postId에 해당하는 게시글 정보를 가져옴
        PostDto postDto = postService.getPostDtoById(postId);

        if (postDto == null) {
            // 게시글이 없을 경우 예외처리
            // 여기서는 단순하게 "게시글이 없습니다."를 반환하도록 하겠습니다.
            return "게시글이 없습니다.";
        }

        List<Comment> commentDtoList = commentService.getCommentsByPostId(postId);
        List<CommentLikeInfo> commentLikeInfoList = new ArrayList<>();

        // 현재 클라이언트의 IP 주소 가져오기
        String clientIpAddress = getClientIP(request);

        // 댓글과 좋아요 정보를 가져와서 클라이언트 IP에 해당하는 댓글과 좋아요 여부를 전달
        for (Comment comment : commentDtoList) {
            boolean isLiked = commentService.isCommentLikedByIp(comment.getId(), clientIpAddress);
            commentLikeInfoList.add(new CommentLikeInfo(comment, isLiked));
        }

        model.addAttribute("comment", commentLikeInfoList);
        model.addAttribute("post", postDto);
        return "readPost";
    }

    private Post convertDtoToEntity(PostDto postDto) {
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
