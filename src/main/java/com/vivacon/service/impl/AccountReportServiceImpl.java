package com.vivacon.service.impl;

import com.vivacon.dto.request.AccountReportRequest;
import com.vivacon.entity.Account;
import com.vivacon.entity.AccountReport;
import com.vivacon.mapper.AccountReportMapper;
import com.vivacon.repository.AccountReportRepository;
import com.vivacon.service.AccountReportService;
import com.vivacon.service.AccountService;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.NonTransientDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLException;

@Service
public class AccountReportServiceImpl implements AccountReportService {

    private AccountReportMapper accountReportMapper;

    private AccountReportRepository accountReportRepository;

    private AccountService accountService;

    public AccountReportServiceImpl(AccountReportMapper accountReportMapper, AccountReportRepository accountReportRepository, AccountService accountService) {
        this.accountReportMapper = accountReportMapper;
        this.accountReportRepository = accountReportRepository;
        this.accountService = accountService;
    }

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = {DataIntegrityViolationException.class, NonTransientDataAccessException.class, SQLException.class, Exception.class})
    @Override
    public AccountReport createAccountReport(AccountReportRequest accountReportRequest) {
        Account accountById = accountService.getAccountById(accountReportRequest.getAccountId());
        AccountReport accountReport = accountReportMapper.toAccountReport(accountReportRequest);
        accountReport.setActive(true);
        accountReport.setAccount(accountById);

        return accountReportRepository.save(accountReport);
    }
}
