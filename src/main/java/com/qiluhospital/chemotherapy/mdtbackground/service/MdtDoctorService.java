package com.qiluhospital.chemotherapy.mdtbackground.service;

import com.qiluhospital.chemotherapy.mdtbackground.dto.DoctorAndDepart;
import com.qiluhospital.chemotherapy.mdtbackground.entity.Doctor;

import java.util.List;


public interface MdtDoctorService {
    List<Doctor> findAll();

    List<DoctorAndDepart> findAllDoctorAndDepart();

    DoctorAndDepart findDoctorAndDepartById(Long id);

    Boolean AddDoctor(String username, String telNum, String wxNum);

    Boolean updateDoctorById(Long id, String username, String tel_num, String wx_num);

    Boolean deleteRDDByDoctorId(Long id);

    Boolean updateRDDByDoctorId(Long id, List<String> docDepartList);

    List<DoctorAndDepart> findDoctorByChatRoomId(Long id);

    Doctor findById(Long id);
}
