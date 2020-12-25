package com.project.ecommerce.form;

import com.project.ecommerce.dto.VendorStatisticDto;

import java.io.Serializable;
import java.util.List;

public class VendorStatisticForm implements Serializable {
    private static final long serialVersionUID = -8162914317344406449L;
    private List<VendorStatisticDto> statisticDtoList;
    private Long totalIncome;
    private Long incomeLast30Days;
    private Long todayIncome;


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public List<VendorStatisticDto> getStatisticDtoList() {
        return statisticDtoList;
    }

    public void setStatisticDtoList(List<VendorStatisticDto> statisticDtoList) {
        this.statisticDtoList = statisticDtoList;
    }

    public Long getTotalIncome() {
        return totalIncome;
    }

    public void setTotalIncome(Long totalIncome) {
        this.totalIncome = totalIncome;
    }

    public Long getIncomeLast30Days() {
        return incomeLast30Days;
    }

    public void setIncomeLast30Days(Long incomeLast30Days) {
        this.incomeLast30Days = incomeLast30Days;
    }

    public Long getTodayIncome() {
        return todayIncome;
    }

    public void setTodayIncome(Long todayIncome) {
        this.todayIncome = todayIncome;
    }
}
