package com.vivacon.repository;

import com.vivacon.entity.AccountReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountReportRepository extends JpaRepository<AccountReport, Long> {


}
