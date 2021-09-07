package kr.co._29cm.homework.product.service;

import java.util.List;

import kr.co._29cm.homework.product.domain.Product;

public interface ProductsListingService {
 
    public List<Product> getProductsListEntity();

    public String getProductsListing();
}
