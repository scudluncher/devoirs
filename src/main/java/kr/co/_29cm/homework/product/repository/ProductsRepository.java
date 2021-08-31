package kr.co._29cm.homework.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co._29cm.homework.product.domain.Product;

public interface ProductsRepository extends JpaRepository<Product, Long>{
    
}
