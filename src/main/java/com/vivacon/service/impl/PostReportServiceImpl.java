package com.vivacon.service.impl;

import com.vivacon.common.utility.PageableBuilder;
import com.vivacon.dto.request.PostReportRequest;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.Post;
import com.vivacon.entity.PostReport;
import com.vivacon.exception.RecordNotFoundException;
import com.vivacon.mapper.PageMapper;
import com.vivacon.mapper.PostReportMapper;
import com.vivacon.repository.PostReportRepository;
import com.vivacon.repository.PostRepository;
import com.vivacon.service.PostReportService;
import com.vivacon.service.PostService;
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
public class PostReportServiceImpl implements PostReportService {

    private PostReportMapper postReportMapper;

    private PostReportRepository postReportRepository;

    private PostRepository postRepository;

    private PostService postService;

    public PostReportServiceImpl(PostReportMapper postReportMapper,
                                 PostReportRepository postReportRepository,
                                 PostService postService,
                                 PostRepository postRepository) {
        this.postReportMapper = postReportMapper;
        this.postReportRepository = postReportRepository;
        this.postRepository = postRepository;
        this.postService = postService;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    @Override
    public PostReport createPostReport(PostReportRequest postReportRequest) {
        Post post = postRepository.findById(postReportRequest.getPostId()).orElse(null);
        PostReport postReport = postReportMapper.toPostReport(postReportRequest);
        postReport.setActive(true);
        postReport.setPost(post);

        return postReportRepository.save(postReport);
    }

    @Override
    public PageDTO<PostReport> getAll(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex) {
        Pageable pageable = PageableBuilder.buildPage(order, sort, pageSize, pageIndex, PostReport.class);
        Page<PostReport> postReportPage = postReportRepository.findAllByActive(true, pageable);
        return PageMapper.toPageDTO(postReportPage);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    public boolean approvedPostReport(long id) {
        PostReport postReport = postReportRepository.findById(id).orElseThrow(RecordNotFoundException::new);
        postService.deactivePost(postReport.getPost().getId());
        return this.postReportRepository.deactivateById(id) > 0;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    public boolean rejectedPostReport(long id) {
        return this.postReportRepository.deactivateById(id) > 0;
    }
}
