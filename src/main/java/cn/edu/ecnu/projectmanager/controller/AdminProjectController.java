package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.JsonResult;
import cn.edu.ecnu.projectmanager.common.PageJson;
import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.service.impl.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/admin/project")
public class AdminProjectController {
    @Autowired
    ProjectServiceImpl projectService;
    @GetMapping("/list")
    @ResponseBody
    public PageJson<Project> listAll(){
        List<Project> projectList = projectService.getProjectList();
        PageJson<Project> projectPageJson = new PageJson<>();
        projectPageJson.setCode(0);
        projectPageJson.setMsg("Success");
        projectPageJson.setData(projectList);
        projectPageJson.setCount(projectList.size());
        return projectPageJson;
    }
    @GetMapping("/getByName")
    @ResponseBody
    public PageJson<Project> getByName(@RequestParam("projectName")String prjectName){
        try {
            Project project = projectService.findByName(prjectName);
            PageJson<Project> projectPageJson = new PageJson<>();
            projectPageJson.setCode(0);
            projectPageJson.setMsg("Success");
            projectPageJson.getData().add(project);
            projectPageJson.setCount(projectPageJson.getData().size());
            return projectPageJson;
        }catch (Exception e){
            return null;
        }
    }

    @PostMapping("/add")
    @ResponseBody
    public JsonResult add(@RequestBody Project project){
        try {
            projectService.add(project);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(@RequestParam Integer projectId){
        try{
            projectService.deleteById(projectId);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(@RequestBody Project project){
        try{
            log.info(project.toString());
            projectService.saveOrUpdate(project);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
}
