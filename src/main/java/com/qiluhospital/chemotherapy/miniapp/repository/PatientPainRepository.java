package com.qiluhospital.chemotherapy.miniapp.repository;

import com.qiluhospital.chemotherapy.miniapp.entity.PatientPain;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PatientPainRepository extends JpaRepository<PatientPain,Long> {

    List<PatientPain> findAllByPatientId(Long patId, Sort sort);
}
