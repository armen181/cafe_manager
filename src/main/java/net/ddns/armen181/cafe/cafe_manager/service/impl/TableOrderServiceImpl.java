package net.ddns.armen181.cafe.cafe_manager.service.impl;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.OrderStatus;
import net.ddns.armen181.cafe.cafe_manager.repository.TableOrderRepository;
import net.ddns.armen181.cafe.cafe_manager.service.ProductInOrderService;
import net.ddns.armen181.cafe.cafe_manager.service.CaffeTableService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class TableOrderServiceImpl implements CaffeTableService {
    private final TableOrderRepository tableOrderRepository;
    private final ProductInOrderService productInOrderService;

    public TableOrderServiceImpl(TableOrderRepository tableOrderRepository, ProductInOrderService productInOrderService) {
        this.tableOrderRepository = tableOrderRepository;
        this.productInOrderService = productInOrderService;
    }

    @Override
    public TableOrder create(String name) {
        TableOrder tableOrder = new TableOrder();
        tableOrder.setName(name);
        tableOrder.setOrderStatus(OrderStatus.OPEN);
        log.info("Creat Order by Name -> {}", name);
        return tableOrderRepository.save(tableOrder);
    }

    @Override
    public Optional<TableOrder> get(Long id) {
        log.info("Try get Order by id -> {}", id);
        return tableOrderRepository.findById(id);
    }

    @Override
    public Optional<List<TableOrder>> get(String userName) {
        log.info("Try get Order by userName -> {}", userName);
        return tableOrderRepository.findAllByUserName(userName);
    }

    @Override
    public Optional<List<TableOrder>> getByTableName(String tableName) {
        log.info("Try get Order by tableName -> {}", tableName);
        return tableOrderRepository.findAllByCafeTableName(tableName);
    }

    @Override
    public Set<TableOrder> getAll() {
        log.info("Try get All Orders");
        return Sets.newHashSet(tableOrderRepository.findAll());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Optional<TableOrder> tableOrder = tableOrderRepository.findById(id);
        tableOrder.ifPresent(tableOrderRepository::delete);
        log.info("Try delete Order by id -> {}", id);

    }

    @Override
    @Transactional
    public TableOrder signProductInOrder(Long tableOrderId, Long productInOrderId) {
        Optional<TableOrder> tableOrder = tableOrderRepository.findById(tableOrderId);
        if (tableOrder.isPresent()) {
            Optional<ProductInOrder> productInOrder = productInOrderService.get(productInOrderId);
            if (productInOrder.isPresent()) {
                log.info("Try to Sign Order by id -> {}, and ProductInOrder by id - > {}", tableOrderId, productInOrderId);
                tableOrder.get().addProductInOrder(productInOrder.get());
                return tableOrderRepository.save(tableOrder.get());
            }
            log.info("Cannot find  productInOrder by id -> {}", productInOrderId);

        }
        log.info("Cannot find  order by id -> {}", tableOrder);
        return new TableOrder();
    }

    @Override
    @Transactional
    public TableOrder edit(Long id, OrderStatus status) {
      Optional<TableOrder> tableOrder =  this.get(id);
        if (tableOrder.isPresent()) {
            tableOrder.get().setOrderStatus(status);
            log.info("Try to edit order by id -> {}", id);
            return tableOrderRepository.save(tableOrder.get());
        }
        log.info("Cannot find  order by id -> {}", id);
        return new TableOrder();
    }
}
