package com.vivacon.service;

import com.vivacon.dto.request.PostReportRequest;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.PostReport;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;
import java.util.Optional;

public interface PostReportService {

    PostReport createPostReport(PostReportRequest postReportRequest);

    PageDTO<PostReport> getAll(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    boolean deleteById(long id);
}
