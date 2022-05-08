package com.vivacon.service.impl;

import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.request.CommentRequest;
import com.vivacon.dto.response.CommentResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Comment;
import com.vivacon.entity.Post;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.mapper.CommentMapper;
import com.vivacon.mapper.PageDTOMapper;
import com.vivacon.repository.CommentRepository;
import com.vivacon.repository.PostRepository;
import com.vivacon.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;

    private PostRepository postRepository;

    private CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, PostRepository postRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
        this.commentMapper = commentMapper;
    }

    @Override
    public CommentResponse createComment(CommentRequest commentRequest) {
        Post post = postRepository.findByPostId(commentRequest.getPostId());
        Comment parentComment = null;
        if (commentRequest.getParentCommentId() != null) {
            parentComment = commentRepository.findById(commentRequest.getParentCommentId()).orElse(null);
            if (parentComment != null && !parentComment.getPost().getId().equals(commentRequest.getPostId())) {
                throw new RecordNotFoundException("The parent comment is not match with current post of the request!");
            }
        }

        Comment comment = commentMapper.toComment(commentRequest);
        comment.setActive(true);

        comment.setPost(post);
        comment.setParentComment(parentComment);

        Comment savedComment = commentRepository.save(comment);

        return commentMapper.toResponse(savedComment);
    }

    @Override
    public boolean deleteComment(Long commentId) {
        this.commentRepository.deleteById(commentId);
        return true;
    }

    @Override
    public PageDTO<CommentResponse> getAllFirstLevelComment(Optional<String> sort, Optional<String> order, Optional<Integer> pageSize, Optional<Integer> pageIndex, Long postId) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Comment.class);
        Page<Comment> pageComment = commentRepository.findAllFirstLevelComments(postId, pageable);
        return PageDTOMapper.toPageDTO(pageComment, comment -> commentMapper.toResponse(comment));
    }

    @Override
    public PageDTO<CommentResponse> getAllChildComment(Optional<String> sort, Optional<String> order, Optional<Integer> pageSize, Optional<Integer> pageIndex, Long parentCommentId, Long postId) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Comment.class);
        Page<Comment> pageComment = commentRepository.findAllChildCommentsByParentCommentId(parentCommentId, postId, pageable);
        return PageDTOMapper.toPageDTO(pageComment, comment -> commentMapper.toResponse(comment));
    }
}
