package com.nazarii.yukhnovskyi.ecommerceProject.controller;

import com.nazarii.yukhnovskyi.ecommerceProject.model.Product;
import com.nazarii.yukhnovskyi.ecommerceProject.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class ProductController {

    @Autowired
    private ProductService productService;

    @RequestMapping("/")
    public String greet(){
        return "Hello from Product Controller";
    }

    @GetMapping("/products")
    public ResponseEntity<List<Product>> getProducts(){
        return ResponseEntity.ok(productService.getProducts());
    }

    @GetMapping ("/product/{id}")
    public ResponseEntity<Product> getProduct(@PathVariable int id){
        Product prod = productService.getProduct(id);
        if(prod == null){
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(prod);
    }

    @PostMapping("/product")
    public ResponseEntity<?> addProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try{
            Product prod = productService.addProduct(product, imageFile);
            return new ResponseEntity<>(product, HttpStatus.CREATED);
        }catch(Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/product/{id}/image")
    public ResponseEntity<byte[]> getImage(@PathVariable int id){
        Product prod = productService.getProduct(id);
        if(prod == null){
            return ResponseEntity.notFound().build();
        }
        byte[] imageFile = prod.getImg();
        if(imageFile == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok()
                .contentType(MediaType.valueOf(prod.getImgType()))
                .body(imageFile);
    }

}
