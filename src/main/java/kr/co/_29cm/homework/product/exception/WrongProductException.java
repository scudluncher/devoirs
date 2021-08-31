package kr.co._29cm.homework.product.exception;

public class WrongProductException extends RuntimeException {
    public WrongProductException(String errMsg){
        super(errMsg);
    }
}
