package com.springbatch_assignment.domain.repository;

import com.springbatch_assignment.domain.entity.MemberMonthlyWork;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberMonthlyWorkRepository extends JpaRepository<MemberMonthlyWork, Long>{
}
