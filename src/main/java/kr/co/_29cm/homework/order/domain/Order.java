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

    private static final Long parcelPolicyThreshold = Long.valueOf(50000);
    private static final Long parcelAddFee = Long.valueOf(2500);

    public Order(List<OrderOption> orderOptions) {
        this.orderOptions = orderOptions;
        this.totalAmount = Long.valueOf(0);
    }

    @Column(name="ID")
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long orderId;

    @Column(name="TOTAL_AMOUNT")
    private Long totalAmount;


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

    public void paid() {
        registerEvent(new OrderPaidEvent(this));
    }

    public void checkParcelFee(){
        if(totalAmount<parcelPolicyThreshold){
            this.parcelFee = parcelAddFee;
        }
    }


}
