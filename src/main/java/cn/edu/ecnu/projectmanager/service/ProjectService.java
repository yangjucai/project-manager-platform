package cn.edu.ecnu.projectmanager.service;

import cn.edu.ecnu.projectmanager.entity.File;
import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.entity.Team;

import java.util.List;

public interface ProjectService {
    int add(Project project) throws Exception;
    int saveOrUpdate(Project project) throws Exception;

    List<Project> getProjectList();
    Project findById(Integer id);
    Project findByName(String name)throws Exception;
    int count();
    boolean exists(Integer id);

    int termProject(Integer id) throws Exception;
    int deleteById(Integer id)throws Exception;
    //int deleteByName(String name)throws Exception;
    List<File> getFileList(Integer id);
    Team getProjectTeam(Integer projectId);
    //List<String> getAllProjectType();
}
