package cn.edu.ecnu.projectmanager.service;

import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.entity.Student;
import cn.edu.ecnu.projectmanager.entity.Team;

import java.util.List;

public interface StudentService {
    Student login(String username, String password)throws Exception;
    int saveOrUpdate(Student student)throws Exception;
    int add(Student student)throws Exception;

    //Student findByUsername(String username)throws Exception;
    Student findById(String id)throws Exception;
    List<Student> findByName(String name);
    List<Student> listAll();
    List<Team> getTeam(String studentId);
    boolean isExisted(String username);

    int deleteById(String id)throws Exception;
    //int deleteByUsername(String username) throws Exception;
    void joinTeam(String studentId, String teamname)throws Exception;
    List<Project> listProject(String studentId)throws Exception;
}
