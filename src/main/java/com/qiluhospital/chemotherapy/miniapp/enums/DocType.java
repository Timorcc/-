package com.qiluhospital.chemotherapy.miniapp.enums;

public enum DocType {

    DOCTOR("doctor"), NURSE("nurse");

    private String type;

    DocType(String type){
        this.type = type;
    }

    public String getType(){
        return type;
    }


}
