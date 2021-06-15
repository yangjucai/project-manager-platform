package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.PageJson;
import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.entity.Student;
import cn.edu.ecnu.projectmanager.entity.Team;
import cn.edu.ecnu.projectmanager.service.impl.ProjectServiceImpl;
import cn.edu.ecnu.projectmanager.service.impl.TeamServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/project")
public class ProjectController {
    @Autowired
    ProjectServiceImpl projectService;
    @Autowired
    TeamServiceImpl teamService;
    @GetMapping("/info")
    @ResponseBody
    public PageJson<Project> getById(@RequestParam Integer projectId){
        PageJson<Project> page = new PageJson<>();
        Project project = projectService.findById(projectId);
        page.setCode(0);
        page.setMsg("Success");
        page.getData().add(project);
        page.setCount(1);
        return page;
    }

    @GetMapping("/listall")
    @ResponseBody
    public PageJson<Project> listAll(){
        List<Project> projectList =  projectService.getProjectList();
        PageJson<Project> page = new PageJson<>();
        page.setCode(0);
        page.setMsg("Success");
        page.setData(projectList);
        page.setCount(projectList.size());
        return page;
    }

    @GetMapping("/team/info")
    @ResponseBody
    public PageJson<Team> getTeam(@RequestParam Integer projectId){
        PageJson<Team> page = new PageJson<>();
        page.setCode(0);
        page.setMsg("Success");
        Team team = projectService.getProjectTeam(projectId);
        page.getData().add(team);
        return page;
    }
    @GetMapping("/students")
    @ResponseBody
    public PageJson<Student> getStudent(@RequestParam Integer projectId){
        PageJson<Student> page = new PageJson<>();
        page.setCode(0);
        page.setMsg("Success");
        List<Student> studentList = teamService.getAllMember(projectService.getProjectTeam(projectId).getTeam_id());
        page.setData(studentList);
        page.setCount(studentList.size());
        return page;
    }
    @GetMapping("/type/list")
    @ResponseBody
    public PageJson<String> returnTypeList(){
        PageJson<String> pageJson = new PageJson<>();
        pageJson.getData().add("创新");
        pageJson.getData().add("创业");
        pageJson.getData().add("双创");
        pageJson.setCount(3);
        return pageJson;
    }
}
