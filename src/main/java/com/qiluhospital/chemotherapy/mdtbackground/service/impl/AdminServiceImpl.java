package com.qiluhospital.chemotherapy.mdtbackground.service.impl;

import com.qiluhospital.chemotherapy.mdtbackground.entity.Admin;
import com.qiluhospital.chemotherapy.mdtbackground.entity.BigSecretary;
import com.qiluhospital.chemotherapy.mdtbackground.mapper.AdminMapper;
import com.qiluhospital.chemotherapy.mdtbackground.mapper.BigSecretaryMapper;
import com.qiluhospital.chemotherapy.mdtbackground.service.AdminService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Service
@Slf4j
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    BigSecretaryMapper bigSecretaryMapper;

    @Override
    public Map<String, Object> login(String username, String password, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            if (adminMapper.findByUsername(username) != null) {
                Admin bgdAdmin = adminMapper.findByUsername(username);
                log.info("login admin is  ---==》" + bgdAdmin);
                if (!DigestUtils.md5DigestAsHex(password.getBytes()).equals(bgdAdmin.getPassword())) {
                    map.put("status", 1);
                    map.put("msg", "密码错误");
                    return map;
                }
                request.getSession().setAttribute("username", username);
                request.getSession().setAttribute("id", bgdAdmin.getId());
                map.put("status", 0);
                map.put("msg", "登录成功");
                return map;
            } else if (bigSecretaryMapper.findByTel(username)!=null) {
                BigSecretary bigSecretary = bigSecretaryMapper.findByTel(username);
                log.info("login bigSecretary is  ---==》" + bigSecretary);
                if (!password.equals(bigSecretary.getWx_num())) {
                    map.put("status", 1);
                    map.put("msg", "登录失败");
                    return map;
                }
                request.getSession().setAttribute("username", bigSecretary.getUsername());
                request.getSession().setAttribute("id", bigSecretary.getId());
                map.put("status", 0);
                map.put("msg", "登录成功");
                return map;
            }else{
                map.put("status", 1);
                map.put("msg", "用户名不存在");
                return map;
            }


//            if (bgdAdmin == null) {
//
//            }

        } catch (Exception e) {
            log.error(e.toString());
            map.put("status", 1);
            map.put("msg", "用户名或密码错误");
            return map;
        }

    }
}
