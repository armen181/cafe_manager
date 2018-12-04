package net.ddns.armen181.cafe.cafe_manager.service.impl;


import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.Product;
import net.ddns.armen181.cafe.cafe_manager.repository.ProductRepository;
import net.ddns.armen181.cafe.cafe_manager.service.ProductService;
import net.ddns.armen181.cafe.cafe_manager.util.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

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
        Product product = this.get(id);
        product.setName(name);
        log.info("Try to edit Product by id -> {}", id);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        log.info("Try to getAll Product ");
        return Lists.newArrayList(productRepository.findAll());
    }

    @Override
    public Product get(String name) {
        log.info("Try to get Product by name -> {}", name);
        return productRepository.findByName(name).orElseThrow(() -> new NotFoundException("Cannot get Product by Name"));
    }

    @Override
    public Product get(Long id) {
        log.info("Try to get Product by id -> {}", id);
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot get Product by Id"));
    }

    @Override
    public void remove(Long id) {
        log.info("Try to delete Product by id -> {}", id);
        Product product = productRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot get Product by Id for remove"));
        productRepository.delete(product);
    }
}
