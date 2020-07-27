package com.qiluhospital.chemotherapy.background.servise.impl;

import com.qiluhospital.chemotherapy.background.entity.BgdAdmin;
import com.qiluhospital.chemotherapy.background.repository.BgdAdminRepository;
import com.qiluhospital.chemotherapy.background.servise.BgdAdminServise;
import com.qiluhospital.chemotherapy.miniapp.repository.DocGroupRepository;
import com.qiluhospital.chemotherapy.miniapp.repository.DoctorRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class BgdAdminServiseImpl implements BgdAdminServise {

    @Autowired
    private BgdAdminRepository bgdAdminRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private DocGroupRepository docGroupRepository;


    @Override
    public Map<String, Object> login(String username, String password, HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        try {
            BgdAdmin bgdAdmin = bgdAdminRepository.findBgdAdminByUsername(username);
            if (bgdAdmin == null) {
                map.put("status", 1);
                map.put("msg", "用户名不存在");
                return map;
            }
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
        } catch (Exception e) {
            log.error(e.toString());
            map.put("status", 1);
            map.put("msg", "用户名或密码错误");
            return map;
        }

    }

    @Override
    public Map<String, Object> updatePasswd(Long id, String oldPasswd, String newPasswd) {
        Map<String,Object> res = new HashMap<>();
        try {
            BgdAdmin bgdAdmin = bgdAdminRepository.getOne(id);
            if (DigestUtils.md5DigestAsHex(oldPasswd.getBytes()).equals(bgdAdmin.getPassword())){
                if(oldPasswd.equals(newPasswd)){
                    res.put("status", 2);
                    res.put("msg", "新密码不得与原密码相同!");
                    return res;
                }
                bgdAdmin.setPassword(DigestUtils.md5DigestAsHex(newPasswd.getBytes()));
                bgdAdminRepository.save(bgdAdmin);
                res.put("status", 0);
                res.put("msg", "成功修改密码！");
            }else {
                res.put("status", 1);
                res.put("msg", "原密码错误！");
            }
            return res;
        }catch (Exception e){
            log.error(e.toString());
            res.put("status", 3);
            res.put("msg", "密码修改失败！");
            return res;
        }
    }

    @Override
    public List<BgdAdmin> getNotRootAdmins() {
        try {
            return bgdAdminRepository.findAllByUsernameNot("root");
        }catch (Exception e){
            log.error(e.toString());
            return null;
        }
    }

    @Override
    public boolean addAdmin(String adminName, String description) {
        try {
            BgdAdmin bgdAdmin = new BgdAdmin();
            bgdAdmin.setUsername(adminName);
            bgdAdmin.setDescription(description);
            //默认初始密码123456
            bgdAdmin.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            bgdAdminRepository.save(bgdAdmin);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public boolean deleteAdmin(Long id) {
        try {
            BgdAdmin bgdAdmin = bgdAdminRepository.findBgdAdminById(id);
            bgdAdminRepository.delete(bgdAdmin);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }

    @Override
    public boolean resetAdmin(String id) {
        try {
            BgdAdmin bgdAdmin = bgdAdminRepository.findBgdAdminById(Long.valueOf(id));
            bgdAdmin.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));
            bgdAdminRepository.save(bgdAdmin);
            return true;
        }catch (Exception e){
            log.error(e.toString());
            return false;
        }
    }


}
