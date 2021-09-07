package kr.co._29cm.homework.order.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

import kr.co._29cm.homework.common.promptProvider.InitialPromptProvider;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderOption;
import kr.co._29cm.homework.order.repository.OrderRepository;

@ExtendWith(MockitoExtension.class)
public class OrderServiceImplTest {
    

    @Mock
    private OrderValidator orderValidator;
    @Mock
    private OrderRepository orderRepository;
    @Mock
    private InitialPromptProvider console;

    private OrderService orderService;

    private final String PRODUCT_NUMBER = "상품번호 : ";
    private final String QUANTITY = "수량 : ";


    @BeforeEach
    void init(){
        orderService = new OrderServiceImpl(orderValidator, console, orderRepository);
    }

    @Test
    public void 오더입력_1개_정상케이스(){
        OrderOption opt1 = new OrderOption(779989L, 1L);
        ReflectionTestUtils.setField(opt1, "price", 35000L);
        List<OrderOption> orderOptions = new ArrayList<OrderOption>();
        orderOptions.add(opt1);

        Order order = new Order(orderOptions);

        given(console.ask(PRODUCT_NUMBER)).willReturn("779989", " ");
        given(console.ask(QUANTITY)).willReturn("1", " ");
        given(orderValidator.validateOrderOption(any())).willReturn(orderOptions);
        given(orderRepository.save(any(Order.class))).willReturn(order);


        orderService.prepareOrder();

    }


    @Test
    public void 오더입력_2개_정상케이스(){
        OrderOption opt1 = new OrderOption(779989L, 1L);
        OrderOption opt2 = new OrderOption(778422L, 1L);
        ReflectionTestUtils.setField(opt1, "price", 35000L);
        ReflectionTestUtils.setField(opt2, "price", 45000L);
        List<OrderOption> orderOptions = new ArrayList<OrderOption>();
        orderOptions.add(opt1);
        orderOptions.add(opt2);
        Order order = new Order(orderOptions);

        given(console.ask(PRODUCT_NUMBER)).willReturn("779989", " ");
        given(console.ask(QUANTITY)).willReturn("1", " ");
        given(orderValidator.validateOrderOption(any())).willReturn(orderOptions);
        given(orderRepository.save(any(Order.class))).willReturn(order);


        orderService.prepareOrder();

    }


    @Test
    public void 오더입력_0개_비정상케이스(){
        given(console.ask(PRODUCT_NUMBER)).willReturn(" ");
        given(console.ask(QUANTITY)).willReturn(" ");
        
        assertThrows(IllegalArgumentException.class, ()->{
            orderService.prepareOrder();
        });
    }

    @Test
    public void 오더입력_숫자가아닌값입력(){
        given(console.ask(PRODUCT_NUMBER)).willReturn("asdf");
        given(console.ask(QUANTITY)).willReturn("qwer");
        
        assertThrows(IllegalArgumentException.class, ()->{
            orderService.prepareOrder();
        });
    }



    @Test
    public void 오더생성(){

        OrderOption opt1 = new OrderOption(779989L, 1L);
        OrderOption opt2 = new OrderOption(778422L, 1L);
        ReflectionTestUtils.setField(opt1, "price", 35000L);
        ReflectionTestUtils.setField(opt2, "price", 45000L);
        List<OrderOption> orderOptions = new ArrayList<OrderOption>();
        orderOptions.add(opt1);
        orderOptions.add(opt2);
        Order order = new Order(orderOptions);
        order.calculateTotalPrice();
        order.paid();


        given(orderRepository.save(any(Order.class))).willReturn(order);


        Order savedOrder = orderService.placeOrder(orderOptions);
        
        assertEquals( savedOrder.getOrderOptions().size(),order.getOrderOptions().size());
        assertEquals( savedOrder.getPaidAmount(), order.getPaidAmount());
        assertEquals( savedOrder.getParcelFee(), order.getParcelFee());
        assertEquals( savedOrder.getTotalAmount(), order.getTotalAmount());


    }




}
