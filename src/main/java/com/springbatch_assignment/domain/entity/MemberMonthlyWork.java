package com.springbatch_assignment.domain.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MemberMonthlyWork {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    //@Column(name = "year_month", length = 8, nullable = false)
    @Column(name = "year_month", nullable = false)
    private String yearMonth;

    @Column(name = "member_id", nullable = false)
    private Integer memberId;

    @Column(name= "hour", nullable = false)
    private Integer hour;

    @Column(name= "creation_date", nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date creationDate;

}
