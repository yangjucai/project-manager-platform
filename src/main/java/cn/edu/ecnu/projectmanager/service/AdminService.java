package cn.edu.ecnu.projectmanager.service;

import cn.edu.ecnu.projectmanager.entity.Admin;

import java.util.List;

public interface AdminService {
    Admin login(String userId, String password)throws Exception;
    int update(Admin admin);

    //Admin findByUsername(String userId);
    Admin findById(String id);
    List<Admin> findByName(String name);
    List<Admin> listAll();
    //boolean isExisted(String username);
}
