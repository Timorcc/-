package com.qiluhospital.chemotherapy.miniapp.view;

import lombok.Data;

@Data
public class NextOrderView {

    String nextDate;

    String preDate;

    String patName;

    Long patId;

    String tel;

    String patCard;
}
