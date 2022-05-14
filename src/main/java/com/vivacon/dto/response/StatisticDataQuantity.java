package com.vivacon.dto.response;

public class StatisticDataQuantity {

    private Long totalPostCount;

    private Long totalAccountCount;

    public StatisticDataQuantity(Long totalPostCount, Long totalAccountCount) {
        this.totalPostCount = totalPostCount;
        this.totalAccountCount = totalAccountCount;
    }

    public Long getTotalPostCount() {
        return totalPostCount;
    }

    public void setTotalPostCount(Long totalPostCount) {
        this.totalPostCount = totalPostCount;
    }

    public Long getTotalAccountCount() {
        return totalAccountCount;
    }

    public void setTotalAccountCount(Long totalAccountCount) {
        this.totalAccountCount = totalAccountCount;
    }
}
