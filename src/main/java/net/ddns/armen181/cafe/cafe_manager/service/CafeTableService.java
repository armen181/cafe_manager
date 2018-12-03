package net.ddns.armen181.cafe.cafe_manager.service;


import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;

import java.util.Optional;
import java.util.Set;

public interface CafeTableService {
    CafeTable create(String name);
    Set<CafeTable> getAll();
    Optional<CafeTable> get(Long id);
    void remove(Long id);
    CafeTable signTableOrder(Long cafeTableId, Long tableOrder);
}
