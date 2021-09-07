package kr.co._29cm.homework.product.repository;

import java.util.List;

import javax.persistence.LockModeType;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import kr.co._29cm.homework.product.domain.Product;

public interface ProductsRepository extends JpaRepository<Product, Long>{
    
    List<Product> findAll();
    

    @Lock(value = LockModeType.PESSIMISTIC_READ)
    @Query("select p from Product p where p.id =:id")
    Product findByIdWithLock(@Param("id") Long id);



}
