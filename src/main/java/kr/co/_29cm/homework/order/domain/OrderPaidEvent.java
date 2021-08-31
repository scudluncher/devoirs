package kr.co._29cm.homework.order.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import kr.co._29cm.homework.product.domain.PickProduct;

public class OrderPaidEvent {
    private Order order;
    // private List<PickProduct> pickProducts;

    public OrderPaidEvent(Order order) {
        this.order = order;
        // pickProducts = getPickProduct();
    }



    public List<PickProduct> getPickProduct(){
        List<PickProduct> pickProducts = new ArrayList<PickProduct>();
        pickProducts = this.order.getOrderOptions()
                                 .stream()
                                 .map(PickProduct::new)
                                 .collect(Collectors.toList());
        return pickProducts;

    }



    

    
}
