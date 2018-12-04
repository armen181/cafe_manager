package net.ddns.armen181.cafe.cafe_manager.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;
import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.domain.User;
import net.ddns.armen181.cafe.cafe_manager.repository.CafeTableRepository;
import net.ddns.armen181.cafe.cafe_manager.service.CafeTableService;
import net.ddns.armen181.cafe.cafe_manager.service.CaffeTableService;
import net.ddns.armen181.cafe.cafe_manager.service.UserService;
import net.ddns.armen181.cafe.cafe_manager.util.NotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    public CafeTable create(String name) {
        CafeTable cafeTable = new CafeTable();
        cafeTable.setName(name);
        cafeTable.setIsAttachOrder(false);
        log.info("Try to create CafeTable by name -> {}", name);
        return cafeTableRepository.save(cafeTable);
    }

    @Override
    public List<CafeTable> getAll() {
        log.info("Try to getAll CafeTable ");
        return Lists.newArrayList(cafeTableRepository.findAll());
    }

    @Override
    public List<CafeTable> getAll(String userName) {
        log.info("Try to getAll CafeTable by userName -> {}", userName);
        return cafeTableRepository.findAllByUserName(userName).orElseThrow(() -> new NotFoundException("Cannot get All Cafe Tables by Id"));
    }

    @Override
    public CafeTable get(Long id) {
        log.info("Try to get CafeTable by name -> {}", id);
        return cafeTableRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot get Cafe Table by Id"));
    }

    @Override
    public void remove(Long id) {
        log.info("Try to remove CafeTable by name -> {}", id);
        CafeTable cafeTable = cafeTableRepository.findById(id).orElseThrow(() -> new NotFoundException("Cannot get Cafe Table by Id for remove"));
        cafeTableRepository.delete(cafeTable);
    }

    @Override
    @Transactional
    public CafeTable signTableOrder(Long cafeTableId, Long tableOrderId) {
        CafeTable cafeTable = cafeTableRepository.findById(cafeTableId).orElseThrow(() -> new NotFoundException("Cannot get Cafe Table by Id for signTableOrder"));
        TableOrder tableOrder = tableOrderService.get(tableOrderId);
        cafeTable.addTableOrder(tableOrder);
        log.info("Try to sign CafeTable by id -> {}, to TableOrder by Id -> {}", cafeTableId, tableOrderId);
        return cafeTableRepository.save(cafeTable);
    }

    @Override
    public CafeTable signTableWaiter(Long cafeTableId, Long waiterId) {
        CafeTable cafeTable = cafeTableRepository.findById(cafeTableId).orElseThrow(() -> new NotFoundException("Cannot get Cafe Table by Id for signTableWaiter"));
        User user = userService.get(waiterId);
        user.addCafeTable(cafeTable);
        log.info("Try to sign CafeTable by id -> {}, to User by Id -> {}", cafeTableId, waiterId);
        return cafeTableRepository.save(cafeTable);

    }
}
