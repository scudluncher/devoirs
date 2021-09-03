package kr.co._29cm.homework.common.service;

import java.io.PrintStream;
import java.text.DecimalFormat;

import org.springframework.stereotype.Service;

import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.domain.OrderOption;

@Service
public class ConsoleService {
    private final PrintStream out = System.out;
    

    private final static String SPLITTER ="--------------------------";
    private final static String EA = "개";
    // private final static String CURRENCY = "원";
    private final static String ORDERAMOUNT = "주문금액 : ";
    private final static String PAIDAMOUNT  = "지불금액 : ";
    private final static String PARCELFEE = "배송비 : ";
    private final static String ORDERDETAIL = "주문내역 :";
    // private final static String PRODUCTDESC = "상품명";


    public enum Currency {
        KRW("원"),
        USD("USD");

        private final String displayName;

        private Currency(String displayName){
            this.displayName = displayName;
        }

        public String getDisplayName(){
            return this.displayName;
        }
        

    }

    public void write(String message){
        this.out.printf(message);
        this.out.println();
    }

    public void writeSplitter(){
        this.out.print(SPLITTER);
        this.out.println();
    }


    public void writeOrderDetail(Order order){
        this.write(ORDERDETAIL);
        this.writeSplitter();
        
        for(OrderOption opt : order.getOrderOptions()){  // 상품별 가격을 보여줍니다. 
            String message = opt.showSelectedItemInfo() + EA; 
            this.write(message);
        }
        this.writeSplitter();

        String orderAmount = displayMoneyAccount(ORDERAMOUNT, order.getTotalAmount(), Currency.KRW);

        this.write(orderAmount);

        if(!order.isParcelFeeFree()){ // 택배비 부과 되는 경우인지 체크하여 택배비 항목 추가
            String parcelFee =  displayMoneyAccount(PARCELFEE, order.getParcelFee(),  Currency.KRW);
            this.write(parcelFee);
        }

        this.writeSplitter();
        String paidAmount = displayMoneyAccount(PAIDAMOUNT, order.getPaidAmount() , Currency.KRW);
        this.write(paidAmount);
        this.writeSplitter();


    }


    private String displayMoneyAccount(String type, Long amount, Currency currency){
        String displayValue = type + moneyValueWithComma(amount) + currency.getDisplayName();
        return displayValue;
    }


    private String moneyValueWithComma(Long amount){
        DecimalFormat formatter = new DecimalFormat("###,###");
        return formatter.format(amount);
    }




}
