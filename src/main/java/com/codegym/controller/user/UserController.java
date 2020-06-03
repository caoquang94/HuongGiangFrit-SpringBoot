package com.codegym.controller.user;

import com.codegym.controller.admin.web.AdminBaseController;
import com.codegym.model.*;
import com.codegym.service.Impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.config.annotation.RedirectViewControllerRegistration;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import java.sql.SQLOutput;
import java.util.List;

@Controller
@RequestMapping("/user")
public class UserController extends AdminBaseController {
    private static final Object ACTION_EDIT = "edit";
    private static final Object ACTION_ADD = "add";
    @Autowired
    private ContactService contactService;
    @Autowired
    private AboutUsService aboutUsService;
    @Autowired
    private NewService newService;
    @Autowired
    private FeedBackService feedBackService;
    @Autowired
    private CareerService careerService;
    @Autowired
    private CategoriesService categoriesService;
    @Autowired
    private ProductService productService;
    @GetMapping("/index")
    public ModelAndView index(){
        int counts;
        counts =+1;
        List<AboutUs> aboutUs = aboutUsService.findAll();
        List<Contact> contacts = contactService.findAll();
        List<News> news = newService.findAll();
        List<Categories> categories = categoriesService.findAll();
        ModelAndView modelAndView = new ModelAndView("user/index");
        modelAndView.addObject("view",counts);
        modelAndView.addObject("contacts",contacts);
        modelAndView.addObject("aboutUs",aboutUs);
        modelAndView.addObject("news",news);
        modelAndView.addObject("categories",categories);

        return modelAndView;
    }
    @GetMapping("/contact")
    public ModelAndView Contact(){
        List<Contact> contacts = contactService.findAll();
        List<Categories> categories = categoriesService.findAll();
        ModelAndView modelAndView = new ModelAndView("user/contact");
        modelAndView.addObject("contacts", contacts);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("feedBack", new FeedBack());
        return modelAndView;
    }
    @GetMapping("/feedBack/add")
    public ModelAndView showAddForm(){
        ModelAndView modelAndView = new ModelAndView("user/contact");
        modelAndView.addObject("feedback",new FeedBack());
        modelAndView.addObject("action",ACTION_ADD);

        return  modelAndView;
    }
    @PostMapping("/feedBack/add")
    public ModelAndView saveAddForm( @ModelAttribute("feedBack") FeedBack feedBack, ViewControllerRegistry registry){
        feedBackService.save(feedBack);

        ModelAndView modelAndView = new ModelAndView("user/contact");
        modelAndView.addObject("feedBack",new Career());
        modelAndView.addObject("action",ACTION_ADD);
        RedirectViewControllerRegistration r =
                registry.addRedirectViewController("/user/contact", "/user/index");
        return  modelAndView;

    }

    @GetMapping("/aboutUs")
    public ModelAndView AboutUs(){
        List<Categories> categories = categoriesService.findAll();
        List<Contact> contacts = contactService.findAll();
        ModelAndView modelAndView = new ModelAndView("user/about");
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("contacts", contacts);
        return modelAndView;
    }

    @GetMapping("/news")
    public ModelAndView News(){
        List<News> news = newService.findAll();
        List<Contact> contacts = contactService.findAll();
        List<Categories>  categories = categoriesService.findAll();
        ModelAndView modelAndView = new ModelAndView("user/news");
        modelAndView.addObject("news", news);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("contacts", contacts);
        return modelAndView;}

    @GetMapping("/career")
    public ModelAndView Career(){
        List<Career> careers = careerService.findAll();
        List<Categories> categories = categoriesService.findAll();
        List<Contact> contacts = contactService.findAll();
        ModelAndView modelAndView = new ModelAndView("user/career");
        modelAndView.addObject("careers", careers);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("contacts", contacts);
        return modelAndView;
    }

    @PostMapping("/news/{id}")
    public ModelAndView saveEditForm(@ModelAttribute("news") News news){

        feedBackService.findAll();
        //
        ModelAndView modelAndView = new ModelAndView("/user/single-news");
        modelAndView.addObject("news",news);
        modelAndView.addObject("action",ACTION_EDIT);

        //
        return  modelAndView;
    }

    @GetMapping("/news/{id}")
    public ModelAndView showUpdateForm(@PathVariable Long id){
        List<Categories> categories = categoriesService.findAll();
        List<Contact> contacts = contactService.findAll();
        News news = newService.findById(id);
        if(news != null) {
            ModelAndView modelAndView = new ModelAndView("/user/single-news");
            modelAndView.addObject("news",news);
            modelAndView.addObject("categories",categories);
            modelAndView.addObject("contacts", contacts);
            modelAndView.addObject("action",ACTION_EDIT);
            return  modelAndView;
        }else {
            ModelAndView modelAndView = new ModelAndView("/error.404");
            return modelAndView;
        }

    }

    @GetMapping("/product/1")
    public ModelAndView categories(){
        List<Categories> categories = categoriesService.findAll();
        Categories category=categoriesService.findById(1);
        List<Product> products = productService.findAllByCategories(1);
        ModelAndView modelAndView = new ModelAndView("user/product");
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("category", category);
        modelAndView.addObject("products", products);
        return modelAndView;
    }
    @GetMapping("/product/{id}")
    public ModelAndView product(@PathVariable Long id){
        List<Categories> categories = categoriesService.findAll();
        Categories category=categoriesService.findById(id);
        List<Product> products = productService.findAllByCategories(id);
        List<Contact> contacts = contactService.findAll();
        ModelAndView modelAndView = new ModelAndView("user/product");
        modelAndView.addObject("category", category);
        modelAndView.addObject("categories", categories);
        modelAndView.addObject("products", products);
        modelAndView.addObject("contacts", contacts);
        return modelAndView;
    }
}