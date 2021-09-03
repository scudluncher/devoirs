package kr.co._29cm.homework.common.shellCommand;


import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;

import kr.co._29cm.homework.common.service.ConsoleService;
import kr.co._29cm.homework.order.domain.Order;
import kr.co._29cm.homework.order.service.OrderService;
import kr.co._29cm.homework.product.service.ProductsListingService;

@ShellComponent
public class OrderCommands {




    private final ProductsListingService productsListingSvc;
    private final ConsoleService console;
    private final OrderService orderSvc;
    

  

    public OrderCommands(ProductsListingService productsListingSvc,  ConsoleService console, OrderService orderSvc){
        this.productsListingSvc = productsListingSvc;
        this.console = console;
        this.orderSvc = orderSvc;
    }





    @ShellMethod(value = "order process", key="o")
    public void order(){
        String productListing = productsListingSvc.getProductsListing();  // 상품 정보 조회
        this.console.write(productListing);  // 상품 정보 display 

        try{
            Order order = orderSvc.prepareOrder(); // 오더 진행
            console.writeOrderDetail(order); // 오더 진행 내용 display 


        }catch(Exception e){
            this.console.write(e.getMessage());
            return;
        }
        
        
        
        
        
    }

 

    @ShellMethod(value ="exit the shell", key = "q")
    public void orderQuit(){
        this.console.write("고객님의 주문 감사합니다.");
        System.exit(1);
        
    }

 
}
