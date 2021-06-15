package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.JsonResult;
import cn.edu.ecnu.projectmanager.common.PageJson;
import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.entity.Student;
import cn.edu.ecnu.projectmanager.entity.Team;
import cn.edu.ecnu.projectmanager.service.impl.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@Slf4j
@RequestMapping("/student")
public class StudentController {
    @Autowired
    StudentServiceImpl studentService;
    @Autowired
    TeacherServiceImpl teacherService;
    @Autowired
    ExpertServiceImpl expertService;
    @Autowired
    ProjectServiceImpl projectService;
    @Autowired
    TeamServiceImpl teamService;
    @Autowired
    FileServiceImpl fileService;

    @GetMapping("/listall")
    @ResponseBody
    public PageJson<Student> listAll(){
        List<Student> students = studentService.listAll();
        PageJson<Student> page = new PageJson<>();
        page.setCode(0);
        page.setMsg("Success");
        page.setCount(students.size());
        page.setData(students);
        return page;
    }

    @GetMapping("/info")
    @ResponseBody
    public PageJson<Student> info(@SessionAttribute("user")Student student){
        return getById(student.getStu_id());
    }

    @GetMapping("/info/getById")
    @ResponseBody
    public PageJson<Student> getById(@RequestParam String studentId){
        PageJson<Student> page = new PageJson<>();
        Student student;
        try {
            student = studentService.findById(studentId);
        }catch (Exception e){
            page.setCode(-1);
            page.setMsg(e.getMessage());
            return page;
        }
        page.getData().add(student);
        if(student != null)
            page.setCount(1);
        return page;
    }
    @PostMapping("/info/update")
    @ResponseBody
    public JsonResult updateInfo(@RequestBody Student student, @SessionAttribute("user") Student studentOrigin){
        try {
            student.setStu_id(studentOrigin.getStu_id());
            if(student.getPassword() == null){
                student.setPassword(studentOrigin.getPassword());
            }
            log.info(student.toString());
            studentService.saveOrUpdate(student);
        }catch (Exception e){
            log.error("Student {} error, origin {}", student.getStu_id(), studentOrigin.getStu_id());
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok("OK");
    }

    @PostMapping("/project/update")
    @ResponseBody
    public JsonResult saveOrUpdate(@RequestBody Project project, @SessionAttribute("role") String role){
        JsonResult jsonResult = new JsonResult();
        try {
            projectService.saveOrUpdate(project);
            Integer primaryKey = projectService.findByName(project.getPro_name()).getPro_id();
            jsonResult.set("projectId", primaryKey);
            jsonResult.setOk();
            jsonResult.set("msg", "ok");
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return jsonResult;
    }
    @GetMapping("/project/list")
    @ResponseBody
    public PageJson<Project> listProject(@SessionAttribute("user") Student student){
        PageJson<Project> page = new PageJson<>();
        log.info("ListProject");
        try {
            List<Project> projectList = studentService.listProject(student.getStu_id());
            page.setData(projectList);
            page.setCount(projectList.size());
        }catch (Exception e){
            page.setMsg(e.getMessage());
            page.setCode(-1);
            return page;
        }
        return page;
    }
    @PostMapping("/project/term")
    @ResponseBody
    public JsonResult term(@RequestParam Integer projectId){
        try {
            projectService.termProject(projectId);
        }catch (Exception e){
            log.info("exception", e.getMessage());
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @PostMapping("/team/join")
    @ResponseBody
    public JsonResult joinTeam(@RequestParam Integer teamId, @SessionAttribute("user") Student student, @SessionAttribute("role") String role){
        try {
            String teamname = teamService.getById(teamId).getTeam_name();
            verifyLogin(role);
            studentService.joinTeam(student.getStu_id(), teamname);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @PostMapping("/team/leave")
    @ResponseBody
    public JsonResult leaveTeam(@RequestParam Integer teamId, @SessionAttribute("role") String role,
                                @SessionAttribute("user") Student student){
        try {
            verifyLogin(role);
            teamService.deleteMember(teamId, student.getStu_id());
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    private void verifyLogin(String role) throws Exception{
        if(!role.trim().equals("student")){
            throw new Exception("未登录权限不足");
        }
    }
    @GetMapping("/team/list")
    @ResponseBody
    public PageJson<Team> listTeam(@SessionAttribute("user") Student student){
        PageJson<Team> pageJson = new PageJson<>();
        List<Team> teamList = studentService.getTeam(student.getStu_id());
        pageJson.setData(teamList);
        pageJson.setCount(teamList.size());
        pageJson.setCode(0);
        pageJson.setMsg("success");
        return pageJson;
    }
}
