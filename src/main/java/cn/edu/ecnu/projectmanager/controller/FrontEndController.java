package cn.edu.ecnu.projectmanager.controller;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;

@Configuration
@Controller
public class FrontEndController {
    @RequestMapping("/")
    public String loginIndex() {
        return "/login.html";
    }

}