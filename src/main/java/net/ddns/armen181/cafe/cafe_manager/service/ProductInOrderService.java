package net.ddns.armen181.cafe.cafe_manager.service;


import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.ProductInOrderStatus;

import java.util.List;

public interface ProductInOrderService {
    ProductInOrder create(int amount, ProductInOrderStatus status);

    ProductInOrder edit(Long id, int amount, ProductInOrderStatus status);

    List<ProductInOrder> getAll();

    List<ProductInOrder> getAllByOrderName(String orderName);

    ProductInOrder get(Long id);

    void remove(Long id);

    ProductInOrder signProduct(Long productInOrderId, Long productId, int amount);
}
