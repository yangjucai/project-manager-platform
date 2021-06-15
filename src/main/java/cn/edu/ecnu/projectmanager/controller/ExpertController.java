package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.JsonResult;
import cn.edu.ecnu.projectmanager.common.PageJson;
import cn.edu.ecnu.projectmanager.entity.Expert;
import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.service.impl.ExpertServiceImpl;
import cn.edu.ecnu.projectmanager.service.impl.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.Console;
import java.util.List;

@Controller
@Slf4j
@RequestMapping("/expert")
public class ExpertController {
    @Autowired
    ExpertServiceImpl expertService;
    @Autowired
    ProjectServiceImpl projectService;

    @GetMapping("/info")
    @ResponseBody
    public PageJson<Expert> info(@SessionAttribute("user") Expert expert){
        return getById(expert.getExp_id());
    }

    @GetMapping("/info/getById")
    @ResponseBody
    public PageJson<Expert> getById(@RequestParam String expertId){
        Expert expert = expertService.findById(expertId);
        PageJson<Expert> page = new PageJson<>();
        page.setCode(0);
        page.setMsg("Success");
        page.setCount(1);
        page.getData().add(expert);
        return page;
    }
    @GetMapping("/listall")
    @ResponseBody
    public PageJson<Expert> listall(){
        PageJson<Expert> page = new PageJson<>();
        page.setCode(0);
        page.setMsg("Success");
        List<Expert> expertList = expertService.listAll();
        page.setData(expertList);
        page.setCount(expertList.size());
        return page;
    }

    @GetMapping("/project/list")
    @ResponseBody
    public PageJson<Project> listProject(@SessionAttribute("user") Expert expert){
        PageJson<Project> page = new PageJson<>();
        try {
            List<Project> projectList = expertService.listProject(expert.getExp_id());
            page.setCode(0);
            page.setData(projectList);
            page.setCount(projectList.size());
            page.setMsg("Success");
        }catch (Exception e){
            page.setMsg(e.getMessage());
        }
        return page;
    }


    @PostMapping("/project/comment")
    @ResponseBody
    public JsonResult comments(@RequestParam Integer grades, @RequestParam String exp_comment,
                               @RequestParam boolean confirm, @RequestParam Integer pro_id, @SessionAttribute("role") String role){
        if(!role.trim().equals("expert")){
            return JsonResult.fail("权限不足");
        }

        try {
            Project project = projectService.findById(pro_id);

            expertService.comment(project, confirm, exp_comment, grades);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @PostMapping("/info/update")
    @ResponseBody
    public JsonResult update(@RequestBody Expert expert, HttpServletRequest request, @SessionAttribute("user") Expert expertOld){
        try {
            expertOld.setPassword(expert.getPassword());
            expertOld.setExp_name(expert.getExp_name());
            expertOld.setEmail(expert.getEmail());
            expertService.saveOrUpdate(expertOld);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
}
