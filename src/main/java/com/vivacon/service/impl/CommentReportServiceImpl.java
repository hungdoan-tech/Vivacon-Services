package com.vivacon.service.impl;

import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.request.CommentReportRequest;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Comment;
import com.vivacon.entity.CommentReport;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.mapper.CommentReportMapper;
import com.vivacon.mapper.PageMapper;
import com.vivacon.repository.CommentReportRepository;
import com.vivacon.repository.CommentRepository;
import com.vivacon.service.CommentReportService;
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
public class CommentReportServiceImpl implements CommentReportService {

    private CommentReportMapper commentReportMapper;

    private CommentReportRepository commentReportRepository;

    private CommentRepository commentRepository;

    private CommentService commentService;

    public CommentReportServiceImpl(CommentReportMapper commentReportMapper,
                                    CommentReportRepository commentReportRepository,
                                    CommentRepository commentRepository,
                                    CommentService commentService) {
        this.commentReportMapper = commentReportMapper;
        this.commentReportRepository = commentReportRepository;
        this.commentRepository = commentRepository;
        this.commentService = commentService;
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

    @Override
    public PageDTO<CommentReport> getAll(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, CommentReport.class);
        Page<CommentReport> commentReportPage = commentReportRepository.findAllByActive(true, pageable);
        return PageMapper.toPageDTO(commentReportPage);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    public boolean approvedCommentReport(long id) {
        CommentReport commentReport = commentReportRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        commentService.deactivateComment(commentReport.getComment().getId());
        return commentReportRepository.deactivateById(id) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    public boolean rejectedCommentReport(long id) {
        return commentReportRepository.deactivateById(id) > 0;
    }

    @Override
    public CommentReport getDetailCommentReport(Long commentReportId) {
        return this.commentReportRepository.findById(commentReportId).orElse(null);
    }
}
