package com.springbatch_assignment.service;

import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class VacationService {

    public Integer getWorkHour(Integer memberId, LocalDate startDate, LocalDate endDate) {
        return 0; // 임시적으로 외근이 없다고 가정하고 0을 리턴
    }
}
