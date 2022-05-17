package com.vivacon.controller;

import com.vivacon.common.constant.Constants;
import com.vivacon.dto.request.AccountReportRequest;
import com.vivacon.entity.AccountReport;
import com.vivacon.service.AccountReportService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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

}
