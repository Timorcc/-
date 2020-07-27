package com.qiluhospital.chemotherapy.miniapp.repository;

import com.qiluhospital.chemotherapy.miniapp.entity.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface DoctorRepository extends JpaRepository<Doctor,Long> {

    Doctor findOneById(Long id);

    Doctor findByOpenId(String openid);

    List<Doctor> findAllByAuthenticityAndGroupId(boolean authenticity,Long groupId);

    List<Doctor> findAllByAuthenticityAndGroupIdAndType(boolean authenticity,Long groupId,String type);

    long countByDocNameContaining(String docName);

    Page<Doctor> findAllByDocNameContaining(String docName,Pageable pageable);



}
