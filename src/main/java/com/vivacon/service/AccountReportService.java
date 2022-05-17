package com.vivacon.service;

import com.vivacon.dto.request.AccountReportRequest;
import com.vivacon.entity.AccountReport;

public interface AccountReportService {

    AccountReport createAccountReport(AccountReportRequest accountReportRequest);

}
