package com.vivacon.service.impl;

import com.vivacon.common.PageableBuilder;
import com.vivacon.dto.request.CommentRequest;
import com.vivacon.dto.response.CommentResponse;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Comment;
import com.vivacon.mapper.CommentMapper;
import com.vivacon.mapper.PageDTOMapper;
import com.vivacon.repository.CommentRepository;
import com.vivacon.service.CommentService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

@Service
public class CommentServiceImpl implements CommentService {

    private CommentRepository commentRepository;
    private CommentMapper commentMapper;

    public CommentServiceImpl(CommentRepository commentRepository, CommentMapper commentMapper) {
        this.commentRepository = commentRepository;
        this.commentMapper = commentMapper;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    @Override
    public CommentResponse createComment(CommentRequest commentRequest) {
        Comment comment = commentMapper.toComment(commentRequest);
        comment.setActive(true);
        Comment savedComment = commentRepository.save(comment);

//        List<Attachment> attachments = postRequest.getAttachments()
//                .stream().map(attachment -> new Attachment(
//                        attachment.getActualName(),
//                        attachment.getUniqueName(),
//                        attachment.getUrl(),
//                        savedPost))
//                .collect(Collectors.toList());
//        commentRepository.saveAll(attachments);

        return commentMapper.toResponse(savedComment);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    public boolean deleteComment(Long commentId) {
        this.commentRepository.deleteById(commentId);
        return true;
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
