package cn.edu.ecnu.projectmanager.service;

import cn.edu.ecnu.projectmanager.entity.Student;
import cn.edu.ecnu.projectmanager.entity.Team;

import java.util.List;

public interface TeamService {
    int add(Team team) throws Exception;
    Team getById(Integer id) throws Exception;
    //Team getByName(String name);
    //List<Team> getTeamList(int pageSize, int pageNumber);
    List<Team> listAll();
    List<Student> getAllMember(Integer teamId);
    int count();
    void addMember(Integer id, Student student)throws Exception;
    //boolean isExist(String teamname);

    int deleteById(int id)throws Exception;
    void deleteMember(Integer teamId, String studentId) throws Exception;
}
