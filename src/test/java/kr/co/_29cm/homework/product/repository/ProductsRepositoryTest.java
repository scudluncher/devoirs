package kr.co._29cm.homework.product.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import kr.co._29cm.homework.product.domain.Product;

@DataJpaTest
public class ProductsRepositoryTest {
    
    @Autowired
    private ProductsRepository productsRepo;



    @Test
    @DisplayName("상품 리스트 조회")
    public void productsListing(){
        List<Product> productList = productsRepo.findAll();
        assertEquals(productList.size(), 19);
    }

    
}
