package com.qiluhospital.chemotherapy.miniapp.repository;

import com.qiluhospital.chemotherapy.miniapp.entity.PatientUploadImg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PatUplImgRepository extends JpaRepository<PatientUploadImg,Long> {

    List<PatientUploadImg> findAllByReportId(Long reportId);

}
