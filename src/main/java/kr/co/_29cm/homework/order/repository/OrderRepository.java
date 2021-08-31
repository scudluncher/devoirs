package kr.co._29cm.homework.order.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import kr.co._29cm.homework.order.domain.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    
}
