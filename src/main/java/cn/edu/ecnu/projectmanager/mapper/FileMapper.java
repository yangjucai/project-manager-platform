package cn.edu.ecnu.projectmanager.mapper;

import cn.edu.ecnu.projectmanager.entity.File;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FileMapper {
    // Create
    @Insert("insert into file(file_id,file_name,pro_id,url) values(#{file_id},#{file_name},#{pro_id},#{url);")
    int addFile(File file);

    // Retrieve
    @ResultType(File.class)
    @Select("select * from file where file_id=#{file_id};")
    File findFileById(@Param("file_id") Integer file_id);

    @Update("update file set file_name=#{file_name}, pro_id=#{pro_id}, url=#{url} where id=#{id}")
    int updateFile(File file);

    @ResultType(File.class)
    @Select("select * from file where file_name=#{file_name};")
    File findFileByName(@Param("file_name") String file_name);

    @ResultType(File.class)
    @Select("select * from file limit #{begin},#{length};")
    List<File> getFile(@Param("length") Integer length, @Param("begin") Integer begin);

    // Delete
    @Delete("delete from file where file_id=#{file_id};")
    int deleteFileById(@Param("file_id") Integer file_id);

    // Count
    @Select("select count(*) from file")
    int countFiles();
}
