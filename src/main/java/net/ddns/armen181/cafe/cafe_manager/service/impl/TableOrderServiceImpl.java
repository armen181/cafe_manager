package net.ddns.armen181.cafe.cafe_manager.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.OrderStatus;
import net.ddns.armen181.cafe.cafe_manager.repository.TableOrderRepository;
import net.ddns.armen181.cafe.cafe_manager.service.CaffeTableService;
import net.ddns.armen181.cafe.cafe_manager.service.ProductInOrderService;
import net.ddns.armen181.cafe.cafe_manager.util.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

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
    public TableOrder get(Long id) {
        log.info("Try get Order by id -> {}", id);
        return tableOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot get Table In Order by Id"));
    }

    @Override
    public List<TableOrder> get(String userName) {
        log.info("Try get Order by userName -> {}", userName);
        return tableOrderRepository.findAllByUserName(userName).orElseThrow(() -> new NotFoundException("Cannot get Table Order by User Name"));
    }

    @Override
    public List<TableOrder> getByTableName(String tableName) {
        log.info("Try get Order by tableName -> {}", tableName);
        return tableOrderRepository.findAllByCafeTableName(tableName).orElseThrow(() -> new NotFoundException("Cannot get Table Order by Table Name"));
    }

    @Override
    public List<TableOrder> getAll() {
        log.info("Try get All Orders");
        return Lists.newArrayList(tableOrderRepository.findAll());
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
        TableOrder tableOrder = tableOrderRepository.findById(tableOrderId).orElseThrow(() -> new NotFoundException("Cannot get Table Order by Id for signProductInOrder"));
        ProductInOrder productInOrder = productInOrderService.get(productInOrderId);
        log.info("Try to Sign Order by id -> {}, and ProductInOrder by id - > {}", tableOrderId, productInOrderId);
        tableOrder.addProductInOrder(productInOrder);
        return tableOrderRepository.save(tableOrder);
    }

    @Override
    @Transactional
    public TableOrder edit(Long id, OrderStatus status) {
        TableOrder tableOrder = this.get(id);
        tableOrder.setOrderStatus(status);
        log.info("Try to edit order by id -> {}", id);
        return tableOrderRepository.save(tableOrder);
    }
}
