package com.vivacon.service;

import com.vivacon.dto.request.PostReportRequest;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.PostReport;

import java.util.Optional;

public interface PostReportService {

    PostReport createPostReport(PostReportRequest postReportRequest);

    PageDTO<PostReport> getAll(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);

}
