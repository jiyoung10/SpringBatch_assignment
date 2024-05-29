package com.springbatch_assignment.domain.entity;


public enum Gender {
    F("여자"),
    M("남자");

    private final String value;

    Gender(String value){
        this.value = value;

    }
    public String getValue(){
        return value;
    }

}
