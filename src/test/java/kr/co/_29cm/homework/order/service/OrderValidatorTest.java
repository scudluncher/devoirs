package kr.co._29cm.homework.order.service;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import kr.co._29cm.homework.order.domain.OrderOption;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.exception.SoldOutException;
import kr.co._29cm.homework.product.exception.WrongProductException;
import kr.co._29cm.homework.product.repository.ProductsRepository;



@ExtendWith(MockitoExtension.class)
public class OrderValidatorTest {
    
    @Mock
    private ProductsRepository productsRepo;

    private OrderValidator orderValidator;

    @BeforeEach
    void init(){
        orderValidator = new OrderValidator(productsRepo);
    }


    @Test
    public void 없는상품입력시_WrongProductException발생(){
        
        OrderOption opt1 = new OrderOption(1L, 1L);
        List<OrderOption> orderOptions = new ArrayList<OrderOption>();
        orderOptions.add(opt1);

        assertThrows(WrongProductException.class , ()->{
            orderValidator.validateOrderOption(orderOptions);
        });
    }


    @Test
    public void 재고부족시_SoldOutException발생(){
        Long stockQty = 5L;

        Long productId = 779989L;
        Product product = new Product();
        ReflectionTestUtils.setField(product, "id", productId);
        ReflectionTestUtils.setField(product, "stockQty", stockQty);

        List<Product> productList = new ArrayList<>();
        productList.add(product);


        OrderOption opt1 = new OrderOption(productId, stockQty+1);
        List<OrderOption> orderOptions = new ArrayList<>();
        orderOptions.add(opt1);
        
        
        List<Long> productIdList = new ArrayList<>();
        productIdList.add(productId);



        given(productsRepo.findAllById(productIdList)).willReturn(productList);
        
        assertThrows(SoldOutException.class, ()->{
            orderValidator.validateOrderOption(orderOptions);
        });
       

    }

}
