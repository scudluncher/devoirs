package kr.co._29cm.homework.product.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import kr.co._29cm.homework.order.domain.OrderOption;
import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.exception.SoldOutException;
import kr.co._29cm.homework.product.exception.WrongProductException;
import kr.co._29cm.homework.product.repository.ProductsRepository;

@Component
public class ProductsValidator {
    

    private final ProductsRepository productsRepo;
    public ProductsValidator(ProductsRepository productsRepo){
        this.productsRepo = productsRepo;
    }


    public List<OrderOption> validateOrderOption(List<OrderOption> orderOptions) throws WrongProductException, SoldOutException{
        
        List<Long> productIdList = orderOptions.stream().map(opt->opt.getProductId()).collect(Collectors.toList());
        List<Product> productsFound = productsRepo.findAllById(productIdList);

        if(!validateProductIdCount(productIdList, productsFound)){
            throw new WrongProductException("잘못된 Product Id 입력됨");
        }

        if(!validateStockQty(orderOptions, productsFound)){
            throw new SoldOutException("SoldOutException 발생. 주문량이 재고량보다 큽니다.");
        }

        Map<Long, Product> infoMap = new HashMap<Long, Product>();
        productsFound.stream().forEach(product -> infoMap.put(product.getId(), product));
        orderOptions.stream().forEach(opt -> opt.fillInfo(infoMap.get(opt.getProductId())));

        return orderOptions;

    }



    private boolean validateProductIdCount(List<Long> productIdList , List<Product> productsFound){
        
        int orderProductCount = productIdList.size();
        int validProductCount = productsFound.size();
        if(orderProductCount != validProductCount){
            return false;
        }else{
            return true;
        } 
    }


    private boolean validateStockQty(List<OrderOption> orderOptions, List<Product> productsFound){
        Map<Long,Long> validatorMap = new HashMap<Long,Long>();
        productsFound.stream().forEach(product -> 
                                        validatorMap.put(product.getId(), product.getStockQty()));
        orderOptions.stream().forEach((ordOpt)->
                                        validatorMap.put(ordOpt.getProductId(),validatorMap.get(ordOpt.getProductId()) - ordOpt.getDemandQty()));


        for(Long key : validatorMap.keySet()){
            if(validatorMap.get(key)<0){
                return false;
            }
        }
        return true;

    }
   
    
   


}
