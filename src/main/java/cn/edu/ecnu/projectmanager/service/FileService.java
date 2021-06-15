package cn.edu.ecnu.projectmanager.service;

import cn.edu.ecnu.projectmanager.entity.File;

import java.util.List;

public interface FileService {
    int add(File file);
    //File getById(Integer id);
    //File getByName(String name);
    //List<File> getAllFileList();
    int count();
    int saveOrUpdate(File file);

    int deleteById(int id);
}
