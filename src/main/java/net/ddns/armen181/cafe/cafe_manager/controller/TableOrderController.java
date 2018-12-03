package net.ddns.armen181.cafe.cafe_manager.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.OrderStatus;
import net.ddns.armen181.cafe.cafe_manager.service.CaffeTableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rest")
@Slf4j
public class TableOrderController {

    private final CaffeTableService tableOrderService;

    public TableOrderController(CaffeTableService tableOrderService) {
        this.tableOrderService = tableOrderService;
    }


    @GetMapping("/orderGetById")
    @PreAuthorize("hasAnyRole('WAITER','MANAGER')")
    public ResponseEntity<TableOrder> orderGetById(@NonNull @RequestHeader Long id) {
        Optional<TableOrder> tableOrder = tableOrderService.get(id);
        return tableOrder.map(cafeTable1 -> new ResponseEntity<>(cafeTable1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new TableOrder(), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/orderGet")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<List<TableOrder>> orderGet() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        Optional<List<TableOrder>> tableOrder = tableOrderService.get(userName);
        return tableOrder.map(cafeTable1 -> new ResponseEntity<>(tableOrder.get(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<List<TableOrder>>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST));
    }


    @GetMapping("/orderGetByTableName")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<List<TableOrder>> orderGetByTableName(@NonNull @RequestHeader String tableName) {

        Optional<List<TableOrder>> tableOrder = tableOrderService.getByTableName(tableName);
        return tableOrder.map(cafeTable1 -> new ResponseEntity<>(tableOrder.get(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<List<TableOrder>>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST));
    }


    @GetMapping("/orderSignToProductInOrder")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<TableOrder> orderGet(@NonNull @RequestHeader Long orderId,@NonNull @RequestHeader Long productInOrderId) {
        TableOrder tableOrder = tableOrderService.signProductInOrder(orderId,productInOrderId);
        return new ResponseEntity<>(tableOrder, tableOrder.getId()!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/orderCreat")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<TableOrder> orderGet(@NonNull @RequestHeader String  name) {
        TableOrder tableOrder = tableOrderService.create(name);
        return new ResponseEntity<>(tableOrder, HttpStatus.OK);
    }

    @GetMapping("/orderRemoveById")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity orderRemoveById(@NonNull @RequestHeader Long id) {
        tableOrderService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/orderEditById")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<TableOrder> orderEditById(@NonNull @RequestHeader Long id, @NonNull @RequestHeader OrderStatus status) {
        TableOrder tableOrder = tableOrderService.edit(id,status);
        return new ResponseEntity<>(tableOrder,tableOrder.getId()!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

}
