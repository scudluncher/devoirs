package kr.co._29cm.homework.product.exception;

public class SoldOutException extends RuntimeException{
    public SoldOutException(String errMsg){
        super(errMsg);
    }
}
