package com.qiluhospital.chemotherapy.mdt.service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface UserService {

    Map<String, Object> login(String telNum, String wxNum, HttpServletRequest request);

}
