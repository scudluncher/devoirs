package kr.co._29cm.homework.common.service;

import java.io.PrintStream;

import org.springframework.stereotype.Service;

import kr.co._29cm.homework.product.domain.Product;

@Service
public class ConsoleService {
    private final PrintStream out = System.out;
    

    private final static String SPLITTER ="--------------------------";
    private final static String EA = "개";
    private final static String CURRENCY = "원";
    private final static String ORDERAMOUNT = "주문금액 : ";
    private final static String PAIDAMOUNT  = "지불금액 : ";



    public void write(String message){
        this.out.print("> ");
        this.out.printf(message);
        this.out.println();
    }

    public void writeSplitter(){
        this.out.print(SPLITTER);
        this.out.println();
    }


    public void writeProductDescAndQuantity(Product product){
        
    }



}
