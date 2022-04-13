package com.vivacon.service.impl;

import com.vivacon.common.PageableBuilder;
import com.vivacon.dto.response.CommentResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Comment;
import com.vivacon.mapper.CommentMapper;
import com.vivacon.mapper.PageDTOMapper;
import com.vivacon.repository.CommentRepository;
import com.vivacon.service.CommentService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }


    @Override
    public PageDTO<CommentResponse> getAll(Optional<String> sort, Optional<String> order, Optional<Integer> pageSize, Optional<Integer> pageIndex, Long postId) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Comment.class);
        Page<Comment> entityPage = commentRepository.findAllFirstLevelComments(postId, pageable);
        return PageDTOMapper.toPageDTO(entityPage, CommentResponse.class, entity -> this.commentMapper.toResponse(entity));
    }

    @Override
    public PageDTO<CommentResponse> getAllChildComment(Optional<String> sort, Optional<String> order, Optional<Integer> pageSize, Optional<Integer> pageIndex, Long parentCommentId, Long postId) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, Comment.class);
        Page<Comment> entityPage = commentRepository.findAllChildCommentsByParentCommentId(parentCommentId, postId, pageable);
        return PageDTOMapper.toPageDTO(entityPage, CommentResponse.class, entity -> this.commentMapper.toResponse(entity));
    }
}
