package com.qiluhospital.chemotherapy.miniapp.enums;

public enum PatState {

    CURE("cure"),RECOVERY("recovery"),DEAD("dead");

    private String state;

    PatState(String state){
        this.state = state;
    }

    public String getState(){
        return state;
    }

    public void setState(String state){
        this.state = state;
    }
}
