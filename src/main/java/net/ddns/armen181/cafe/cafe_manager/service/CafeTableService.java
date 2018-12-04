package net.ddns.armen181.cafe.cafe_manager.service;


import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;

import java.util.List;

public interface CafeTableService {
    CafeTable create(String name);

    List<CafeTable> getAll();

    List<CafeTable> getAll(String userName);

    CafeTable get(Long id);

    void remove(Long id);

    CafeTable signTableOrder(Long cafeTableId, Long tableOrderId);

    CafeTable signTableWaiter(Long cafeTableId, Long waiterId);
}
