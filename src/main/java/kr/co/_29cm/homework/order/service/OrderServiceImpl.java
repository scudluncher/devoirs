package kr.co._29cm.homework.order.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import kr.co._29cm.homework.common.promptProvider.InitialPromptProvider;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderOption;
import kr.co._29cm.homework.order.repository.OrderRepository;
import kr.co._29cm.homework.product.exception.SoldOutException;
import kr.co._29cm.homework.product.exception.WrongProductException;
// import kr.co._29cm.homework.product.service.StockService;

@Service
public class OrderServiceImpl implements OrderService{

    private final String PRODUCT_NUMBER = "상품번호 : ";
    private final String QUANTITY = "수량 : ";


    private final OrderValidator orderValidator;
    private final InitialPromptProvider console;
    private final OrderRepository orderRepository;

    public OrderServiceImpl(OrderValidator orderValidator, InitialPromptProvider console, OrderRepository orderRepository){ 
        this.orderValidator = orderValidator;
        this.console = console;
        this.orderRepository = orderRepository;
    }



    @Override
    public Order prepareOrder() throws WrongProductException, SoldOutException, IllegalArgumentException{

        String productIdStr ="";
        String demandQtyStr ="";
        List<OrderOption> orderOptions = new ArrayList<OrderOption>();
        OrderOption orderOption;
        
        while(true){
            productIdStr = console.ask(PRODUCT_NUMBER);

            if(" ".equals(productIdStr) && orderOptions.size()>0){  // stock quantity validation 은 product Id " " 한번 들어올 때 진행
            orderOptions = orderValidator.validateOrderOption(orderOptions);
            }

            demandQtyStr = console.ask(QUANTITY);

            if(" ".equals(productIdStr) && " ".equals(demandQtyStr)){  // 탈출조건 
                break;
            }

            orderOption = parseOrderOption(productIdStr, demandQtyStr);
            orderOptions.add(orderOption);
        }

        
        if(orderOptions.size()>0){
            Order order = this.placeOrder(orderOptions); // 주문생성
            return order;
            // paid 가정
        }else{
            throw new IllegalArgumentException("주문 내역 없음"); 
        }
    }
    


    
    private OrderOption parseOrderOption(String productIdStr, String demandQtyStr){
        try{
            Long productId = Long.valueOf(productIdStr);
            Long demandQty = Long.valueOf(demandQtyStr);
            OrderOption orderOption = new OrderOption(productId, demandQty);
            return orderOption;
        }catch(NumberFormatException e){
                throw new IllegalArgumentException("숫자만 입력할 수 있습니다.");
            }
    }
    

    @Override
    @Transactional
    public Order placeOrder(List<OrderOption> orderOptions){
        Order order = new Order(orderOptions);
        order.calculateTotalPrice();
        order.paid(); 
        order = orderRepository.save(order);
        return order;
    }







}
