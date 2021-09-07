package kr.co._29cm.homework.product.domain;

import org.springframework.context.event.EventListener;
// import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import kr.co._29cm.homework.order.domain.OrderPaidEvent;
import kr.co._29cm.homework.product.repository.ProductsRepository;

@Component
public class PickAndSendWithOrderPaidEventHandler {
    private ProductsRepository productsRepo;

    public PickAndSendWithOrderPaidEventHandler(ProductsRepository productsRepo){
        this.productsRepo = productsRepo;
    }


    //@Async
    @EventListener
    @Transactional 
    public void handle(OrderPaidEvent event){
        for(PickProduct pickProduct : event.getPickProduct()){
            Product product = productsRepo.findByIdWithLock(pickProduct.getProductId());
            product.decreaseStock(pickProduct.getDemandQty());
            productsRepo.save(product);
        }
        
    }

}
