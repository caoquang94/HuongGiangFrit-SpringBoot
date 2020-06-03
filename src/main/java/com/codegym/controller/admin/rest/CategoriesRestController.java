package com.codegym.controller.admin.rest;

import com.codegym.model.Categories;
import com.codegym.service.Impl.CategoriesService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoriesRestController {
    @Autowired
    private CategoriesService categoriesService;

    @GetMapping("/categorieses/")
    private ResponseEntity<List<Categories>> listCategoriess(){
        List<Categories> categorieses= categoriesService.findAll ();

        return new ResponseEntity<List<Categories>>(categorieses, HttpStatus.OK);
    }

    @GetMapping(value = "/categorieses/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Categories> getCategories(@PathVariable("id") long id) {
        Categories categories = categoriesService.findById(id);
        if (categories == null) {

            return new ResponseEntity<Categories>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Categories>(categories, HttpStatus.OK);
    }

    @PostMapping(value = "/categorieses/")
    public ResponseEntity<Categories> createCategories(@RequestBody Categories categories, UriComponentsBuilder ucBuilder) {
        try {
            categoriesService.save(categories);
            return new ResponseEntity<Categories>(categories, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Categories>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/categorieses/{id}")
    public ResponseEntity<Categories> updateCategories(@PathVariable("id") long id, @RequestBody Categories categories) {

        Categories currentCategories = categoriesService.findById(id);

        if (currentCategories == null) {
            System.out.println("Categories with id " + id + " not found");
            return new ResponseEntity<Categories>(HttpStatus.NOT_FOUND);
        }

        currentCategories.setName (categories.getName ());
        try {
            categoriesService.save(currentCategories);
        }catch (Exception ex){
            return new ResponseEntity<Categories>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Categories>(currentCategories, HttpStatus.OK);
    }
    @DeleteMapping(value = "/categorieses/{id}")
    public ResponseEntity<Categories> deleteCategories(@PathVariable("id") Long id){
        Categories currentCategories = categoriesService.findById(id);
        if (currentCategories == null) {
            System.out.println("Categories with id " + id + " not found");
            return new ResponseEntity<Categories>(HttpStatus.NOT_FOUND);
        }
        try {
            categoriesService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<Categories> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Categories>(currentCategories, HttpStatus.OK);
    }
}
