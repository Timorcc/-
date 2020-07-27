package com.qiluhospital.chemotherapy.miniapp.enums;

public enum PatConfirmState {

    COMFIRMED("已确认"),UNCOMFIRMED("未确认"),CANCELLED("已注销");

    private String state;

    PatConfirmState(String state){
        this.state = state;
    }

    public String getState(){
        return this.state;
    }

    public void setState(String state){
        this.state = state;
    }

}
