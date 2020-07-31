package com.qiluhospital.chemotherapy.miniapp.repository;

import com.qiluhospital.chemotherapy.miniapp.entity.PatReport;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface PatReportRepository extends JpaRepository<PatReport,Long> {

    List<PatReport> findAllByPatIdAndIsNormal(Long patId, Boolean isNormal, Sort sort);

    PatReport findPatReportByBind(String bind);

//    boolean save(PatReport patReport);


}
