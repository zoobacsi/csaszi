package com.websystique.springboot.controller;
 
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Map;

@Controller
public class AppController {
 
    @RequestMapping("/")
    String home(ModelMap modal) {
        modal.addAttribute("title","CRUD Example");
        return "index";
    }
 
    @RequestMapping("/partials/{page}")
    String partialHandler(@PathVariable("page") final String page) {
        Map.of
        return page;
    }
 
}