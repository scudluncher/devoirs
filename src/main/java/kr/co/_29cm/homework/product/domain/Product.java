package kr.co._29cm.homework.product.domain;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import kr.co._29cm.homework.product.exception.SoldOutException;


@Entity
@Table(name="PRODUCT")
@DynamicUpdate
public class Product {
    
    public Product(){};

    @Column(name="ID")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="PRODUCT_DESC")
    private String productDesc;

    @Column(name="PRICE")
    private Long price;

    @Column(name="STOCK_QTY")
    private Long stockQty;



    public Long getId() {
        return id;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public Long getPrice() {
        return price;
    }

    public Long getStockQty() {
        return stockQty;
    }

    @Override
    public String toString(){
        return this.id.toString() + "    " + this.productDesc + "    " + this.price + "    " + this.stockQty;
    }

    
    public void decreaseStock(Long demandQty){
        this.stockQty -= demandQty;
        if(stockQty<0){
            throw new SoldOutException("SoldOutException 발생. 주문량이 재고량보다 큽니다.");
        }
    }




}
