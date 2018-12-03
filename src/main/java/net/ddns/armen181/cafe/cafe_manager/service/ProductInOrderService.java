package net.ddns.armen181.cafe.cafe_manager.service;


import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.ProductInOrderStatus;

import java.util.Optional;
import java.util.Set;

public interface ProductInOrderService {
    ProductInOrder create(int amount, ProductInOrderStatus status);
    Set<ProductInOrder> getAll();
    Optional<ProductInOrder> get(Long id);
    void remove(Long id);
    ProductInOrder signProduct(Long productInOrderId, String productName, int amount);
}
