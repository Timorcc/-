package com.qiluhospital.chemotherapy.miniapp.repository;

import com.qiluhospital.chemotherapy.miniapp.entity.PatVisDateMsg;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Transactional
public interface PatVisDateMsgRepository extends JpaRepository<PatVisDateMsg,Long> {

    List<PatVisDateMsg> findAllByPatIdAndCerVisitDate(Long patId, Date cerVisDate);

    List<PatVisDateMsg> findAllByPatIdAndHasHandle(Long patId,boolean hasHandle);

    PatVisDateMsg findPatVisDateMsgById(Long id);

    List<PatVisDateMsg> findAllByPatIdAndHasHandleAndCerVisitDateBefore(Long patId, Boolean hasHandle, Date cerVisDate);

    List<PatVisDateMsg> findAllByPatIdAndHasHandleAndCerVisitDateAfter(Long patId,Boolean hasHandle,Date cerVisDate);

}
