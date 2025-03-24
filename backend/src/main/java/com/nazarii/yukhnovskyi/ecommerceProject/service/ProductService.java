package com.nazarii.yukhnovskyi.ecommerceProject.service;

import com.nazarii.yukhnovskyi.ecommerceProject.model.Product;
import com.nazarii.yukhnovskyi.ecommerceProject.repo.ProductRepo;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepo repo;


    public List<Product> getProducts() {
        List<Product> results = repo.findAll();
//        for (int i = 0; i < results.size(); i++) {
//            System.out.println(results.get(i).toString());
//        }

        return results;
    }

    public Product getProduct(int id) {
        return repo.findById((long) id).orElse(null);
    }

    public Product addProduct(Product product, MultipartFile imageFile) throws IOException{
        product.setImgName(imageFile.getOriginalFilename());
        product.setImgType(imageFile.getContentType());
        product.setImg(imageFile.getBytes());

        return repo.save(product);

    }

    public Product updateProduct(Product product, MultipartFile imageFile) throws IOException{
        if(this.getProduct((int) product.getId()) == null){
            return null;
        }

        product.setImg(imageFile.getBytes());
        product.setImgName(imageFile.getOriginalFilename());
        product.setImgType(imageFile.getContentType());
        return repo.save(product);
    }

    public boolean deleteProduct(int id){
        if(this.getProduct(id) == null){
            return false;
        }
        repo.deleteById((long) id);
        return true;
    }

    @Transactional
    public List<Product> searchProducts(String keyword) {
        return repo.searchProducts(keyword);
    }
}
