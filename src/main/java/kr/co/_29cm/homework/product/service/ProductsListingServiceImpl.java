package kr.co._29cm.homework.product.service;

import java.util.List;

import org.springframework.stereotype.Service;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.repository.ProductsRepository;

@Service
public class ProductsListingServiceImpl implements ProductsListingService{
    
    
    private final String columnHeader ="상품번호      상품명                  판매가격        재고수";

    private final ProductsRepository productsRepo;
    public ProductsListingServiceImpl(ProductsRepository productsRepo){
        this.productsRepo = productsRepo;
    }

    
    @Override
    public List<Product> getProductsListEntity(){
        return productsRepo.findAll();
    }

    @Override
    public String getProductsListing(){
        List<Product> allProductsEntity = this.getProductsListEntity();

        StringBuilder sb = new StringBuilder();
        sb.append(columnHeader);
        allProductsEntity.stream().forEach(product -> sb.append(product + "\n"));
        return sb.toString();



    }




}
