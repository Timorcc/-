package com.qiluhospital.chemotherapy.miniapp.view;

import com.qiluhospital.chemotherapy.miniapp.entity.Doctor;
import lombok.Data;

import java.util.List;

@Data
public class GroupAndDoctorView {

    String groupName;

    Long groupId;

    List<Doctor> doctors;
}
