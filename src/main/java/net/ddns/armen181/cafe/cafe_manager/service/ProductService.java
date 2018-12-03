package net.ddns.armen181.cafe.cafe_manager.service;


import net.ddns.armen181.cafe.cafe_manager.domain.Product;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface ProductService {
    Product create(String name);
    Product edit (Long id, String name);
    List<Product> getAll();
    Optional<Product> get(String name);
    Optional<Product> get(Long id);
    void remove(Long id);
}
