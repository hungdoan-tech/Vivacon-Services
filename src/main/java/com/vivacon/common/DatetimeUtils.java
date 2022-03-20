package com.vivacon.common;

import com.vivacon.dto.statistic.ComboStartDateEndDate;

import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;

public class DatetimeUtils {

    private DatetimeUtils() {

    }

    public static ComboStartDateEndDate getStartDateAndEndDateInAMonth(int month, int year) {
        LocalDateTime myLocal = LocalDateTime.of(year, month, 1, 0, 0, 0, 0);
        LocalDateTime firstDayOfMonth = myLocal
                .with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime lastDayOfMonth = myLocal
                .with(TemporalAdjusters.lastDayOfMonth());
        return new ComboStartDateEndDate(firstDayOfMonth, lastDayOfMonth);
    }

    public static ComboStartDateEndDate getStartDateAndEndDateInAQuarter(int quarter, int year) {
        LocalDateTime myLocal = LocalDateTime.of(year, quarter * 3, 1, 0, 0, 0, 0);
        LocalDateTime firstDayOfQuarter = myLocal
                .with(myLocal.getMonth().firstMonthOfQuarter())
                .with(TemporalAdjusters.firstDayOfMonth());
        LocalDateTime lastDayOfQuarter = firstDayOfQuarter.plusMonths(2)
                .with(TemporalAdjusters.lastDayOfMonth());
        return new ComboStartDateEndDate(firstDayOfQuarter, lastDayOfQuarter);
    }

    public static ComboStartDateEndDate getStartDateAndEndDateInAYear(int year) {
        LocalDateTime myLocal = LocalDateTime.of(year, 12, 1, 0, 0, 0, 0);
        LocalDateTime firstDayOfYear = myLocal
                .with(TemporalAdjusters.firstDayOfYear());
        LocalDateTime lastDayOfYear = myLocal
                .with(TemporalAdjusters.lastDayOfMonth());
        return new ComboStartDateEndDate(firstDayOfYear, lastDayOfYear);
    }
}
