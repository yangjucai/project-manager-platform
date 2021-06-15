package cn.edu.ecnu.projectmanager.service.impl;

import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.entity.Teacher;
import cn.edu.ecnu.projectmanager.mapper.TeacherMapper;
import cn.edu.ecnu.projectmanager.service.TeacherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class TeacherServiceImpl implements TeacherService {
    @Autowired
    private TeacherMapper teacherMapper;

    @Override
    public Teacher login(String username, String password) throws Exception{
        if(username.isEmpty()){
            throw new Exception("用户名不得为空");
        }
        if(password.isEmpty()){
            throw new Exception("密码不得为空");
        }
        Teacher user = teacherMapper.findTeacherById(username);
        if(user == null){
            log.error("student: {} doesn't exist.", username);
            throw new Exception("用户不存在");
        }else if(!user.getPassword().equals(password)){
            log.error("student: {} password wrong.", username);
            throw new Exception("密码错误");
        }
        return user;
    }

    @Override
    public int saveOrUpdate(Teacher teacher) {
        if(teacherMapper.findTeacherById(teacher.getTea_id())!=null){
            return teacherMapper.updateTeacher(teacher);
        }else{
            return teacherMapper.addTeacher(teacher);
        }
    }

    @Override
    public int add(Teacher teacher) {
        return teacherMapper.addTeacher(teacher);
    }
//
//    @Override
//    public Teacher findByUsername(String username) {
//        return teacherMapper.findTeacherById(username);
//    }

    @Override
    public Teacher findById(String id) {
        return teacherMapper.findTeacherById(id);
    }

    @Override
    public List<Teacher> findByName(String name) {
        return teacherMapper.findTeacherByName(name);
    }

    @Override
    public List<Teacher> listAll() {
        return teacherMapper.listAll();
    }

//    @Override
//    public boolean isExisted(String username) {
//        return teacherMapper.findTeacherById(username) != null;
//    }

    @Override
    public int deleteById(String id) {
        return teacherMapper.deleteTeacherById(id);
    }

//    @Override
//    public int deleteByUsername(String username) {
//        return teacherMapper.deleteTeacherByUsername(username);
//    }

    @Override
    public List<Project> getProjectList(String id) {
        return teacherMapper.findProjectByTeacherId(id);
    }
}
