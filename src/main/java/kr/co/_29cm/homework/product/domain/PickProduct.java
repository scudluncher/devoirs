package kr.co._29cm.homework.product.domain;

import kr.co._29cm.homework.order.domain.OrderOption;

public class PickProduct{
    Long productId;
    Long demandQty;

    public PickProduct(OrderOption orderOption){
        this.productId = orderOption.getProductId();
        this.demandQty = orderOption.getDemandQty();
    }

    public Long getProductId() {
        return productId;
    }

    public Long getDemandQty() {
        return demandQty;
    }
}
