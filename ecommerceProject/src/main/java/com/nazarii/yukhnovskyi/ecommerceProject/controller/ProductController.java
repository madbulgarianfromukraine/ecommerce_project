package com.nazarii.yukhnovskyi.ecommerceProject.controller;

import com.nazarii.yukhnovskyi.ecommerceProject.model.Product;
import com.nazarii.yukhnovskyi.ecommerceProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String greet(){
        return "Hello from Product Controller";
    }

    @GetMapping("/products")
    public List<Product> getProducts(){
        return productService.getProducts();
    }

}
