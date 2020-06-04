package com.codegym.controller.admin.web;

import com.codegym.model.Contact;
import com.codegym.service.Impl.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class ContactController extends com.codegym.controller.admin.web.AdminBaseController {
    @Autowired
    private ContactService contactService;
    private String TERM = "Contact";

    @GetMapping("/contact/")
    public ModelAndView index(){

        List<Contact> contacts = contactService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/contact/index");
        modelAndView.addObject("contacts",contacts);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("term",TERM);

        return modelAndView;
    }
    @GetMapping("/contact/add")
    public ModelAndView showAddForm(){
        ModelAndView modelAndView = new ModelAndView("admin/contact/add");
        modelAndView.addObject("contact",new Contact());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);

        return  modelAndView;
    }

    @PostMapping("/contact/add")
    public ModelAndView saveAddForm(HttpServletRequest request, @ModelAttribute("contact") Contact contact){
        contactService.save(contact);

        ModelAndView modelAndView = new ModelAndView("admin/contact/add");
        modelAndView.addObject("contact",new Contact());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("alert",ALERT_SUCCESS);

        modelAndView.addObject("message", ACTION_ADD_SUCCESS);

        return  modelAndView;

    }


    @PostMapping("/contact/edit")
    public ModelAndView saveEditForm(HttpServletRequest request,@ModelAttribute("contact") Contact contact){
        contactService.save(contact);
        //
        ModelAndView modelAndView = new ModelAndView("admin/contact/add");
        modelAndView.addObject("contact",contact);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_EDIT);
        modelAndView.addObject("alert",ALERT_SUCCESS);
        modelAndView.addObject("message", ACTION_EDIT_SUCCESS);
        //
        return  modelAndView;
    }

    @GetMapping("/contact/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id){
        Contact contact = contactService.findById(id);
        if(contact != null) {

            ModelAndView modelAndView = new ModelAndView("admin/contact/add");
            modelAndView.addObject("contact",contact);
            modelAndView.addObject("action",ACTION_EDIT);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_EDIT);

            return  modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }

    }

    @GetMapping("/contact/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Contact contact = contactService.findById(id);
        if( contact != null) {
            ModelAndView modelAndView = new ModelAndView("admin/contact/delete");

            modelAndView.addObject("contact",contact);
            modelAndView.addObject("action",ACTION_DELETE);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_DELETE);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/contact/delete")
    public String deleteProvince(@ModelAttribute("contact") Contact contact){
        contactService.remove(contact.getId());
        return "redirect:/admin/contact/";
    }
}
