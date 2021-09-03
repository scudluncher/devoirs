package kr.co._29cm.homework.order.domain;

import kr.co._29cm.homework.product.domain.Product;


//table 생략
public class OrderOption {
    
    public OrderOption(Long productId, Long demandQty){
        this.productId = productId;
        this.demandQty = demandQty;
    }

    private Long optionId;
    private Long productId;
    private Long price;
    private String productDesc;
    private Long demandQty;


    public void fillInfo(Product product){
        this.price = product.getPrice();
        this.productDesc = product.getProductDesc();
    }

    public String showSelectedItemInfo(){
        return this.productDesc + "    -  " + this.demandQty;
    }

    public Long getOptionId() {
        return optionId;
    }
    public Long getProductId() {
        return productId;
    }
    public Long getPrice() {
        return price;
    }
    public Long getDemandQty() {
        return demandQty;
    }
    public String getProductDesc(){
        return productDesc;
    }




    


}
