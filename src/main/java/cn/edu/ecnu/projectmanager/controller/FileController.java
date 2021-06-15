package cn.edu.ecnu.projectmanager.controller;

import cn.edu.ecnu.projectmanager.common.JsonResult;
import cn.edu.ecnu.projectmanager.common.PageJson;
import cn.edu.ecnu.projectmanager.entity.Project;
import cn.edu.ecnu.projectmanager.service.impl.FileServiceImpl;
import cn.edu.ecnu.projectmanager.service.impl.ProjectServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@Slf4j
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileServiceImpl fileService;
    @Autowired
    private ProjectServiceImpl projectService;
    @Value("${file.path}")
    private String filePath;
    // where the server stores files

    @PostMapping("/upload")
    @ResponseBody
    public JsonResult upload(@RequestParam("file")MultipartFile file, HttpServletRequest request, @RequestParam Integer projectId)throws FileNotFoundException{
        File upload = null;
        String fileName = projectId.toString()+"_"+file.getOriginalFilename();
        StringBuffer url = request.getRequestURL();
        log.info("requestUrl----"+url.toString());
        upload = new File(filePath);
        if(!upload.exists()){
            upload.mkdirs();
        }
        log.info("upload url:"+upload.getAbsolutePath());
        try{
            String filePath = upload.toString()+File.separator+fileName;
            log.info(filePath);
//            System.setProperty("sun.jnu.encoding","utf-8");
            FileUtils.copyInputStreamToFile(file.getInputStream(), new File(filePath));
        }catch (IOException e){
            return JsonResult.fail(e.getMessage());
        }
        String downloadUrl = StringUtils.replace(url.toString(), "upload", "download");
        log.info("replaceUrl-----"+downloadUrl);
        Map<String, String> map = new HashMap<>();

        cn.edu.ecnu.projectmanager.entity.File newFile = new cn.edu.ecnu.projectmanager.entity.File();
        newFile.setUrl(downloadUrl+"/"+fileName);
        newFile.setPro_id(projectId);
        newFile.setFile_name(fileName);
        fileService.saveOrUpdate(newFile);
        return JsonResult.ok();
    }
    @GetMapping("/download/{name}")
    @ResponseBody
    public void download(@PathVariable("name") String name,HttpServletResponse response) throws  IOException{
        File file = new File(filePath+File.separator+name);
        log.info(file.toString());
        if(file.exists()){
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName="+name);
            FileInputStream inputStream = new FileInputStream(file);
            byte[] data = new byte[(int)file.length()];
            int length = inputStream.read(data);
            inputStream.close();
            // finish reading from server
            OutputStream outputStream = response.getOutputStream();
            outputStream.write(data);
            outputStream.flush();
            outputStream.close();
        }
    }
    @GetMapping("/listall")
    @ResponseBody
    public PageJson<cn.edu.ecnu.projectmanager.entity.File> list(@RequestParam Integer projectId){
        List<cn.edu.ecnu.projectmanager.entity.File> fileList = projectService.getFileList(projectId);
        PageJson<cn.edu.ecnu.projectmanager.entity.File> page = new PageJson<>();
        page.setCode(0);
        page.setMsg("Success");
        page.setCount(fileList.size());
        page.setData(fileList);
        return page;
    }
}
