package cn.edu.ecnu.projectmanager.service.impl;

import cn.edu.ecnu.projectmanager.entity.File;
import cn.edu.ecnu.projectmanager.mapper.FileMapper;
import cn.edu.ecnu.projectmanager.service.FileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private FileMapper fileMapper;

    @Override
    public int add(File file) {
        return fileMapper.addFile(file);
    }

    @Override
    public int saveOrUpdate(File file){
        if(fileMapper.findFileByName(file.getFile_name()) != null){
            return fileMapper.updateFile(file);
        }
        return fileMapper.addFile(file);
    }

//    @Override
//    public File getById(Integer id) {
//        return null;
//    }
//
//    @Override
//    public File getByName(String name) {
//        return null;
//    }
//
//    @Override
//    public List<File> getAllFileList() {
//        return null;
//    }

    @Override
    public int count() {
        return 0;
    }

    @Override
    public int deleteById(int id) {
        return 0;
    }


}
