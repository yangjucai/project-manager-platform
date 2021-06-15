package cn.edu.ecnu.projectmanager.mapper;

import cn.edu.ecnu.projectmanager.entity.*;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ProjectMapper {
    // Create
    // 因为组队时pro_stu中team_id与project里的team_id有外键约束，所以新建队伍时加trigger添加一个项目信息；
    @Update("update project set pro_name=#{pro_name},status=#{status},grades=#{grades},type=#{type},exp_id=#{exp_id},exp_comment=#{exp_comment},tea_id=#{tea_id},tea_comment=#{tea_comment} where team_id=#{team_id}")
    @Options(useGeneratedKeys = true, keyProperty = "pro_id")
    int addProject1(Project project);

//    @Insert("insert into project(pro_id,pro_name,status,grades,type,team_id,exp_id,exp_comment,tea_id,tea_comment)" +
//            "values(#{pro_id},#{pro_name},#{status},#{grades},#{type},#{team_id},#{exp_id},#{exp_comment},#{tea_id},#{tea_comment});")
//    @Options(useGeneratedKeys = true, keyProperty = "pro_id")
//    int addProject(Project project);

    // Update
    @Update("update project set pro_name=#{pro_name},status=#{status},grades=#{grades},type=#{type},team_id=#{team_id},exp_id=#{exp_id},exp_comment=#{exp_comment},tea_id=#{tea_id},tea_comment=#{tea_comment} where pro_id=#{pro_id}")
    int updateProject(Project project);

    // Retrieve
    @ResultType(Project.class)
    @Select("select * from project where pro_id=#{pro_id};")
    Project findProjectById(@Param("pro_id") Integer pro_id);

    // Retrieve
    @ResultType(Project.class)
    @Select("select * from project where team_id=#{team_id};")
    Project findProjectByTeamId(@Param("team_id") Integer team_id);

    @ResultType(Project.class)
    @Select("select * from project where pro_name=#{pro_name};")
    Project findProjectByName(@Param("pro_name") String pro_name);

    @Select("select distinct type from project")
    List<String> getAllProjectType();

    @Select("select * from project")
    List<Project> getAllProject();

    @Select("select count(*) from project")
    int count();

    //根据项目ID查看所有file
    @ResultType(File.class)
    @Select("select * from file where pro_id=#{pro_id};")
    List<File> findFileByProjectId(@Param("pro_id") Integer pro_id);


    // Delete
    @Delete("delete from project where pro_id=#{pro_id};")
    int deleteProjectById(@Param("pro_id") Integer pro_id);

    @Delete("delete from project where pro_name=#{pro_name};")
    int deleteProjectByName(@Param("pro_name") String pro_name);
}
