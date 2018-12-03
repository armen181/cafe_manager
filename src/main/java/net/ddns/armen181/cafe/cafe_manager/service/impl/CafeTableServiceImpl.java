package net.ddns.armen181.cafe.cafe_manager.service.impl;

import com.google.common.collect.Sets;
import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;
import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.repository.CafeTableRepository;
import net.ddns.armen181.cafe.cafe_manager.service.CafeTableService;
import net.ddns.armen181.cafe.cafe_manager.service.TableOrderService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.Set;

@Service
public class CafeTableServiceImpl implements CafeTableService {


    private final CafeTableRepository cafeTableRepository;
    private final TableOrderService tableOrderService;

    public CafeTableServiceImpl(CafeTableRepository cafeTableRepository, TableOrderService tableOrderService) {
        this.cafeTableRepository = cafeTableRepository;
        this.tableOrderService = tableOrderService;
    }

    @Override
    public CafeTable create (String name) {
        CafeTable cafeTable = new CafeTable();
        cafeTable.setName(name);
        return cafeTableRepository.save(cafeTable);
    }

    @Override
    public Set<CafeTable> getAll() {
        return Sets.newHashSet(cafeTableRepository.findAll());
    }

    @Override
    public Optional<CafeTable> get(Long id) {
        return cafeTableRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        Optional<CafeTable> cafeTable = cafeTableRepository.findById(id);
        cafeTable.ifPresent(cafeTableRepository::delete);
    }

    @Override
    @Transactional
    public CafeTable signTableOrder(Long cafeTableId, Long tableOrder) {
        Optional<CafeTable> cafeTable = cafeTableRepository.findById(cafeTableId);
        if (cafeTable.isPresent()) {
            Optional<TableOrder> optionalTableOrder = tableOrderService.get(tableOrder);
            if (optionalTableOrder.isPresent()) {
                //log
                 cafeTable.get().addTableOrder(optionalTableOrder.get());
                 return cafeTableRepository.save(cafeTable.get());
            }
            // log
        }
        // log
        return null;
    }
}
