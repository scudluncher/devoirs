package kr.co._29cm.homework.order.domain;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.data.domain.AbstractAggregateRoot;

@Entity
@Table(name="ORDER_TABLE")
public class Order extends AbstractAggregateRoot<Order> {

    private static final Long parcelPolicyThreshold = 50000L;
    private static final Long parcelAddFee = 2500L;

    public Order(List<OrderOption> orderOptions) {
        this.orderOptions = orderOptions;
        this.totalAmount = 0L;
    }

    @Column(name="ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long orderId;

    @Column(name="TOTAL_AMOUNT")
    private Long totalAmount;

    @Column(name="PAID_AMOUNT")
    private Long paidAmount;


    //테이블 생략
    @Transient
    private List<OrderOption> orderOptions;

    @Column(name="PARCEL_FEE")
    private Long parcelFee;

    public void calculateTotalPrice() {
        orderOptions.stream().forEach(opt -> totalAmount += opt.getDemandQty() * opt.getPrice());
        checkParcelFee();
    }

    public Long getOrderId() {
        return orderId;
    }

    public Long getTotalAmount() {
        return totalAmount;
    }

    public List<OrderOption> getOrderOptions() {
        return orderOptions;
    }

    public Long getParcelFee() {
        return this.parcelFee;
    }
    
    public Long getPaidAmount() {
        return paidAmount;
    }

    public void paid() {
        this.paidAmount = totalAmount + parcelFee;
        registerEvent(new OrderPaidEvent(this));
    }

    private void checkParcelFee(){
        if(totalAmount<parcelPolicyThreshold){
            this.parcelFee = parcelAddFee;
        }else{
            this.parcelFee = 0L;
        }
    }

    public boolean isParcelFeeFree(){
        return this.parcelFee == 0L ? true : false;
    }

}
