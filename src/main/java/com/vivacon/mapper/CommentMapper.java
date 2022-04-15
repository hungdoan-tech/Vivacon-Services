package com.vivacon.mapper;

import com.vivacon.common.AuditableHelper;
import com.vivacon.dto.request.CommentRequest;
import com.vivacon.dto.response.CommentResponse;
import com.vivacon.entity.AuditableEntity;
import com.vivacon.entity.Comment;
import com.vivacon.repository.CommentRepository;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class CommentMapper {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentMapper.class);

    private ModelMapper mapper;

    private AuditableHelper auditableHelper;

    private CommentRepository commentRepository;

    public CommentMapper(ModelMapper mapper,
                         AuditableHelper auditableHelper,
                         CommentRepository commentRepository
    ) {
        this.mapper = mapper;
        this.auditableHelper = auditableHelper;
        this.commentRepository = commentRepository;
    }

    public Comment toComment(CommentRequest commentResponse) {
        Comment comment = this.mapper.map(commentResponse, Comment.class);
        AuditableEntity auditableEntity = auditableHelper.updateAuditingCreatedFields(comment, null);
        auditableEntity.setLastModifiedAt(LocalDateTime.now());
        return (Comment) auditableEntity;
    }

    public CommentResponse toResponse(Object object) {
        try {
            Comment comment = (Comment) object;
            CommentResponse postResponse = mapper.map(comment, CommentResponse.class);
            Long postId = Long.valueOf(0);
            if (comment != null && comment.getPost() != null) {
                postId = comment.getPost().getId();
            } else {
                postId = null;
            }
            long totalCountComment = commentRepository.getCountingChildComments(comment.getId(), postId);
            postResponse.setTotalChildComments(totalCountComment);
            return postResponse;
        } catch (ClassCastException ex) {
            LOGGER.info(ex.getMessage());
            return null;
        }
    }

}