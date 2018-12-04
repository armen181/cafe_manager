package net.ddns.armen181.cafe.cafe_manager.service;


import net.ddns.armen181.cafe.cafe_manager.domain.Product;

import java.util.List;

public interface ProductService {
    Product create(String name);

    Product edit(Long id, String name);

    List<Product> getAll();

    Product get(String name);

    Product get(Long id);

    void remove(Long id);
}
