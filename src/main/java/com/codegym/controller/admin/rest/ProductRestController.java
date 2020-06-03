package com.codegym.controller.admin.rest;

import com.codegym.model.Product;
import com.codegym.service.Impl.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/api")
public class ProductRestController {
@Autowired
    private ProductService productService;
    @GetMapping("/products/")
    private ResponseEntity<List<Product>> listProducts(){
        List<Product> products= productService.findAll ();

        return new ResponseEntity<List<Product>>(products, HttpStatus.OK);
    }

    @GetMapping(value = "/products/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Product> getProduct(@PathVariable("id") long id) {
        Product product = productService.findById(id);
        if (product == null) {

            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Product>(product, HttpStatus.OK);
    }

    @PostMapping(value = "/products/")
    public ResponseEntity<Product> createProduct(@RequestBody Product product, UriComponentsBuilder ucBuilder) {
        try {
            productService.save(product);
            return new ResponseEntity<Product>(product, HttpStatus.OK);
        }catch (Exception ex){
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping(value = "/products/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable("id") long id, @RequestBody Product product) {

        Product currentProduct = productService.findById(id);

        if (currentProduct == null) {
            System.out.println("Product with id " + id + " not found");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }

        currentProduct.setName (product.getName ());
        try {
            productService.save(currentProduct);
        }catch (Exception ex){
            return new ResponseEntity<Product>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
    }
    @DeleteMapping(value = "/products/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable("id") Long id){
        Product currentProduct = productService.findById(id);
        if (currentProduct == null) {
            System.out.println("Product with id " + id + " not found");
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
        try {
            productService.remove (id);
        } catch (Exception e) {
            return new ResponseEntity<Product> (HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<Product>(currentProduct, HttpStatus.OK);
    }
}
