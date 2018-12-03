package net.ddns.armen181.cafe.cafe_manager.repository;


import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TableOrderRepository extends CrudRepository<TableOrder, Long> {
    Optional<TableOrder> findByName(String userName);
}
