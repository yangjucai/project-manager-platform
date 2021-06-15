package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.JsonResult;
import cn.edu.ecnu.projectmanager.common.PageJson;
import cn.edu.ecnu.projectmanager.entity.Expert;
import cn.edu.ecnu.projectmanager.service.impl.ExpertServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Slf4j
@Controller
@RequestMapping("/admin/expert")
public class AdminExpertController {
    @Autowired
    ExpertServiceImpl expertService;
    @GetMapping("/info")
    @ResponseBody
    public PageJson<Expert> info(String expertId){
        Expert expert = expertService.findById(expertId);
        PageJson<Expert> pageJson = new PageJson<>();
        if(expert != null){
            pageJson.setCount(1);
            pageJson.setMsg("Success");
            pageJson.setCode(0);
            pageJson.getData().add(expert);
        }
        return pageJson;
    }
    @PostMapping("/add")
    @ResponseBody
    public JsonResult add(@RequestBody Expert expert){
        try {
            expertService.add(expert);
        }catch (Exception e){
            log.debug(e.getMessage());
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @PostMapping("/delete")
    @ResponseBody
    public JsonResult delete(@RequestParam String expertId){
        try {
            expertService.deleteById(expertId);
        }catch (Exception e){
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
    @PostMapping("/update")
    @ResponseBody
    public JsonResult update(@RequestBody Expert expert){
        try {
            expertService.saveOrUpdate(expert);
        }catch (Exception e){
            log.error("ERROR IN UPDATE");
            return JsonResult.fail(e.getMessage());
        }
        return JsonResult.ok();
    }
}
