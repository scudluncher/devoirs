package kr.co._29cm.homework.product.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.Rollback;

import kr.co._29cm.homework.product.domain.Product;

@DataJpaTest
public class ProductsRepositoryTest {
    
    @Autowired
    private ProductsRepository productsRepo;



    @Test
    public void 상품_리스트_조회(){
        List<Product> productList = productsRepo.findAll();
        assertEquals(productList.size(), 19);
    }

    @Test
    @Rollback(true)
    public void 싱글스레드_갯수_차감(){
        Product product = productsRepo.findByIdWithLock(778422L);
        product.decreaseStock(7L);

        product = productsRepo.save(product);

        assertEquals(product.getStockQty() , Long.valueOf(0));
        
    }



    
}
