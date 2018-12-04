package net.ddns.armen181.cafe.cafe_manager.service;


import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.OrderStatus;

import java.util.List;

public interface CaffeTableService {
    TableOrder create(String name);

    TableOrder get(Long id);

    List<TableOrder> get(String userName);

    List<TableOrder> getByTableName(String tableName);

    List<TableOrder> getAll();

    void remove(Long id);

    TableOrder signProductInOrder(Long tableOrderId, Long productInOrderId);

    TableOrder edit(Long id, OrderStatus status);
}
