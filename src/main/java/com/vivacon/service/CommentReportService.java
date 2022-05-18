package com.vivacon.service;

import com.vivacon.dto.request.CommentReportRequest;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.CommentReport;

import java.util.Optional;

public interface CommentReportService {

    CommentReport createCommentReport(CommentReportRequest commentReportRequest);

    PageDTO<CommentReport> getAll(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);

}
