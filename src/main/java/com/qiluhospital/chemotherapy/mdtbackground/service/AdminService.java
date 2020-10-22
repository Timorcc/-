package com.qiluhospital.chemotherapy.mdtbackground.service;


import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AdminService {


    Map<String, Object> login(String username, String password, HttpServletRequest request);


}
