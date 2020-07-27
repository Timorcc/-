package com.qiluhospital.chemotherapy.miniapp.repository;

import com.qiluhospital.chemotherapy.miniapp.entity.Patient;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Transactional
public interface PatientRepository extends JpaRepository<Patient,Long> {

    List<Patient> findAllByGroupIdAndConfirmState(Long groupId,String confirmState);

    List<Patient> findAllByUserId(Long userId);

    List<Patient> findAllByUserIdAndConfirmState(Long userId,String confirmState);

    List<Patient> findAllByUserIdAndConfirmStateNot(Long userId,String comfirmState);

    List<Patient> findAllByUserIdAndStateAndConfirmState(Long userId,String state,String comState);

    Patient findOneById(Long id);

    Patient findPatientByIdeCard(String ideCard);

    List<Patient> findAllByGroupIdAndConfirmStateAndState(Long groupId,String confirmState,String state);

    Page<Patient> findAllByGroupIdAndConfirmStateAndState(Long groupId, String confirmState,String state, Pageable pageable);

    List<Patient> findAllByGroupIdAndPatNameContainingAndConfirmStateAndState(Long groupId,String name,String confirmState,String state);

    List<Patient> findAllByPatNameContainingAndConfirmStateAndState(String name,String confirmState,String state);

    List<Patient> findAllByGroupIdAndConfirmStateAndPreCertainVisitDateAndState(Long groupId, String confirmState, Date preCertainVistDate,String state);

    Page<Patient> findAllByRetIsNormalAndGroupIdAndConfirmStateAndState(Boolean isNormal, Long groupId,String comState,String state,Pageable pageable);

    Page<Patient> findAllByPainDateIsNotNullAndConfirmStateAndActuPainDateIsNotNullAndState(String confirmState,String state,Pageable pageable);

    List<Patient> findAllByConfirmStateAndCertainVisitDateBetween(String confirmState,Date beginDate,Date endDate);

    long countAllByPatNameContainingAndConfirmStateAndState(String patName,String comfirmState,String state);

    long countAllByPatNameContainingAndState(String patName,String state);

    Page<Patient> findAllByConfirmStateAndState(String comfirmState,String state,Pageable pageable);

    Page<Patient> findAllByState(String state,Pageable pageable);

    Page<Patient> findAllByConfirmStateAndStateAndPatNameContaining(String comfirmState,String state,String patName,Pageable pageable);

    Page<Patient> findAllByStateAndPatNameContaining(String state,String patName,Pageable pageable);

    long countAllByStateAndConfirmState(String state,String comState);

    long countAllByState(String state);

    List<Patient> findAllByUserIdAndPainDateAndConfirmState(Long usrId,Date painDate,String comfirmState);

    List<Patient> findAllByUserIdAndUploadDateAndConfirmState(Long usrId,Date uploadDate,String comfirmState);


}
