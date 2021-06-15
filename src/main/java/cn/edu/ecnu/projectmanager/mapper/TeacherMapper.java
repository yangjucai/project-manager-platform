package cn.edu.ecnu.projectmanager.mapper;

import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.entity.Teacher;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface TeacherMapper {
    // Create
    @Insert("insert into teacher(tea_id,tea_name,password,sex,phone) values(#{tea_id},#{tea_name},#{password},#{sex},#{phone});")
    int addTeacher(Teacher teacher);

    // Update
    @Update("update teacher set tea_name=#{tea_name},password=#{password},sex=#{sex},phone=#{phone} where tea_id=#{tea_id};")
    int updateTeacher(Teacher teacher);

    // Retrieve
    @Select("select * from teacher where tea_id=#{tea_id};")
    Teacher findTeacherById(@Param("tea_id") String tea_id);

    @Select(("select * from teacher"))
    List<Teacher> listAllTeacher();

    @Select("select * from teacher where tea_name=#{tea_name};")
    List<Teacher> findTeacherByName(@Param("tea_name") String tea_name);

    //通过teacher id查看他的所有项目
    @Select("select * from project where tea_id=#{tea_id};")
    List<Project> findProjectByTeacherId(@Param("tea_id") String tea_id);

    // Delete
    @Delete("delete from teacher where tea_id=#{tea_id};")
    int deleteTeacherById(@Param("tea_id") String tea_id);

    @Select("select * from teacher;")
    List<Teacher> listAll();
}
