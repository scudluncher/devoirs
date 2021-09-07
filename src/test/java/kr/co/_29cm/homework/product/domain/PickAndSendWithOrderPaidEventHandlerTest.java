package kr.co._29cm.homework.product.domain;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import org.springframework.shell.jline.ScriptShellApplicationRunner;
import org.springframework.test.annotation.Rollback;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderOption;
import kr.co._29cm.homework.order.domain.OrderPaidEvent;
import kr.co._29cm.homework.product.exception.SoldOutException;

@SpringBootTest(properties = {
    InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false",
    ScriptShellApplicationRunner.SPRING_SHELL_SCRIPT + ".enabled=false"
})
public class PickAndSendWithOrderPaidEventHandlerTest {
    
    

    @Autowired
    private PickAndSendWithOrderPaidEventHandler eventHandler;

    
  
    @Test
    @DisplayName("멀티 스레드 갯수 차감")
    @Rollback(true)
    public void 멀티스레드_갯수_차감_레이스컨디션() throws InterruptedException{
        OrderOption orderOption = new OrderOption(778422L, 1L);  // 최초 재고 7개
        
        List<OrderOption> orderOptions = new ArrayList<OrderOption>();
        orderOptions.add(orderOption);
        Order order = new Order(orderOptions);
        int threadCount = 10;
        int initialStock = 7;
    
        OrderPaidEvent event = new OrderPaidEvent(order);
        
        AtomicInteger successCount = new AtomicInteger();
        AtomicInteger failCount = new AtomicInteger();
        int numberOfExcute = 10;
        ExecutorService service = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(numberOfExcute);

        for(int i = 0 ; i< numberOfExcute ; i++){
            service.execute(()->{
                try{
                    eventHandler.handle(event);
                    successCount.getAndIncrement();
                   
                }catch(SoldOutException e){
                    System.out.println("sold out");
                    failCount.getAndIncrement();
                }
                latch.countDown();
                
            });
        }

        latch.await();
        assertEquals(successCount.get(), initialStock);
        assertEquals(failCount.get(), numberOfExcute - initialStock);
    
    
        


    }


    
}
