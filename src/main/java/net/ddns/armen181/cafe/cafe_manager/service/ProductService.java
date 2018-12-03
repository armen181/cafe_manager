package net.ddns.armen181.cafe.cafe_manager.service;


import net.ddns.armen181.cafe.cafe_manager.domain.Product;

import java.util.Optional;
import java.util.Set;

public interface ProductService {
    Product create(String name);
    Set<Product> getAll();
    Optional<Product> get(String name);
    void remove(Long id);
}
