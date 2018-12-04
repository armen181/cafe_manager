package net.ddns.armen181.cafe.cafe_manager.repository;


import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductInOrderRepository extends CrudRepository<ProductInOrder, Long> {
        Optional<List<ProductInOrder>> findAllByOrderName (String orderName);
}
