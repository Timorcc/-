package com.qiluhospital.chemotherapy.background.controller;

import com.qiluhospital.chemotherapy.background.servise.BgdDoctorServise;
import com.qiluhospital.chemotherapy.background.view.DocView;
import com.qiluhospital.chemotherapy.miniapp.entity.DocGroup;
import com.qiluhospital.chemotherapy.miniapp.entity.Doctor;
import com.qiluhospital.chemotherapy.util.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @param
 * @author liuguoan
 * @date 2022-01-18
 * @Description 分组&&医生相关controller
 * @return
 */

@Controller
public class BgdDoctorController {

    @Autowired
    private BgdDoctorServise bgdDoctorServise;

    private static int PAGESIZE = 6;


    /**
    * @describe 查询所有的医生 || 根据医生名字模糊查询
    * @param    request
    * @return
    *
    */
    @RequestMapping(value = "background/doclist", method = RequestMethod.GET)
    public String docList(HttpServletRequest request, Model model) {
        String page = request.getParameter("page");
        String docName = request.getParameter("docName");
        int pg = 1;
        long count;
        //没有查询条件时，显示第一页
        if (docName == null) {
            count = bgdDoctorServise.countAllDoc();
        } else {
            count = bgdDoctorServise.countByDocName(docName);
        }
        if (page != null) {
            pg = Integer.parseInt(page);
        }
        List<DocView> docViews = bgdDoctorServise.getDocList(pg - 1, PAGESIZE, docName);
        model.addAttribute("docName", docName);
        model.addAttribute("docViews", docViews);
        model.addAttribute("currentPage", pg);
        model.addAttribute("count", count);
        model.addAttribute("pagesize", PAGESIZE);
        return "doc_list";
    }

    /**
     * @describe 确定||取消医生认证
     * @param    state:yes||cancel
     * @return   ResponseResult
     *
     */
    @RequestMapping(value = "background/doc/auth/{state}", method = RequestMethod.POST)
    @ResponseBody
    public ResponseResult authDoc(HttpServletRequest request, @PathVariable("state") String state) {
        String docId = request.getParameter("id");
        ResponseResult result = bgdDoctorServise.authDoc(Long.valueOf(docId), state);
        return result;
    }

    /**
    * @describe 点击编辑按钮后，查询所有的医生组，跳转到修改医生分组和类型页面
    * @param    request
    * @return
    *
    */
    @RequestMapping(value = "background/doctor/grouplist", method = RequestMethod.GET)
    public String docGroupList(HttpServletRequest request, Model model) {
        String docId = request.getParameter("id");
        //查询所有的医生组，返回给前端
        List<DocGroup> docGroups = bgdDoctorServise.getAllDocGroup();
        Doctor doctor = bgdDoctorServise.getDocById(Long.valueOf(docId));
        model.addAttribute("docGroups", docGroups);
        model.addAttribute("doctor", doctor);
        return "doc_add_group";
    }

    /*修改医生分组和类型*/
    @RequestMapping(value = "background/doctor/group/alter", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> modDocGroup(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String docId = request.getParameter("docId");
        String groupId = request.getParameter("groupId");
        String type = request.getParameter("type");
        boolean state = bgdDoctorServise.modDocGroupAndType(Long.valueOf(docId), Long.valueOf(groupId), type);
        map.put("state", state);
        return map;
    }

    /*显示所有的医生分组*/
    @RequestMapping(value = "background/docGroupList", method = RequestMethod.GET)
    public String docGroupHtml(Model model) {
        List<DocGroup> docGroups = bgdDoctorServise.getAllDocGroup();
        model.addAttribute("docGroups", docGroups);
        return "group_list";
    }

    /*删除一个分组*/
    @RequestMapping(value = "background/doctor/group/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delDocGroup(HttpServletRequest request) {
        Map<String, Object> map = new HashMap<>();
        String groupId = request.getParameter("id");
        boolean state = bgdDoctorServise.delDocGroup(Long.valueOf(groupId));
        map.put("state", state);
        return map;
    }

    /*添加或者修改分组页面显示*/
    @RequestMapping(value = "background/group/list/{type}", method = RequestMethod.GET)
    public String addGroupHtml(HttpServletRequest request, Model model, @PathVariable("type") String type) {
        if (type.equals("edit")) {
            String id = request.getParameter("id");
            DocGroup docGroup = bgdDoctorServise.getDocGroupById(Long.valueOf(id));
            model.addAttribute("docGroup", docGroup);
        }
        model.addAttribute("type", type);
        return "group_add_edit";
    }


    /*添加或者修改一个分组*/
    @RequestMapping(value = "background/group/list/post/{type}", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delDocGroup(HttpServletRequest request, @PathVariable("type") String type) {
        Map<String, Object> map = new HashMap<>();
        String groupName = request.getParameter("groupName").trim();
        String description = request.getParameter("description").trim();
        boolean state = false;
        if (type.equals("edit")) {
            String groupId = request.getParameter("groupId");
            state = bgdDoctorServise.editDocGroup(groupName, description, Long.valueOf(groupId));
        }
        if (type.equals("add")) {
            state = bgdDoctorServise.addDocGroup(groupName, description);
        }
        map.put("type", type);
        map.put("state", state);
        return map;
    }
}
