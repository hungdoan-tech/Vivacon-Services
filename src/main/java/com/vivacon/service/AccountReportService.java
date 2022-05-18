package com.vivacon.service;

import com.vivacon.dto.request.AccountReportRequest;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.AccountReport;

import java.util.Optional;

public interface AccountReportService {

    AccountReport createAccountReport(AccountReportRequest accountReportRequest);

    PageDTO<AccountReport> getAll(Optional<String> order, Optional<String> sort, Optional<Integer> pageSize, Optional<Integer> pageIndex);

}
