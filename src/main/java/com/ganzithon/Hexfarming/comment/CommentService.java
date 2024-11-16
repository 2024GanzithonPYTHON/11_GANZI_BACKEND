package com.ganzithon.Hexfarming.comment;

import com.ganzithon.Hexfarming.dto.fromClient.CommentRequestDto;
import com.ganzithon.Hexfarming.dto.fromServer.CommentResponseDto;
import com.ganzithon.Hexfarming.domain.user.User;
import com.ganzithon.Hexfarming.domain.user.UserRepository;
import com.ganzithon.Hexfarming.post.Post;
import com.ganzithon.Hexfarming.post.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    @Autowired
    public CommentService(CommentRepository commentRepository, PostRepository postRepository, UserRepository userRepository) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public CommentResponseDto createComment(Long postId, CommentRequestDto commentRequestDto, String username) {
        // 작성자 확인
        User user = getUserByUsername(username);

        // 게시물 확인
        Post post = getPostById(postId);

        // 점수 유효성 검증
        validateScore(commentRequestDto.getScore());

        // 댓글 생성
        Comment comment = new Comment();
        comment.setContent(commentRequestDto.getContent());
        comment.setWriterNickname(user.getNickname());
        comment.setWriterTier(user.getTier());
        comment.setScore(commentRequestDto.getScore());
        comment.setPost(post);
        comment.setWriter(user);

        // 댓글 저장
        commentRepository.save(comment);

        // DTO 반환
        return mapToDto(comment);
    }

    @Transactional(readOnly = true)
    public List<CommentResponseDto> getCommentsByPostId(Long postId) {
        // 댓글 조회 및 DTO 변환
        return commentRepository.findByPost_PostId(postId)
                .stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public CommentResponseDto updateComment(Long postId, Long commentId, CommentRequestDto commentRequestDto, String username) {
        // 게시물 확인
        Post post = getPostById(postId);

        // 댓글 조회 및 작성자 확인
        Comment comment = getCommentByIdAndVerifyOwnership(commentId, username);

        // 댓글이 해당 게시물에 속해 있는지 확인
        if (!comment.getPost().equals(post)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시물의 댓글이 아닙니다.");
        }

        // 점수 유효성 검증(100~200점)
        validateScore(commentRequestDto.getScore());

        // 댓글 수정
        comment.setContent(commentRequestDto.getContent());
        comment.setScore(commentRequestDto.getScore());

        // DTO 반환
        return mapToDto(comment);
    }

    @Transactional
    public void deleteComment(Long postId, Long commentId, String username) {
        Post post = getPostById(postId);

        // 댓글 조회 & 작성자 확인
        Comment comment = getCommentByIdAndVerifyOwnership(commentId, username);

        if (!comment.getPost().equals(post)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "해당 게시물의 댓글이 아닙니다.");
        }

        // 댓글 삭제
        commentRepository.delete(comment);
    }

    // 유틸리티 메서드
    private User getUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "작성자를 찾을 수 없습니다.");
        }
        return user;
    }

    private Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "게시물을 찾을 수 없습니다."));
    }

    private Comment getCommentByIdAndVerifyOwnership(Long commentId, String username) {
        Comment comment = commentRepository.findById(commentId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "댓글을 찾을 수 없습니다."));

        if (!comment.getWriter().getUsername().equals(username)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "본인의 댓글만 수정/삭제할 수 있습니다.");
        }
        return comment;
    }

    private void validateScore(int score) {
        if (score < 100 || score > 200) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "점수는 100에서 200 사이여야 합니다.");
        }
    }

    private CommentResponseDto mapToDto(Comment comment) {
        return new CommentResponseDto(
                comment.getId(),
                comment.getContent(),
                comment.getWriterNickname(),
                comment.getWriterTier(),
                comment.getCreatedAt(),
                comment.getScore()
        );
    }
}
