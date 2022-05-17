package com.vivacon.service.impl;

import com.vivacon.dto.request.CommentReportRequest;
import com.vivacon.entity.Comment;
import com.vivacon.entity.CommentReport;
import com.vivacon.mapper.CommentReportMapper;
import com.vivacon.repository.CommentReportRepository;
import com.vivacon.repository.CommentRepository;
import com.vivacon.service.CommentReportService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class CommentReportServiceImpl implements CommentReportService {

    private CommentReportMapper commentReportMapper;

    private CommentReportRepository commentReportRepository;

    private CommentRepository commentRepository;

    public CommentReportServiceImpl(CommentReportMapper commentReportMapper, CommentReportRepository commentReportRepository, CommentRepository commentRepository) {
        this.commentReportMapper = commentReportMapper;
        this.commentReportRepository = commentReportRepository;
        this.commentRepository = commentRepository;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    @Override
    public CommentReport createCommentReport(CommentReportRequest commentReportRequest) {
        Comment comment = commentRepository.findById(commentReportRequest.getCommentId()).orElse(null);
        CommentReport commentReport = commentReportMapper.toCommentReport(commentReportRequest);
        commentReport.setActive(true);
        commentReport.setComment(comment);

        return commentReportRepository.save(commentReport);
    }
}
