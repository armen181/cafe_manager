package net.ddns.armen181.cafe.cafe_manager.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;
import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.service.TableOrderService;
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

    private final TableOrderService tableOrderService;

    public TableOrderController(TableOrderService tableOrderService) {
        this.tableOrderService = tableOrderService;
    }

    @GetMapping("/orderGetById")
    @PreAuthorize("hasAnyRole('WAITER','MANAGER')")
    public ResponseEntity<TableOrder> orderGetById(@NonNull @RequestHeader Long id) {
        Optional<TableOrder> tableOrder = tableOrderService.get(id);
        return tableOrder.map(cafeTable1 -> new ResponseEntity<>(cafeTable1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<TableOrder>(new TableOrder(), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/orderGet")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<List<TableOrder>> orderGet() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        Optional<List<TableOrder>> tableOrder = tableOrderService.get(userName);
        return tableOrder.map(cafeTable1 -> new ResponseEntity<>(tableOrder.get(), HttpStatus.OK)).orElseGet(() -> new ResponseEntity<List<TableOrder>>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/orderSignToProductInOrder")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<TableOrder> orderGet(@NonNull @RequestHeader Long orderId,@NonNull @RequestHeader Long productInOrderId) {
        TableOrder tableOrder = tableOrderService.signProductInOrder(orderId,orderId);
        return new ResponseEntity<TableOrder>(tableOrder, HttpStatus.OK);
     }


}
