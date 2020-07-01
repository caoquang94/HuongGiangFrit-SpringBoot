package com.codegym.controller.admin.web;

import com.codegym.model.Categories;
import com.codegym.model.Product;
import com.codegym.service.Impl.CategoriesService;
import com.codegym.service.Impl.ProductService;
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
public class ProductController extends com.codegym.controller.admin.web.AdminBaseController {
    private String TERM = "Product";
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoriesService categoriesService;
    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Product.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }


    @GetMapping("/")
    public ModelAndView index(){
//        product.setId_d(product.getCategories().getDeleted());
        List<Product> products = productService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/product/index");
        modelAndView.addObject("products",products);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("term",TERM);

        return modelAndView;
    }
    @GetMapping("/product/add")
    public ModelAndView showAddForm(){
        List<Categories> categories = categoriesService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/product/add");
        modelAndView.addObject("product",new Product(new Categories()));
        modelAndView.addObject("categories",categories);
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);

        return  modelAndView;
    }

    @PostMapping("/product/add")
    public ModelAndView saveAddForm(HttpServletRequest request, @ModelAttribute("product") Product product){
        //
        String uploadRootPath = request.getServletContext().getRealPath("static/upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = product.getFileImage();
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
                    product.setImage(name);
                    System.out.println("Write file: " + serverFile);
                } catch (Exception e) {
                    System.out.println("Error Write file: " + name);
                }
            }
        }
        //
        product.setId_c(product.getCategories().getId());
        List<Categories> categories = categoriesService.findAll();
        productService.save(product);

        ModelAndView modelAndView = new ModelAndView("admin/product/add");
        modelAndView.addObject("product",new Product(new Categories()));
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("alert",ALERT_SUCCESS);

        modelAndView.addObject("message", ACTION_ADD_SUCCESS);

        return  modelAndView;

    }


    @PostMapping("/product/edit")
    public ModelAndView saveEditForm(HttpServletRequest request,@ModelAttribute("product") Product product){
        //
        String uploadRootPath = request.getServletContext().getRealPath("static/upload");
        System.out.println("uploadRootPath=" + uploadRootPath);

        File uploadRootDir = new File(uploadRootPath);
        //
        // Tạo thư mục gốc upload nếu nó không tồn tại.
        if (!uploadRootDir.exists()) {
            uploadRootDir.mkdirs();
        }
        CommonsMultipartFile[] fileDatas = product.getFileImage();
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
                        product.setImage(name);
                        System.out.println("Write file: " + serverFile);
                    } catch (Exception e) {
                        System.out.println("Error Write file: " + name);
                    }
                }
            }
        }
        //
        List<Categories> categories = categoriesService.findAll();
        productService.save(product);
        //
        ModelAndView modelAndView = new ModelAndView("admin/product/add");
        modelAndView.addObject("product",product);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_EDIT);
        modelAndView.addObject("alert",ALERT_SUCCESS);
        modelAndView.addObject("message", ACTION_EDIT_SUCCESS);
        //
        return  modelAndView;
    }

    @GetMapping("/product/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id){

        Product product = productService.findById(id);
        if(product != null) {
            List<Categories> categories = categoriesService.findAll();
            ModelAndView modelAndView = new ModelAndView("admin/product/add");
            modelAndView.addObject("product",product);
            modelAndView.addObject("categories",categories);
            modelAndView.addObject("action",ACTION_EDIT);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_EDIT);

            return  modelAndView;

        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }

    }

    @GetMapping("/product/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Product product = productService.findById(id);
        if( product != null) {
            ModelAndView modelAndView = new ModelAndView("admin/product/delete");

            modelAndView.addObject("product",product);
            modelAndView.addObject("action",ACTION_DELETE);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_DELETE);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/product/delete")
    public String deleteProvince(@ModelAttribute("product") Product product){
        productService.remove(product.getId());
        return "redirect:/admin/";
    }
}
