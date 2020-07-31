package com.qiluhospital.chemotherapy.miniapp.repository;

import com.qiluhospital.chemotherapy.miniapp.entity.UploadDate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Transactional
public interface UploadDateRepository extends JpaRepository<UploadDate,Long> {
    List<UploadDate> findByUserId(Long userId);
}
