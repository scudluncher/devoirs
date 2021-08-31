package kr.co._29cm.homework.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import kr.co._29cm.homework.common.promptProvider.InitialPromptProvider;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderOption;
import kr.co._29cm.homework.order.repository.OrderRepository;
import kr.co._29cm.homework.product.exception.SoldOutException;
import kr.co._29cm.homework.product.exception.WrongProductException;
import kr.co._29cm.homework.product.service.ProductsValidator;

@Service
public class OrderServiceImpl implements OrderService{

    private final String PRODUCT_NUMBER = "상품번호 : ";
    private final String QUANTITY = "수량 : ";


    private final ProductsValidator productsValidator;
    private final InitialPromptProvider console;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(ProductsValidator productsValidator, InitialPromptProvider console, OrderRepository orderRepository){
        this.productsValidator = productsValidator;
        this.console = console;
        this.orderRepository = orderRepository;
    }

    @Override
    public Order prepareOrder() throws WrongProductException, SoldOutException, IllegalArgumentException{

        Long productId;
        Long demandQty;

        String productIdStr ="";
        String demandQtyStr ="";
        List<OrderOption> orderOptions = new ArrayList<OrderOption>();
        
        while(true){
            productIdStr = console.ask(PRODUCT_NUMBER);
            demandQtyStr = console.ask(QUANTITY);
            if(" ".equals(productIdStr) && " ".equals(demandQtyStr)){
                break;
            }else{
                try{
                    productId = Long.valueOf(productIdStr);
                    demandQty = Long.valueOf(demandQtyStr);
                    OrderOption orderOption = new OrderOption(productId, demandQty);
                    orderOptions.add(orderOption);
                }catch(NumberFormatException e){
                    throw new IllegalArgumentException("숫자만 입력할 수 있습니다.");
                }
            }
        }

        productsValidator.validateOrderOption(orderOptions);

        Order order = this.createOrder(orderOptions); // 주문생성
         // paid 가정

        return order;
    }


    private Order createOrder(List<OrderOption> orderOptions){
        Order order = new Order(orderOptions);
        order.calculateTotalPrice();
        order.paid();
        orderRepository.save(order);
        
        return order;
    }







}
