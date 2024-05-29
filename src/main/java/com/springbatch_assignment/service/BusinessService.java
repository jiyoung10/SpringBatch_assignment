package com.springbatch_assignment.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class BusinessService {

    public Integer getWorkHour(Integer memberId, LocalDate startDate, LocalDate endDate) {
        return 8 * 20; // 임시적으로 8시간 근무로 가정하고 한 달 동안의 근로 시간을 리턴
    }

}
