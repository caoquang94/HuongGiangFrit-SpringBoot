package com.codegym.controller.admin.web;

import com.codegym.model.Categories;
import com.codegym.model.Product;
import com.codegym.service.Impl.CategoriesService;
import com.codegym.service.Impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.support.ByteArrayMultipartFileEditor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class CategoriesController extends AdminBaseController {
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private ProductService productService;
    
    private String TERM = "Categories";

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        if (target.getClass() == Categories.class) {

            // Đăng ký để chuyển đổi giữa các đối tượng multipart thành byte[]
            dataBinder.registerCustomEditor(byte[].class, new ByteArrayMultipartFileEditor());
        }
    }


    @GetMapping("/categories/")
    public ModelAndView index(){

        List<Categories> categorieses = categoriesService.findAll();
        ModelAndView modelAndView = new ModelAndView("admin/categories/index");
        modelAndView.addObject("categorieses",categorieses);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("term",TERM);

        return modelAndView;
    }
    @GetMapping("/categories/add")
    public ModelAndView showAddForm(){
        ModelAndView modelAndView = new ModelAndView("admin/categories/add");
        modelAndView.addObject("categories",new Categories());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);

        return  modelAndView;
    }

    @PostMapping("/categories/add")
    public ModelAndView saveAddForm(HttpServletRequest request, @ModelAttribute("categories") Categories categories){
        categoriesService.save(categories);
        ModelAndView modelAndView = new ModelAndView("admin/categories/add");
        modelAndView.addObject("categories",new Categories());
        modelAndView.addObject("action",ACTION_ADD);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_ADD);
        modelAndView.addObject("alert",ALERT_SUCCESS);

        modelAndView.addObject("message", ACTION_ADD_SUCCESS);

        return  modelAndView;

    }


    @PostMapping("/categories/edit")
    public ModelAndView saveEditForm(HttpServletRequest request,@ModelAttribute("categories") Categories categories){
        categoriesService.save(categories);
        //
        ModelAndView modelAndView = new ModelAndView("/admin/categories/edit");
        modelAndView.addObject("categories",categories);
        modelAndView.addObject("action",ACTION_EDIT);
        modelAndView.addObject("term",TERM);
        modelAndView.addObject("title",TITLE_EDIT);
        modelAndView.addObject("alert",ALERT_SUCCESS);
        modelAndView.addObject("message", ACTION_EDIT_SUCCESS);
        //
        return  modelAndView;
    }

    @GetMapping("/categories/edit/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id){
        Categories categories = categoriesService.findById(id);
        if(categories != null) {

            ModelAndView modelAndView = new ModelAndView("/admin/categories/edit");
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

    @GetMapping("/categories/delete/{id}")
    public ModelAndView showDeleteForm(@PathVariable Long id){
        Categories categories = categoriesService.findById(id);
        if( categories != null) {
            ModelAndView modelAndView = new ModelAndView("/admin/categories/delete");

            modelAndView.addObject("categories",categories);
            modelAndView.addObject("action",ACTION_DELETE);
            modelAndView.addObject("term",TERM);
            modelAndView.addObject("title",TITLE_DELETE);
            return modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }
    }

    @PostMapping("/categories/delete")
    public String deleteProvince(@ModelAttribute("categories") Categories categories){
        List<Product> productList = productService.findAllByCategories(categories.getId());
        for (Product product: productList) {
            product.setDeleted((short) 1);
            productService.save(product);
        }
        categoriesService.remove(categories.getId());
        return "redirect:/admin/categories/";
    }
}
