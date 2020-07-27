package com.qiluhospital.chemotherapy.miniapp.repository;

import com.qiluhospital.chemotherapy.miniapp.entity.PatUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface PatUserRepository extends JpaRepository<PatUser,Long> {

    PatUser findByOpenId(String openid);
}
