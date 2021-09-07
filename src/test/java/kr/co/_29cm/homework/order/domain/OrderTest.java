package kr.co._29cm.homework.order.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;
import java.util.List;


import org.junit.jupiter.api.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class OrderTest {
    
    @Test
    public void 택배비결정_5만원이상(){

        OrderOption opt1 = new OrderOption(779989L, 1L);
        OrderOption opt2 = new OrderOption(778422L, 1L);
        ReflectionTestUtils.setField(opt1, "price", 35000L);
        ReflectionTestUtils.setField(opt2, "price", 45000L);
        List<OrderOption> orderOptions = new ArrayList<OrderOption>();
        orderOptions.add(opt1);
        orderOptions.add(opt2);
                
        Order order = new Order(orderOptions);
        order.calculateTotalPrice();
        
        assertTrue(order.getTotalAmount()>=Long.valueOf(50000));
        assertEquals(order.getParcelFee(), Long.valueOf(0));



    }



    @Test
    public void 택배비결정_5만원미만(){
        OrderOption opt1 = new OrderOption(779989L, 1L);
        ReflectionTestUtils.setField(opt1, "price", 35000L);
        List<OrderOption> orderOptions = new ArrayList<OrderOption>();
        orderOptions.add(opt1);

        Order order = new Order(orderOptions);
        order.calculateTotalPrice();
        
        assertTrue(order.getTotalAmount()<Long.valueOf(50000));
        assertEquals(order.getParcelFee(), Long.valueOf(2500));


    }
}
