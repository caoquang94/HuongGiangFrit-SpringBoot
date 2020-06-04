package com.codegym.controller.admin.web;

import com.codegym.model.Career;
import com.codegym.service.Impl.CareerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class CareerController extends com.codegym.controller.admin.web.AdminBaseController {
    @Autowired
    private CareerService careerService;
    
    private String TERM = "Career";

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Career.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }


    @GetMapping("/career/")
    public ModelAndView index(){

        List<Career> career = careerService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/career/index");
        modelAndView.addObject("career",career);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("term",TERM);

        return modelAndView;
    }
    @GetMapping("/career/add")
    public ModelAndView showAddForm(){
        ModelAndView modelAndView = new ModelAndView("admin/career/add");
        modelAndView.addObject("career",new Career());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);

        return  modelAndView;
    }

    @PostMapping("/career/add")
    public ModelAndView saveAddForm(HttpServletRequest request, @ModelAttribute("career") Career career){
        //
        String uploadRootPath = request.getServletContext().getRealPath("static/upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = career.getFileImage();
        //
        Map<File, String> uploadedFiles = new HashMap();
        for (CommonsMultipartFile fileData : fileDatas) {
            String name = fileData.getOriginalFilename();
            System.out.println("Client File Name = " + name);

            if (name != null && name.length() > 0) {
                try {
                    File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                    stream.write(fileData.getBytes());
                    stream.close();
                    //
                    career.setImage(name);
                    System.out.println("Write file: " + serverFile);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                }
            }
        }
        //
        careerService.save(career);

        ModelAndView modelAndView = new ModelAndView("admin/career/add");
        modelAndView.addObject("career",new Career());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("alert",ALERT_SUCCESS);

        modelAndView.addObject("message", ACTION_ADD_SUCCESS);

        return  modelAndView;

    }


    @PostMapping("/career/edit")
    public ModelAndView saveEditForm(HttpServletRequest request,@ModelAttribute("career") Career career){
        //
        String uploadRootPath = request.getServletContext().getRealPath("static/upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = career.getFileImage();
        //
        if (fileDatas!=null){
            Map<File, String> uploadedFiles = new HashMap();
            for (CommonsMultipartFile fileData : fileDatas) {

                // Tên file gốc tại Client.
                String name = fileData.getOriginalFilename();
                System.out.println("Client File Name = " + name);

                if (name != null && name.length() > 0) {
                    try {
                        // Tạo file tại Server.
                        File serverFile = new File(uploadRootDir.getAbsolutePath() + File.separator + name);

                        // Luồng ghi dữ liệu vào file trên Server.
                        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile));
                        stream.write(fileData.getBytes());
                        stream.close();
                        //
                        career.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        //
        careerService.save(career);
        //
        ModelAndView modelAndView = new ModelAndView("admin/career/add");
        modelAndView.addObject("career",career);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_EDIT);
        modelAndView.addObject("alert",ALERT_SUCCESS);
        modelAndView.addObject("message", ACTION_EDIT_SUCCESS);
        //
        return  modelAndView;
    }

    @GetMapping("/career/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id){
        Career career = careerService.findById(id);
        if(career != null) {

            ModelAndView modelAndView = new ModelAndView("admin/career/add");
            modelAndView.addObject("career",career);
            modelAndView.addObject("action",ACTION_EDIT);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_EDIT);

            return  modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }

    }

    @GetMapping("/career/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Career career = careerService.findById(id);
        if( career != null) {
            ModelAndView modelAndView = new ModelAndView("admin/career/delete");

            modelAndView.addObject("career",career);
            modelAndView.addObject("action",ACTION_DELETE);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_DELETE);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/career/delete")
    public String deleteProvince(@ModelAttribute("career") Career career){
        careerService.remove(career.getId());
        return "redirect:/admin/career/";
    }
}
