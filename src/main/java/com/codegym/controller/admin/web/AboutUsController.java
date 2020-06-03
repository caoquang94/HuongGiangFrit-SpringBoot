package com.codegym.controller.admin.web;

import com.codegym.controller.admin.web.AdminBaseController;
import com.codegym.model.AboutUs;
import com.codegym.service.Impl.AboutUsService;
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
public class AboutUsController extends AdminBaseController {
    @Autowired
    private AboutUsService aboutUsService;

    private final  String TERM = "About Us";

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == AboutUs.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }


    @GetMapping("/aboutUs/")
    public ModelAndView index(){

        List<AboutUs> aboutUs = aboutUsService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/aboutUs/index");
        modelAndView.addObject("aboutUs",aboutUs);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("term",TERM);

        return modelAndView;
    }
    @GetMapping("/aboutUs/add")
    public ModelAndView showAddForm(){
        ModelAndView modelAndView = new ModelAndView("admin/aboutUs/add");
        modelAndView.addObject("aboutUs",new AboutUs());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);

        return  modelAndView;
    }

    @PostMapping("/aboutUs/add")
    public ModelAndView saveAddForm(HttpServletRequest request, @ModelAttribute("aboutUs") AboutUs aboutUs){
        //
        String uploadRootPath = request.getServletContext().getRealPath("static/upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = aboutUs.getFileImage();
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
                    aboutUs.setImage(name);
                    System.out.println("Write file: " + serverFile);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                }
            }
        }
        //
        aboutUsService.save(aboutUs);

        ModelAndView modelAndView = new ModelAndView("admin/aboutUs/add");
        modelAndView.addObject("aboutUs",new AboutUs());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("alert",ALERT_SUCCESS);

        modelAndView.addObject("message", ACTION_ADD_SUCCESS);

        return  modelAndView;

    }


    @PostMapping("/aboutUs/edit")
    public ModelAndView saveEditForm(HttpServletRequest request,@ModelAttribute("aboutUs") AboutUs aboutUs){
        //
        String uploadRootPath = request.getServletContext().getRealPath("static/upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = aboutUs.getFileImage();
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
                        aboutUs.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        //
        aboutUsService.save(aboutUs);
        //
        ModelAndView modelAndView = new ModelAndView("/admin/aboutUs/add");
        modelAndView.addObject("aboutUs",aboutUs);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_EDIT);
        modelAndView.addObject("alert",ALERT_SUCCESS);
        modelAndView.addObject("message", ACTION_EDIT_SUCCESS);
        //
        return  modelAndView;
    }

    @GetMapping("/aboutUs/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id){
        AboutUs aboutUs = aboutUsService.findById(id);
        if(aboutUs != null) {

            ModelAndView modelAndView = new ModelAndView("/admin/aboutUs/add");
            modelAndView.addObject("aboutUs",aboutUs);
            modelAndView.addObject("action",ACTION_EDIT);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_EDIT);

            return  modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }

    }

    @GetMapping("/aboutUs/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        AboutUs aboutUs = aboutUsService.findById(id);
        if( aboutUs != null) {
            ModelAndView modelAndView = new ModelAndView("/admin/aboutUs/delete");

            modelAndView.addObject("aboutUs",aboutUs);
            modelAndView.addObject("action",ACTION_DELETE);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_DELETE);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/aboutUs/delete")
    public String deleteProvince(@ModelAttribute("aboutUs") AboutUs aboutUs){
        aboutUsService.remove(aboutUs.getId());
        return "redirect:/admin/aboutUs/";
    }
}
