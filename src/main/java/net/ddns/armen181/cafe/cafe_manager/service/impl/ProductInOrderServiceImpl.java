package net.ddns.armen181.cafe.cafe_manager.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.Product;
import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.ProductInOrderStatus;
import net.ddns.armen181.cafe.cafe_manager.repository.ProductInOrderRepository;
import net.ddns.armen181.cafe.cafe_manager.service.ProductInOrderService;
import net.ddns.armen181.cafe.cafe_manager.service.ProductService;
import net.ddns.armen181.cafe.cafe_manager.util.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ProductInOrderServiceImpl implements ProductInOrderService {

    private final ProductInOrderRepository productInOrderRepository;
    private final ProductService productService;

    public ProductInOrderServiceImpl(ProductInOrderRepository productInOrderRepository, ProductService productService) {
        this.productInOrderRepository = productInOrderRepository;
        this.productService = productService;
    }

    @Override
    public ProductInOrder create(int amount, ProductInOrderStatus status) {
        ProductInOrder productInOrder = new ProductInOrder();
        productInOrder.setAmount(amount);
        productInOrder.setStatus(status);
        log.info("Try to create productInOrder");
        return productInOrderRepository.save(productInOrder);
    }

    @Override
    public ProductInOrder edit(Long id, int amount, ProductInOrderStatus status) {
        ProductInOrder productInOrder = this.get(id);
        productInOrder.setStatus(status);
        productInOrder.setAmount(amount);
        log.info("Try to edit productInOrder by id -> {}", id);
        return productInOrderRepository.save(productInOrder);
    }

    @Override
    public List<ProductInOrder> getAll() {
        log.info("Try to GetAll productInOrder");
        return Lists.newArrayList(productInOrderRepository.findAll());
    }

    @Override
    public List<ProductInOrder> getAllByOrderName(String orderName) {
        log.info("Try to GetAllByOrderName productInOrder by orderName -> {}", orderName);
        return productInOrderRepository.findAllByOrderName(orderName).orElseThrow(() -> new NotFoundException("Cannot get Product In Order by Order name"));
    }

    @Override
    public ProductInOrder get(Long id) {
        log.info("Try to get productInOrder by id -> {}", id);
        return productInOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot get Product in Order by Id"));
    }

    @Override
    public void remove(Long id) {
        log.info("Try to Remove productInOrder by id -> {}", id);
        ProductInOrder productInOrder = productInOrderRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot get Product in Order by Id for delete"));
        productInOrderRepository.delete(productInOrder);
    }

    @Override
    @Transactional
    public ProductInOrder signProduct(Long productInOrderId, Long productId, int amount) {
        ProductInOrder productInOrder = productInOrderRepository.findById(productInOrderId).orElseThrow(() -> new NotFoundException("Cannot find productInOrder by Id for Signing"));
        Product product = productService.get(productId);
        productInOrder.setAmount(amount);
        productInOrder.addProduct(product);
        log.info("Try to Sign productInOrder by id -> {}, to product by id ->{}", productInOrderId, productId);
        return productInOrderRepository.save(productInOrder);
    }
}
