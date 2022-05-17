package com.vivacon.service;

import com.vivacon.dto.request.CommentReportRequest;
import com.vivacon.entity.CommentReport;

public interface CommentReportService {

    CommentReport createCommentReport(CommentReportRequest commentReportRequest);

}
