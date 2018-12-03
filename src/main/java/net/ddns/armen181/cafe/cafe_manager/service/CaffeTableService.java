package net.ddns.armen181.cafe.cafe_manager.service;


import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.OrderStatus;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface CaffeTableService {
    TableOrder create(String name);
    Optional<TableOrder> get(Long id);
    Optional<List<TableOrder>> get(String userName);
    Optional<List<TableOrder>> getByTableName(String tableName);
    Set<TableOrder> getAll();
    void remove(Long id);
    TableOrder signProductInOrder(Long tableOrderId, Long productInOrderId);
    TableOrder edit(Long id, OrderStatus status);
}
