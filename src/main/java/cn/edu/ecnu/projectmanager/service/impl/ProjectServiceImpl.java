package cn.edu.ecnu.projectmanager.service.impl;

import cn.edu.ecnu.projectmanager.entity.File;
import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.entity.Team;
import cn.edu.ecnu.projectmanager.mapper.ProjectMapper;
import cn.edu.ecnu.projectmanager.mapper.StudentMapper;
import cn.edu.ecnu.projectmanager.mapper.TeacherMapper;
import cn.edu.ecnu.projectmanager.mapper.TeamMapper;
import cn.edu.ecnu.projectmanager.service.ProjectService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ProjectServiceImpl implements ProjectService {
    @Autowired
    private ProjectMapper projectMapper;
    @Autowired
    private StudentMapper studentMapper;
    @Autowired
    private TeacherMapper teacherMapper;
    @Autowired
    private TeamMapper teamMapper;

    @Override
    public Team getProjectTeam(Integer projectId) {
        Team team = teamMapper.findTeamById(projectMapper.findProjectById(projectId).getTeam_id());
        return team;
    }

    @Override
    public int add(Project project) throws Exception{
        verifyProject(project);
        return projectMapper.addProject1(project);
    }

    @Override
    public List<File> getFileList(Integer id) {
        return projectMapper.findFileByProjectId(id);
    }

    @Override
    public boolean exists(Integer id) {
        Project project = projectMapper.findProjectById(id);
        return project != null;
    }

    @Override
    public int saveOrUpdate(Project project) throws Exception{
//        log.info(project.toString());
        verifyProject(project);
        if(project.getPro_id() != null && exists(project.getPro_id())){
            return projectMapper.updateProject(project);
        }else {
            if(projectMapper.findProjectByName(project.getPro_name()) != null){
                throw new Exception("项目名已被占用");
            }
            project.setStatus("等待指导导师确认");
            log.info(project.toString());
            return projectMapper.addProject1(project);
        }
    }

    @Override
    public List<Project> getProjectList() {
        return projectMapper.getAllProject();
    }

    @Override
    public Project findById(Integer id) {
        return projectMapper.findProjectById(id);
    }

    @Override
    public Project findByName(String name) throws Exception{
        return projectMapper.findProjectByName(name);
    }

    @Override
    public int count() {
        return projectMapper.count();
    }

    @Override
    public int deleteById(Integer id) throws Exception{
        return projectMapper.deleteProjectById(id);
    }

    @Override
    public int termProject(Integer id) throws Exception {
        Project project = projectMapper.findProjectById(id);
//        log.info("update"+project.toString());
        String statusNow = project.getStatus().trim();
        if(statusNow.equals("等待指导导师确认") || statusNow.equals("等待项目审核与立项")){
            throw new Exception("项目初期不能提前终结");
        }
        if(statusNow.equals("项目完成")){
            throw new Exception("项目已结束");
        }
//        if(statusNow.equals("HANDLE")){
//            throw new Exception("项目已终结, 请勿重复提交");
//        }
//        project.setStatus("HANDLE");
//        projectMapper.updateProject(project);
        projectMapper.deleteProjectById(project.getPro_id());

        return project.getPro_id();
    }

//    @Override
//    public int deleteByName(String name) throws Exception{
//        return projectMapper.deleteProjectByName(name);
//    }

//    @Override
//    public List<String> getAllProjectType() {
//        return projectMapper.getAllProjectType();
//    }

    private void verifyProject(Project project) throws Exception{
        if(project.getPro_name() == null){
            throw new Exception("项目名称不得为空");
        }
        if(project.getType() == null){
            throw new Exception("项目类型不得为空");
        }
//        if(project.getStatus() == null){
//            throw new Exception("项目状态不得为空");
//        }
//        log.info("pass");
        if(project.getStatus() != null && (project.getStatus().equals("项目提前终结") || project.getStatus().equals("项目完成"))){
            throw new Exception("项目已经结束, 无法修改");
        }
        if(teamMapper.findTeamById(project.getTeam_id()) == null){
            throw new Exception("队伍不存在");
        }
        if(project.getTeam_id() == null){
            throw new Exception("未指定队伍");
        }
    }
}
