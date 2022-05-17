package com.vivacon.service;

import com.vivacon.dto.request.PostReportRequest;
import com.vivacon.entity.PostReport;

public interface PostReportService {

    PostReport createPostReport(PostReportRequest postReportRequest);

}
