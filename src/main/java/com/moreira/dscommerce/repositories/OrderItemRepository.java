package com.moreira.dscommerce.repositories;

import com.moreira.dscommerce.entities.Order;
import com.moreira.dscommerce.entities.OrderItem;
import com.moreira.dscommerce.entities.OrderItemPK;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, OrderItemPK> {
}
