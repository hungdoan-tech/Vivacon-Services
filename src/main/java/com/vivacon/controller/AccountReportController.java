package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.request.AccountReportRequest;
import com.vivacon.dto.sorting_filtering.PageDTO;
import com.vivacon.entity.AccountReport;
import com.vivacon.service.AccountReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Optional;

@Api(value = "Account Report Controller")
@RestController
@RequestMapping(value = Constants.API_V1 + "/accountReport")
public class AccountReportController {

    private AccountReportService accountReportService;

    public AccountReportController(AccountReportService accountReportService) {
        this.accountReportService = accountReportService;
    }

    /**
     * This endpoint is used to provide creating account report feature
     *
     * @param accountReportRequest
     * @return AccountReport
     */
    @ApiOperation(value = "Creating account report")
    @PostMapping()
    public AccountReport createAccountReport(@Valid @RequestBody AccountReportRequest accountReportRequest) {
        return this.accountReportService.createAccountReport(accountReportRequest);
    }

    @ApiOperation(value = "Get list account report based on criteria")
    @GetMapping()
    public PageDTO<AccountReport> getAll(
            @RequestParam(value = "_order", required = false) Optional<String> order,
            @RequestParam(value = "_sort", required = false) Optional<String> sort,
            @RequestParam(value = "limit", required = false) Optional<Integer> pageSize,
            @RequestParam(value = "page", required = false) Optional<Integer> pageIndex) {
        return accountReportService.getAll(order, sort, pageSize, pageIndex);
    }
}
