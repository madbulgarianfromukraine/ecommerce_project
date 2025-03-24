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

    @PutMapping("/product/{id}")
    public ResponseEntity<?> updateProduct(@RequestPart Product product, @RequestPart MultipartFile imageFile){
        try {
            Product prod_updated = productService.updateProduct(product, imageFile);
            if (prod_updated == null) {
                return new ResponseEntity<>("Product not found", HttpStatus.BAD_REQUEST);
            }
            return new ResponseEntity<>(prod_updated, HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Internal Server Error", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/product/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable int id){
        if(productService.deleteProduct(id)){
            return new ResponseEntity<>("Product deleted", HttpStatus.OK);
        }
        return new ResponseEntity<>("Product not found", HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/products/search")
    public ResponseEntity<List<Product>> searchProducts(@RequestParam String keyword){
        System.out.println("Searching with " + keyword);
        List<Product> results = productService.searchProducts(keyword);
        if(results == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(results);
    }

}
