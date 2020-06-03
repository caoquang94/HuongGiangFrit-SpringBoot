package com.codegym.controller.admin.web;

import com.codegym.model.FeedBack;
import com.codegym.service.Impl.FeedBackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
@RequestMapping("/admin")
public class FeedBackController extends AdminBaseController {
    @Autowired
    private FeedBackService feedBackService;

    private String TERM = "FeedBack";

    @GetMapping("/feedBack/")
    public ModelAndView index(){

        List<FeedBack> feedBacks = feedBackService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/feedBack/index");
        modelAndView.addObject("feedBacks",feedBacks);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("term",TERM);

        return modelAndView;
    }
    @GetMapping("/feedBack/add")
    public ModelAndView showAddForm(){
        ModelAndView modelAndView = new ModelAndView("user/contact");
        modelAndView.addObject("feedBack",new FeedBack());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);
        return  modelAndView;
    }

    @PostMapping("/feedBack/add")
    public String saveAddForm(@ModelAttribute("feedBack") FeedBack feedBack, Model model){
        model.addAttribute("feedBack", new FeedBack());
        feedBackService.save(feedBack);
        return "redirect:/user/index";

    }

    @GetMapping("/feedBack/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        FeedBack feedBack = feedBackService.findById(id);
        if( feedBack != null) {
            ModelAndView modelAndView = new ModelAndView("/admin/feedBack/delete");

            modelAndView.addObject("feedBack",feedBack);
            modelAndView.addObject("action",ACTION_DELETE);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_DELETE);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/feedBack/delete")
    public String deleteProvince(@ModelAttribute("feedBack") FeedBack feedBack){
        feedBackService.remove(feedBack.getId());
        return "redirect:/feedBack/";
    }
}
