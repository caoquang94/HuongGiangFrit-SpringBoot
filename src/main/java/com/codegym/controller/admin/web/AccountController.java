package com.codegym.controller.admin.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
@Controller
public class AccountController {
    @GetMapping("/login")
    public ModelAndView login(Principal principal){
        if (principal==null){
            ModelAndView modelAndView = new ModelAndView();
            modelAndView.addObject("userName", "");
            modelAndView.setViewName("admin/login");
            return modelAndView;
        }
        else {
            return new ModelAndView("error.404");
        }
    }
    @GetMapping("/error")
    public String error(){
        return "error.404";
    }
}
