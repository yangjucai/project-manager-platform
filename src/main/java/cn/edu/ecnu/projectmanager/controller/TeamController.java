package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.JsonResult;
import cn.edu.ecnu.projectmanager.common.PageJson;
import cn.edu.ecnu.projectmanager.entity.Student;
import cn.edu.ecnu.projectmanager.entity.Team;
import cn.edu.ecnu.projectmanager.service.impl.TeamServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@Slf4j
@RequestMapping("/team")
public class TeamController {
    @Autowired
    TeamServiceImpl teamService;
    @PostMapping("/add")
    @ResponseBody
    public JsonResult add(@RequestParam String teamName, @SessionAttribute("user") Student student){
        Team team = new Team();
        team.setTeam_name(teamName);
        team.setLeader_id(student.getStu_id());
        log.error(team.toString());
        try {
            teamService.add(team);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @GetMapping("/listall")
    @ResponseBody
    public PageJson<Team> listAll(){
        PageJson<Team> page = new PageJson<>();
        page.setCode(0);
        page.setMsg("Success");
        page.setData(teamService.listAll());
        page.setCount(teamService.count());
        return page;
    }
    @GetMapping("/member")
    @ResponseBody
    public PageJson<Student> member(@RequestParam Integer teamId){
        PageJson<Student> page = new PageJson<>();
        page.setCode(0);
        page.setMsg("Success");
        page.setData(teamService.getAllMember(teamId));
        page.setCount(page.getData().size());
        log.info(teamService.getAllMember(teamId).toString());
        return page;
    }
}
