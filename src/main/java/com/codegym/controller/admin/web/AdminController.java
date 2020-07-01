package com.codegym.controller.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AdminController {
    @GetMapping("/admin")
    public String Dashboard(){
        return "admin/product/index";
    }
}
