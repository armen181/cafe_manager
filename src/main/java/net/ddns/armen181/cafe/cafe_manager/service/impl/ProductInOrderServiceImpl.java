package net.ddns.armen181.cafe.cafe_manager.service.impl;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.Product;
import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.ProductInOrderStatus;
import net.ddns.armen181.cafe.cafe_manager.repository.ProductInOrderRepository;
import net.ddns.armen181.cafe.cafe_manager.service.ProductInOrderService;
import net.ddns.armen181.cafe.cafe_manager.service.ProductService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

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
        Optional<ProductInOrder> productInOrder = this.get(id);
        if (productInOrder.isPresent()) {
            productInOrder.get().setStatus(status);
            productInOrder.get().setAmount(amount);
            log.info("Try to edit productInOrder by id -> {}", id);
            return productInOrderRepository.save(productInOrder.get());
        }
        log.info("Cannot edit productInOrder by id -> {}", id);
        return new ProductInOrder();
    }

    @Override
    public List<ProductInOrder> getAll() {
        log.info("Try to GetAll productInOrder");
        return Lists.newArrayList(productInOrderRepository.findAll());
    }

    @Override
    public Optional<List<ProductInOrder>> getAllByOrderName(String orderName) {
        log.info("Try to GetAllByOrderName productInOrder by orderName -> {}", orderName);
        return productInOrderRepository.findAllByOrderName(orderName);
    }

    @Override
    public Optional<ProductInOrder> get(Long id) {
        log.info("Try to get productInOrder by id -> {}", id);
        return productInOrderRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        Optional< ProductInOrder> optional = productInOrderRepository.findById(id);
        optional.ifPresent(productInOrderRepository::delete);
        log.info("Try to Remove productInOrder by id -> {}", id);
    }

    @Override
    @Transactional
    public ProductInOrder signProduct(Long productInOrderId, Long productId, int amount) {
        Optional<ProductInOrder> productInOrder = productInOrderRepository.findById(productInOrderId);
        if (productInOrder.isPresent()) {
            Optional<Product> optionalProduct = productService.get(productId);
            if(optionalProduct.isPresent()){
              productInOrder.get().setAmount(amount);
              productInOrder.get().addProduct(optionalProduct.get());
                log.info("Try to Sign productInOrder by id -> {}, to product by id ->{}" , productInOrderId,productId);
              return  productInOrderRepository.save(productInOrder.get());
            }
        }
        return new ProductInOrder();
    }
}
