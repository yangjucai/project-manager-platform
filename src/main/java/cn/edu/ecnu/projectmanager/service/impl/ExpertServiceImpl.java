package cn.edu.ecnu.projectmanager.service.impl;

import cn.edu.ecnu.projectmanager.entity.Expert;
import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.mapper.ExpertMapper;
import cn.edu.ecnu.projectmanager.mapper.ProjectMapper;
import cn.edu.ecnu.projectmanager.service.ExpertService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ExpertServiceImpl implements ExpertService {
    @Autowired
    private ExpertMapper expertMapper;
    @Autowired
    private ProjectMapper projectMapper;

    @Override
    public Expert login(String userId, String password) throws Exception {
        if(userId.isEmpty()){
            throw new Exception("用户名不得为空");
        }
        if(password.isEmpty()){
            throw new Exception("密码不得为空");
        }
        Expert user = expertMapper.findExpertById(userId);
        if(user == null){
            log.error("expert: {} doesn't exist.", userId);
            throw new Exception("用户不存在");
        }else if(!user.getPassword().equals(password)){
            log.error("expert: {} password wrong.", userId);
            throw new Exception("密码错误");
        }
        return user;
    }
    @Override
    public int saveOrUpdate(Expert expert) throws Exception{
        if(isExisted(expert.getExp_id())){
            verify(expert);
            return expertMapper.updateExpert(expert);
        }else {
            verify(expert);
            return add(expert);
        }
    }

    @Override
    public int add(Expert expert) throws Exception{
        if(findByUserId(expert.getExp_id())!= null){
            throw new Exception("用户名被占用");
        }
        verify(expert);
        return expertMapper.addExpert(expert);
    }

    @Override
    public Expert findByUserId(String username) throws Exception {
       Expert expert =  expertMapper.findExpertById(username);
//       if(expert == null){
//           throw new Exception("用户不存在");
//       }
       return expert;
    }

    @Override
    public Expert findById(String id) {
        return expertMapper.findExpertById(id);
    }

    @Override
    public List<Expert> findByName(String name) {
        return expertMapper.getExpertByName(name);
    }

    @Override
    public List<Expert> listAll(){
        return expertMapper.listAllExpert();
    }

    @Override
    public boolean isExisted(String exp_id) {
        return expertMapper.findExpertById(exp_id) != null;
    }

    @Override
    public int deleteById(String id)throws Exception {
        return expertMapper.deleteExpertById(id);
    }
//
//    @Override
//    public int deleteByUsername(String username) throws Exception{
//        return expertMapper.deleteExpertByUsername(username);
//    }

    @Override
    public void comment(Project project, Boolean confirm, String comment, Integer grades) throws Exception{
        if(project.getStatus().trim().equals("等待指导导师确认")){
            throw new Exception("项目尚未通过指导老师确认");
        }else if(project.getStatus().trim().equals("等待项目审核与立项") && confirm){
            project.setStatus("正在中期答辩");
        }else if(project.getStatus().trim().equals("正在中期答辩") && confirm){
            project.setStatus("项目中期审核");
        }else if(project.getStatus().trim().equals("项目中期审核") && confirm){
            project.setStatus("项目等待终期评审和确定成绩");
        }else if(project.getStatus().trim().equals("项目等待终期评审和确定成绩") && confirm){
            project.setStatus("项目完成");
        }else if(project.getStatus().trim().equals("项目完成")){
            throw new Exception("项目已结束");
        }
        if(grades == null){
            throw new Exception("未打分");
        }
        project.setGrades(grades);
        project.setExp_comment(comment);
        projectMapper.updateProject(project);
    }

    @Override
    public List<Project> listProject(String expertId) throws Exception{
        return expertMapper.findProjectByExpertId(expertId);
    }

    @Override
    public int update(Expert e) throws Exception{
        return expertMapper.updateExpert(e);
    }

    private void verify(Expert expert)throws Exception{
        if(expert.getExp_name().isEmpty()){
            throw new Exception("名字不得为空");
        }
        if(expert.getPassword().isEmpty()){
            throw new Exception("密码不能为空");
        }
        if(expert.getExp_id().isEmpty()){
            throw new Exception("用户名不能为空");
        }
    }
}
