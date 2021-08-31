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
    public String order(){
        String productListing = productsListingSvc.getProductsListing();
        this.console.write(productListing);

        try{
            Order order = orderSvc.prepareOrder();
            console.write(order.getTotalAmount().toString());
            console.writeSplitter();


        }catch(Exception e){
            this.console.write(e.getMessage());
            return "";
        }
        
        

        
        
        return "asdf";
    }

 

    @ShellMethod(value ="quit", key = "q")
    public String quit(){
        return "quit";
        //todo 
    }

 
}
