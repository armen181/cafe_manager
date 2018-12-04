package net.ddns.armen181.cafe.cafe_manager.service.impl;

import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;
import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.domain.User;
import net.ddns.armen181.cafe.cafe_manager.repository.CafeTableRepository;
import net.ddns.armen181.cafe.cafe_manager.service.CafeTableService;
import net.ddns.armen181.cafe.cafe_manager.service.CaffeTableService;
import net.ddns.armen181.cafe.cafe_manager.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@Slf4j
public class CafeTableServiceImpl implements CafeTableService {


    private final CafeTableRepository cafeTableRepository;
    private final CaffeTableService tableOrderService;
    private final UserService userService;

    public CafeTableServiceImpl(CafeTableRepository cafeTableRepository, CaffeTableService tableOrderService, UserService userService) {
        this.cafeTableRepository = cafeTableRepository;
        this.tableOrderService = tableOrderService;
        this.userService = userService;
    }

    @Override
    public CafeTable create (String name) {
        CafeTable cafeTable = new CafeTable();
        cafeTable.setName(name);
        cafeTable.setIsAttachOrder(false);
        log.info("Try to create CafeTable by name -> {}", name);
        return cafeTableRepository.save(cafeTable);
    }

    @Override
    public Set<CafeTable> getAll() {
        log.info("Try to getAll CafeTable ");
        return Sets.newHashSet(cafeTableRepository.findAll());
    }

    @Override
    public Optional<List<CafeTable>> getAll(String userName) {
        log.info("Try to getAll CafeTable by userName -> {}", userName);
        return cafeTableRepository.findAllByUserName(userName);
    }

    @Override
    public Optional<CafeTable> get(Long id) {
        log.info("Try to get CafeTable by name -> {}", id);
        return cafeTableRepository.findById(id);
    }

    @Override
    public void remove(Long id) {
        Optional<CafeTable> cafeTable = cafeTableRepository.findById(id);
        cafeTable.ifPresent(cafeTableRepository::delete);
        log.info("Try to remove CafeTable by name -> {}", id);
    }

    @Override
    @Transactional
    public CafeTable signTableOrder(Long cafeTableId, Long tableOrderId) {
        Optional<CafeTable> cafeTable = cafeTableRepository.findById(cafeTableId);
        if (cafeTable.isPresent()) {
            Optional<TableOrder> optionalTableOrder = tableOrderService.get(tableOrderId);
            if (optionalTableOrder.isPresent()) {

                 cafeTable.get().addTableOrder(optionalTableOrder.get());
                log.info("Try to sign CafeTable by id -> {}, to TableOrder by Id -> {}", cafeTableId, tableOrderId);
                 return cafeTableRepository.save(cafeTable.get());
            }
            log.info("Cannot find order by ID -> {}", tableOrderId);
        }
        log.info("Cannot find table by ID -> {}", cafeTableId);
        return new CafeTable();
    }

    @Override
    public CafeTable signTableWaiter(Long cafeTableId, Long waiterId) {
        Optional<CafeTable> cafeTable = cafeTableRepository.findById(cafeTableId);
        if (cafeTable.isPresent()) {
            Optional<User> user = userService.get(waiterId);
            if (user.isPresent()) {

                user.get().addCafeTable(cafeTable.get());
                log.info("Try to sign CafeTable by id -> {}, to User by Id -> {}", cafeTableId, waiterId);
                return cafeTableRepository.save(cafeTable.get());
            }
            log.info("Cannot find order by ID -> {}", waiterId);
        }
        log.info("Cannot find table by ID -> {}", cafeTableId);
        return new CafeTable();
    }
}
