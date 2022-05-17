package com.vivacon.service.impl;

import com.vivacon.dto.request.PostReportRequest;
import com.vivacon.entity.Post;
import com.vivacon.entity.PostReport;
import com.vivacon.mapper.PostReportMapper;
import com.vivacon.repository.PostReportRepository;
import com.vivacon.repository.PostRepository;
import com.vivacon.service.PostReportService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class PostReportServiceImpl implements PostReportService {

    private PostReportMapper postReportMapper;

    private PostReportRepository postReportRepository;

    private PostRepository postRepository;

    public PostReportServiceImpl(PostReportMapper postReportMapper, PostReportRepository postReportRepository, PostRepository postRepository) {
        this.postReportMapper = postReportMapper;
        this.postReportRepository = postReportRepository;
        this.postRepository = postRepository;
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

}
