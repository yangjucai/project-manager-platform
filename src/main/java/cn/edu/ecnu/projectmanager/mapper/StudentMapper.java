package cn.edu.ecnu.projectmanager.mapper;

import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.entity.Student;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface StudentMapper {
    @ResultType(Student.class)
    @Select("select * from student where stu_id=#{stu_id};")
    Student findById(String stu_id);

    @ResultType(Student.class)
    @Select("select * from student where stu_name=#{stu_name}")
    List<Student> findByName(@Param("name") String name);

    @ResultType(Student.class)
    @Select("select * from student where stu_id=#{stu_id} and password=#{password}")
    Student findByIdAndPassword(@Param("stu_id") String stu_id,@Param("password") String password);

    //通过studentID查看他的所有项目
    @ResultType(Project.class)
    @Select("select * from project where pro_id in (select pro_id from pro_stu where stu_id=#{stu_id});")
    List<Project> findProjectByStudentId(@Param("stu_id") String stu_id);


    // Create
    @Insert("insert into student(stu_id,stu_name,password,birth,sex,phone,grade,major)" +
            " values(#{stu_id},#{stu_name},#{password},#{birth},#{sex},#{phone},#{grade},#{major});")
    int addStudent(Student student);

    // Update
    @Update("update student set stu_name=#{stu_name},password=#{password},birth=#{birth},sex=#{sex},phone=#{phone},grade=#{grade},major=#{major} where stu_id=#{stu_id}")
    int updateStudent(Student student);

    // Delete
    @Delete("delete from student where stu_id=#{stu_id};")
    int deleteStudentById(@Param("stu_id") String stu_id);

    @Select("select * from student")
    List<Student> getAllStudent();
}
