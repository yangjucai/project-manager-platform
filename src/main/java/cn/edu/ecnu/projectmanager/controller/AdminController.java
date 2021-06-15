package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.JsonResult;
import cn.edu.ecnu.projectmanager.common.PageJson;
import cn.edu.ecnu.projectmanager.entity.Admin;
import cn.edu.ecnu.projectmanager.entity.Expert;
import cn.edu.ecnu.projectmanager.mapper.ExpertMapper;
import cn.edu.ecnu.projectmanager.service.impl.AdminServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
public class AdminController {
    @Autowired
    AdminServiceImpl adminService;

    @GetMapping("/admin/info")
    @ResponseBody
    public PageJson<Admin> personalInfo(@SessionAttribute("user") Admin admin){
        Admin adminNew = adminService.findById(admin.getAdm_id());
        PageJson<Admin> page = new PageJson<>();
        page.setCount(1);
        page.getData().add(adminNew);
        page.setCode(0);
        page.setMsg("Success");
        return page;
    }
    @PostMapping("/admin/info/update")
    @ResponseBody
    public JsonResult update(@SessionAttribute("user")Admin adminOld, @RequestBody Admin admin){
        adminOld.setPassword(admin.getPassword());
        adminOld.setAdm_id(admin.getAdm_id());
        adminOld.setPhone(adminOld.getPhone());
        try{
            adminService.update(adminOld);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
}
