package net.ddns.armen181.cafe.cafe_manager.service.impl;


import com.google.common.collect.Sets;
import net.ddns.armen181.cafe.cafe_manager.domain.Product;
import net.ddns.armen181.cafe.cafe_manager.repository.ProductRepository;
import net.ddns.armen181.cafe.cafe_manager.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

@Service
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }


    @Override
    public Product create(String name) {
        Product product = new Product();
        product.setName(name);
        return productRepository.save(product);
    }

    @Override
    public Set<Product> getAll() {
        return Sets.newHashSet(productRepository.findAll());
    }

    @Override
    public Optional<Product> get(String name) {
        return productRepository.findByName(name);
    }

    @Override
    public void remove(Long id) {
        Optional<Product> product = productRepository.findById(id);
        product.ifPresent(productRepository::delete);
    }
}
