package net.ddns.armen181.cafe.cafe_manager.repository;


import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CafeTableRepository extends CrudRepository<CafeTable, Long> {
    Optional<CafeTable> findByName(String userName);
    Optional<List<CafeTable>> findAllByUserName (String name);
}
