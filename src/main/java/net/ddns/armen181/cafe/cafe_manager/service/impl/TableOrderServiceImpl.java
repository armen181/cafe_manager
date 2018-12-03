package net.ddns.armen181.cafe.cafe_manager.service.impl;

import com.google.common.collect.Sets;
import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.OrderStatus;
import net.ddns.armen181.cafe.cafe_manager.repository.TableOrderRepository;
import net.ddns.armen181.cafe.cafe_manager.service.ProductInOrderService;
import net.ddns.armen181.cafe.cafe_manager.service.TableOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class TableOrderServiceImpl implements TableOrderService {
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
        return tableOrderRepository.save(tableOrder);
    }

    @Override
    public Optional<TableOrder> get(Long id) {
        return tableOrderRepository.findById(id);
    }

    @Override
    public Set<TableOrder> getAll() {
        return Sets.newHashSet(tableOrderRepository.findAll());
    }

    @Override
    @Transactional
    public void remove(Long id) {
        Optional<TableOrder> tableOrder = tableOrderRepository.findById(id);
        tableOrder.ifPresent(tableOrderRepository::delete);
    }

    @Override
    public TableOrder signProductInOrder(Long tableOrderId, Long productInOrderId) {
        Optional<TableOrder> tableOrder = tableOrderRepository.findById(tableOrderId);
        if (tableOrder.isPresent()) {
            Optional<ProductInOrder> productInOrder = productInOrderService.get(productInOrderId);
            if (productInOrder.isPresent()) {
                //log
                tableOrder.get().addProductInOrder(productInOrder.get());
                return tableOrderRepository.save(tableOrder.get());
            }
            //log
        }
        //log
        return null;
    }
}
