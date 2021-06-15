package cn.edu.ecnu.projectmanager.mapper;

import cn.edu.ecnu.projectmanager.entity.Admin;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminMapper {
    // Create
    // Admin cannot create through this program

    // Update
    @Update("update admin set adm_name=#{adm_name},password=#{password},phone=#{phone} where adm_id=#{adm_id};")
    int updateAdmin(Admin admin);

    // Delete
    // Admin cannot delete through this program

    //Retrieve

    @ResultType(Admin.class)
    @Select("SELECT * FROM admin WHERE adm_id=#{adm_id};")
    Admin getAdminById(@Param("adm_id") String adm_id);



    @ResultType(Admin.class)
    @Select("SELECT * FROM admin WHERE adm_name=#{adm_name};")
    List<Admin> getAdminByAdmName(@Param("name") String adm_name);


    @ResultType(Admin.class)
    @Select("SELECT * FROM admin WHERE adm_id=#{adm_id} and password=#{password};")
    Admin getAdminByIdAndPassword(@Param("adm_id") String adm_id, @Param("password") String password);

    @Select("SELECT * FROM admin")
    List<Admin> getAllAdmin();
}
