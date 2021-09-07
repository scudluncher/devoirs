package kr.co._29cm.homework.order.service;

import java.util.List;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderOption;

public interface OrderService {
    
    public Order prepareOrder();
    public Order placeOrder(List<OrderOption> orderOptions);
    
}
