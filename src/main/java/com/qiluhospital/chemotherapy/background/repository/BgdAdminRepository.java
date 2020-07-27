package com.qiluhospital.chemotherapy.background.repository;

import com.qiluhospital.chemotherapy.background.entity.BgdAdmin;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BgdAdminRepository extends JpaRepository<BgdAdmin,Long> {

    BgdAdmin findBgdAdminByUsername(String name);

    List<BgdAdmin> findAllByUsernameNot(String name);

    BgdAdmin findBgdAdminById(Long id);


}
