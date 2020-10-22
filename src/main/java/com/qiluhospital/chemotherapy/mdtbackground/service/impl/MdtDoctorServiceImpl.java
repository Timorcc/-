package com.qiluhospital.chemotherapy.mdtbackground.service.impl;


import com.qiluhospital.chemotherapy.mdtbackground.dto.DoctorAndDepart;
import com.qiluhospital.chemotherapy.mdtbackground.entity.Doctor;
import com.qiluhospital.chemotherapy.mdtbackground.mapper.MdtDoctorMapper;
import com.qiluhospital.chemotherapy.mdtbackground.service.MdtDoctorService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class MdtDoctorServiceImpl implements MdtDoctorService {
    @Autowired
    MdtDoctorMapper mdtDoctorMapper;

    @Override
    public List<Doctor> findAll() {
        try {
            return mdtDoctorMapper.findAll();
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public List<DoctorAndDepart> findAllDoctorAndDepart() {
        try {
            return mdtDoctorMapper.findAllDoctorAndDepart();
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public DoctorAndDepart findDoctorAndDepartById(Long id) {
        try {
            return mdtDoctorMapper.findDoctorAndDepartById(id);
        } catch (Exception e) {
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public Boolean AddDoctor(String username, String telNum, String wxNum) {
        try {
            return mdtDoctorMapper.AddDoctor(username, telNum, wxNum);
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public Boolean updateDoctorById(Long id, String username, String tel_num, String wx_num) {
        try {
            return mdtDoctorMapper.updateDoctorById(id, username, tel_num, wx_num);
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public Boolean deleteRDDByDoctorId(Long id) {
        try {
            return mdtDoctorMapper.deleteRDDByDoctorId(id);
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public Boolean updateRDDByDoctorId(Long id, List<String> docDepartList) {
        try {
            mdtDoctorMapper.deleteRDDByDoctorId(id);
            for (String departId : docDepartList
            ) {
                mdtDoctorMapper.addRDD(id, Long.valueOf(departId));
            }
            return true;
        } catch (Exception e) {
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public List<DoctorAndDepart> findDoctorByChatRoomId(Long id) {
        try {
            //return doctorMapper.findDoctorByDepartId(id);
            return mdtDoctorMapper.findDoctorByChatRoomId(id);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public Doctor findById(Long id) {
        try {
            return mdtDoctorMapper.findById(id);
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }
}
