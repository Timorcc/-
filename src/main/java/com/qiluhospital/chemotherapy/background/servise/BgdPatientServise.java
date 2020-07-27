package com.qiluhospital.chemotherapy.background.servise;

import com.qiluhospital.chemotherapy.background.view.PatView;
import com.qiluhospital.chemotherapy.miniapp.entity.Patient;

import java.util.List;

public interface BgdPatientServise {

    long countAllPat(String state);

    long countPatByPatName(String patName,String state);

    List<PatView> getAllPat(int pageIndex, int pageSize, String patName,String state);

    boolean setPatState(Long patId,String state);


}
