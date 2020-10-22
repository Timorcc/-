package com.qiluhospital.chemotherapy.mdt.service.impl;

import com.qiluhospital.chemotherapy.mdt.service.UserService;
import com.qiluhospital.chemotherapy.mdtbackground.entity.Doctor;
import com.qiluhospital.chemotherapy.mdtbackground.entity.SmallSecretary;
import com.qiluhospital.chemotherapy.mdtbackground.mapper.MdtDoctorMapper;
import com.qiluhospital.chemotherapy.mdtbackground.mapper.SmallSecretaryMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    @Autowired
    MdtDoctorMapper mdtDoctorMapper;

    @Autowired
    SmallSecretaryMapper smallSecretaryMapper;

    @Override
    public Map<String, Object> login(String telNum, String wxNum, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            Doctor doctor = mdtDoctorMapper.findByTelNum(telNum);
            SmallSecretary smallSecretary = smallSecretaryMapper.findByTelNum(telNum);
            log.info("login doctor is  ---==》" + doctor);
            log.info("login smallSecretary is  ---==》" + smallSecretary);

            if (doctor == null && smallSecretary == null) {
                map.put("status", 1);
                map.put("msg", "用户不存在");
                return map;
            }
            //小秘书
            if (doctor == null) {
                if (smallSecretary.getWx_num().equals(wxNum)) {
                    request.getSession().setAttribute("telNum", telNum);
                    request.getSession().setAttribute("id", smallSecretary.getId());
                    request.getSession().setAttribute("wxNum", wxNum);
                    request.getSession().setAttribute("username", smallSecretary.getUsername());
                    map.put("status", 0);
                    map.put("msg", "登录成功");
                    return map;
                }
                map.put("status", 1);
                map.put("msg", "用户登录信息错误");
                return map;
            }
            //医生
            if (smallSecretary == null) {
                if (doctor.getWx_num().equals(wxNum)) {
                    request.getSession().setAttribute("telNum", telNum);
                    request.getSession().setAttribute("id", doctor.getId());
                    request.getSession().setAttribute("wxNum", wxNum);
                    request.getSession().setAttribute("username", doctor.getUsername());
                    map.put("status", 0);
                    map.put("msg", "登录成功");
                    return map;
                }
                map.put("status", 1);
                map.put("msg", "用户登录信息错误");
                return map;
            }
        } catch (Exception e) {
            log.error(e.toString());
            map.put("status", 1);
            map.put("msg", "登录错误");
            return map;
        }
        return map;
    }
}