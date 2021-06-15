package cn.edu.ecnu.projectmanager.mapper;

import cn.edu.ecnu.projectmanager.entity.Admin;
import cn.edu.ecnu.projectmanager.entity.Expert;
import cn.edu.ecnu.projectmanager.entity.Project;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface ExpertMapper {
    // Create
    @Insert("insert into expert(exp_id,exp_name,password,email) values(#{exp_id},#{exp_name},#{password},#{email});")
    int addExpert(Expert expert);

    // Update
    @Update("update expert set exp_name=#{exp_name},password=#{password},email=#{email} where exp_id=#{exp_id};")
    int updateExpert(Expert expert);

    // Delete
    @Delete("delete from expert where exp_id=#{exp_id};")
    int deleteExpertById(@Param("exp_id") String exp_id);


    // Retrieve
    @ResultType(Expert.class)
    @Select("select * from expert where exp_id=#{exp_id};")
    Expert findExpertById(@Param("exp_id") String exp_id);

    @ResultType(Expert.class)
    @Select("select * from expert where exp_name=#{exp_name};")
    List<Expert> getExpertByName(@Param("exp_name") String exp_name);

    @ResultType(Expert.class)
    @Select("select * from expert")
    List<Expert> listAllExpert();

    @ResultType(Expert.class)
    @Select("SELECT * FROM expert WHERE exp_id=#{exp_id} and password=#{password};")
    Admin getExpertByIdAndPassword(@Param("exp_id") String exp_id, @Param("password") String password);

    // Count
    @Select("select count(*) from expert;")
    int countExpert();


    //根据expertID查看所有project
    @ResultType(Project.class)
    @Select("select * from project where exp_id=#{exp_id}")
    List<Project> findProjectByExpertId(@Param("exp_id") String exp_id);


}
