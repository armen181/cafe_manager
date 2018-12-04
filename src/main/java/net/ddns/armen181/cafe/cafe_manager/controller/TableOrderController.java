package net.ddns.armen181.cafe.cafe_manager.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.Dto.TableOrderDto;
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

import java.util.List;
import java.util.stream.Collectors;

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
    public ResponseEntity<TableOrderDto> orderGetById(@NonNull @RequestHeader Long id) {
        TableOrder tableOrder = tableOrderService.get(id);
        return new ResponseEntity<>(TableOrderToTableOrderDto(tableOrder), HttpStatus.OK);
    }

    @GetMapping("/orderGet")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<List<TableOrderDto>> orderGet() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        final List<TableOrderDto> tableOrderDtos = tableOrderService.get(userName).stream().map(this::TableOrderToTableOrderDto).collect(Collectors.toList());
        return new ResponseEntity<>(tableOrderDtos, HttpStatus.OK);
    }


    @GetMapping("/orderGetByTableName")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<List<TableOrderDto>> orderGetByTableName(@NonNull @RequestHeader String tableName) {
        final List<TableOrderDto> tableOrderDtos = tableOrderService.get(tableName).stream().map(this::TableOrderToTableOrderDto).collect(Collectors.toList());
        return new ResponseEntity<>(tableOrderDtos, HttpStatus.OK);
    }


    @GetMapping("/orderSignToProductInOrder")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<TableOrderDto> orderGet(@NonNull @RequestHeader Long orderId, @NonNull @RequestHeader Long productInOrderId) {
        TableOrder tableOrder = tableOrderService.signProductInOrder(orderId, productInOrderId);
        return new ResponseEntity<>(TableOrderToTableOrderDto(tableOrder), HttpStatus.OK);
    }

    @GetMapping("/orderCreat")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<TableOrderDto> orderGet(@NonNull @RequestHeader String name) {
        TableOrder tableOrder = tableOrderService.create(name);
        return new ResponseEntity<>(TableOrderToTableOrderDto(tableOrder), HttpStatus.OK);
    }

    @GetMapping("/orderRemoveById")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity orderRemoveById(@NonNull @RequestHeader Long id) {
        tableOrderService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/orderEditById")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<TableOrderDto> orderEditById(@NonNull @RequestHeader Long id, @NonNull @RequestHeader OrderStatus status) {
        TableOrder tableOrder = tableOrderService.edit(id, status);
        return new ResponseEntity<>(TableOrderToTableOrderDto(tableOrder), HttpStatus.OK);
    }

    private TableOrderDto TableOrderToTableOrderDto(TableOrder tableOrder) {
        TableOrderDto tableOrderDto = new TableOrderDto();
        tableOrderDto.setId(tableOrder.getId());
        tableOrderDto.setCafeTableName(tableOrder.getCafeTableName());
        tableOrderDto.setName(tableOrder.getName());
        tableOrderDto.setUserName(tableOrder.getUserName());
        tableOrderDto.setOrderStatus(tableOrder.getOrderStatus());
        tableOrderDto.setProductInOrders(tableOrder.getProductInOrders());
        return tableOrderDto;
    }

}
