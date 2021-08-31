package kr.co._29cm.homework.product.service;

import java.util.List;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.repository.ProductsRepository;

public class StockServiceImpl implements StockService{
    
    private final ProductsRepository productsRepo;

    public StockServiceImpl(ProductsRepository productsRepo){
        this.productsRepo = productsRepo;
    }

    @Override
    @Transactional
    public void parcelSend(Order order){
        List<Long> sendItemId = order.getOrderOptions()
                                    .stream()
                                    .map(opt->opt.getProductId()).collect(Collectors.toList());
        
        List<Product> sendItems = productsRepo.findAllById(sendItemId);
        
        // sendItems.stream().forEach(product -> product.sendParcel());
                        
        
    }
}
