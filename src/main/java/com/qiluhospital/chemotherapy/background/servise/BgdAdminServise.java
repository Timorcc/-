package com.qiluhospital.chemotherapy.background.servise;

import com.qiluhospital.chemotherapy.background.entity.BgdAdmin;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

public interface BgdAdminServise {

    Map<String,Object> login(String username, String password, HttpServletRequest request);

    Map<String,Object> updatePasswd(Long id,String oldPasswd,String newPasswd);

    List<BgdAdmin> getNotRootAdmins();

    boolean addAdmin(String adminName,String description);

    boolean deleteAdmin(Long id);

    boolean resetAdmin(String id);

}
