package kr.co._29cm.homework.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.repository.ProductsRepository;

@Service
public class ProductsListingService {
    
    
    private final String columnHeader ="상품번호      상품명                  판매가격        재고수";

    private final ProductsRepository productsRepo;
    public ProductsListingService(ProductsRepository productsRepo){
        this.productsRepo = productsRepo;
    }

    

    public List<Product> getProductsListEntity(){
        return productsRepo.findAll();
    }

    public String getProductsListing(){
        List<Product> allProductsEntity = this.getProductsListEntity();

        StringBuilder sb = new StringBuilder();
        sb.append(columnHeader);
        allProductsEntity.stream().forEach(product -> sb.append(product + "\n"));
        return sb.toString();



    }




}
