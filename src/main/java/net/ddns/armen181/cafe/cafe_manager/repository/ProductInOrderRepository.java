package net.ddns.armen181.cafe.cafe_manager.repository;


import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductInOrderRepository extends CrudRepository<ProductInOrder, Long> {
        Optional<ProductInOrder> deleteAllById(Long id);
}
