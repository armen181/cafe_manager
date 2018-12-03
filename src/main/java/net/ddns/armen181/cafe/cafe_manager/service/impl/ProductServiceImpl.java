package net.ddns.armen181.cafe.cafe_manager.service.impl;


import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.Product;
import net.ddns.armen181.cafe.cafe_manager.repository.ProductRepository;
import net.ddns.armen181.cafe.cafe_manager.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product create(String name) {
        Product product = new Product();
        product.setName(name);
        log.info("Try to create Product by name -> {}", product);
        return productRepository.save(product);
    }

    @Override
    public Product edit(Long id, String name) {
        Optional<Product> product = this.get(id);
        if (product.isPresent()) {
            product.get().setName(name);
            log.info("Try to edit Product by id -> {}", id);
            return productRepository.save(product.get());
        }
        log.info("Cannot edit Product by id -> {}", id);
        return new Product();
    }

    @Override
    public List<Product> getAll() {
        log.info("Try to getAll Product ");
        return Lists.newArrayList(productRepository.findAll());
    }

    @Override
    public Optional<Product> get(String name) {
        log.info("Try to get Product by name -> {}", name);
        return productRepository.findByName(name);
    }

    @Override
    public Optional<Product> get(Long id) {
        log.info("Try to get Product by id -> {}", id);
        return productRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(productRepository::delete);
        log.info("Try to delete Product by id -> {}", id);
    }
}
