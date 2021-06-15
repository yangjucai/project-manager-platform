package cn.edu.ecnu.projectmanager.service.impl;

import cn.edu.ecnu.projectmanager.entity.Student;
import cn.edu.ecnu.projectmanager.entity.Team;
import cn.edu.ecnu.projectmanager.mapper.StudentMapper;
import cn.edu.ecnu.projectmanager.mapper.TeamMapper;
import cn.edu.ecnu.projectmanager.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TeamServiceImpl implements TeamService {
    @Autowired
    TeamMapper teamMapper;
    @Autowired
    StudentMapper studentMapper;
    @Override
    public int add(Team team) throws Exception{
        verifyTeam(team);
        if(teamMapper.findTeamByName(team.getTeam_name()) != null){
            throw new Exception("队伍名称已被占用");
        }
        teamMapper.addTeam(team);
        int primaryKey = teamMapper.findTeamByName(team.getTeam_name()).getTeam_id();
                // 建队以后队长自动入队
        addMember(primaryKey, studentMapper.findById(team.getLeader_id()));
        return primaryKey;
    }

    @Override
    public List<Student> getAllMember(Integer teamId) {
        return teamMapper.findStudentByTeamId(teamId);
    }

    @Override
    public Team getById(Integer id) throws Exception{
        Team team = teamMapper.findTeamById(id);
        if(team == null){
            throw new Exception("队伍不存在");
        }
        return team;
    }

//    @Override
//    public Team getByName(String name){
//        Team team = teamMapper.findTeamByName(name);
//        return team;
//    }


    @Override
    public int count() {
        return teamMapper.count();
    }

    @Override
    public int deleteById(int id) throws Exception{
        if(id < 0){
            throw new Exception("ID不能为负");
        }
        if(teamMapper.findTeamById(id) == null){
            throw new Exception("队伍不存在");
        }
        return teamMapper.deleteTeamById(id);
    }

//    @Override
//    public boolean isExist(String teamname){
//        return false;
//    }

    @Override
    public void addMember(Integer id, Student student) throws Exception{
        verifyTeam(teamMapper.findTeamById(id));
        teamMapper.addMember(id, student.getStu_id());
    }
    @Override
    public void deleteMember(Integer teamId, String studentId)throws Exception{
//        Team team = teamMapper.findTeamById(teamId);
//        verifyTeam(team);
        teamMapper.deleteMember(teamId, studentId);
    }
    private void verifyTeam(Team team)throws Exception{
        if(team == null){
            throw new Exception("队伍不存在");
        }
        if(team.getTeam_name().trim().isEmpty()){
            throw new Exception("队伍名字不得为空");
        }
        if(team.getLeader_id() == null){
            throw new Exception("未指定队长");
        }
        Student leaderFound = studentMapper.findById(team.getLeader_id());
        if(leaderFound == null){
            throw new Exception("队长不存在");
        }
    }

    @Override
    public List<Team> listAll() {
        return teamMapper.listAllTeam();
    }

}
