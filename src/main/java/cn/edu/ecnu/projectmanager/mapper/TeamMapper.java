package cn.edu.ecnu.projectmanager.mapper;

import cn.edu.ecnu.projectmanager.entity.Student;
import cn.edu.ecnu.projectmanager.entity.Team;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeamMapper {
    // Create
    @Insert("insert into team(team_id,team_name,leader_id) values(#{team_id},#{team_name},#{leader_id});")
    int addTeam(Team team);

    @Select("insert into pro_stu(team_id,stu_id) values(#{teamId},#{studentId});")
    Integer addMember(Integer teamId, String studentId);

//    // Update
//    @Update("update team set name=#{team_name},leader_id=#{leader_id} where team_id=#{team_id};")
//    int updateProject(Team team);


    // Retrieve
    @ResultType(Team.class)
    @Select("select * from team where team_id=#{team_id};")
    Team findTeamById(@Param("team_id") Integer team_id);

    @Select("select * from team where team_name=#{team_name};")
    Team findTeamByName(@Param("team_name") String team_name);

    //通过小组ID查小组成员信息
    @ResultType(Student.class)
    @Select("select * from student where stu_id in (select stu_id from pro_stu where team_id=#{team_id});")
    List<Student> findStudentByTeamId(@Param("team_id") Integer team_id);

    // 通过学生ID获取学生所在小组
    @Select("select * from team where team_id in (select team_id from pro_stu where stu_id = #{stu_id});")
    List<Team> findTeamByStudentId(@Param("stu_id") String stu_id);

    // Delete
    @Delete("delete from team where team_id=#{team_id};")
    int deleteTeamById(@Param("team_id") Integer team_id);

//    @Delete("delete from team where team_name=#{team_name};")
//    int deleteTeamByName(@Param("team_name") String team_name);

    @Delete("delete from pro_stu where team_id = #{teamId} and stu_id = #{studentId};")
    int deleteMember(Integer teamId, String studentId);

    @Select("select count(*) from team;")
    int count();

    @Select("select * from team limit #{pageSize},#{pageNumber};")
    List<Team> getTeamList(int pageSize, int pageNumber);
    @Select("select * from team")
    List<Team> listAllTeam();
}