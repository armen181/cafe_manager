package net.ddns.armen181.cafe.cafe_manager.service.impl;

import com.google.common.collect.Sets;
import net.ddns.armen181.cafe.cafe_manager.domain.Product;
import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.ProductInOrderStatus;
import net.ddns.armen181.cafe.cafe_manager.repository.ProductInOrderRepository;
import net.ddns.armen181.cafe.cafe_manager.service.ProductInOrderService;
import net.ddns.armen181.cafe.cafe_manager.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
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
        return productInOrderRepository.save(productInOrder);
    }

    @Override
    public Set<ProductInOrder> getAll() {
        return Sets.newHashSet(productInOrderRepository.findAll());
    }

    @Override
    public Optional<ProductInOrder> get(Long id) {
        return productInOrderRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        Optional< ProductInOrder> optional = productInOrderRepository.findById(id);
        optional.ifPresent(productInOrderRepository::delete);
    }

    @Override
    public ProductInOrder signProduct(Long productInOrderId, String productName, int amount) {
        Optional<ProductInOrder> productInOrder = productInOrderRepository.deleteAllById(productInOrderId);
        if (productInOrder.isPresent()) {
            Optional<Product> optionalProduct = productService.get(productName);
            if(optionalProduct.isPresent()){
              productInOrder.get().setAmount(amount);
              productInOrder.get().addProduct(optionalProduct.get());
              return  productInOrderRepository.save(productInOrder.get());
            }
        }
        return null;
    }
}
