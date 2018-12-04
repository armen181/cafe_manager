package net.ddns.armen181.cafe.cafe_manager.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.Dto.CafeTableDto;
import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;
import net.ddns.armen181.cafe.cafe_manager.service.CafeTableService;
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
public class CafeTableController {

    private final CafeTableService cafeTableService;

    public CafeTableController(CafeTableService cafeTableService) {
        this.cafeTableService = cafeTableService;
    }


    @GetMapping("/tableGetById")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<CafeTableDto> tableGetById(@NonNull @RequestHeader Long id) {
        CafeTable cafeTable = cafeTableService.get(id);
        return new ResponseEntity<>(CafeTableToCafeTableDto(cafeTable), HttpStatus.OK);
    }

    @GetMapping("/tableCreate")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<CafeTableDto> tableCreate(@NonNull @RequestHeader String name) {
        CafeTable cafeTable = cafeTableService.create(name);
        return new ResponseEntity<>(CafeTableToCafeTableDto(cafeTable), HttpStatus.OK);
    }

    @GetMapping("/tableGetAll")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<List<CafeTableDto>> tableGetAll() {
        final List<CafeTableDto> cafeTableDtos = cafeTableService.getAll().stream().map(this::CafeTableToCafeTableDto).collect(Collectors.toList());
        return new ResponseEntity<>(cafeTableDtos, HttpStatus.OK);
    }

    @GetMapping("/tableSignOrder")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<CafeTableDto> tableSignOrder(@NonNull @RequestHeader Long cafeTableId, @NonNull @RequestHeader Long tableOrderId) {
        CafeTable cafeTable = cafeTableService.signTableOrder(cafeTableId, tableOrderId);
        return new ResponseEntity<>(CafeTableToCafeTableDto(cafeTable), HttpStatus.OK);

    }

    @GetMapping("/tableGet")
    @PreAuthorize("hasAnyRole('WAITER', 'MANAGER')")
    public ResponseEntity<List<CafeTableDto>> tableGet() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        final List<CafeTableDto> cafeTableDtos = cafeTableService.getAll(userName).stream().map(this::CafeTableToCafeTableDto).collect(Collectors.toList());
        return new ResponseEntity<>(cafeTableDtos, HttpStatus.OK);

    }

    @GetMapping("/tableRemoveById")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity tableRemoveById(@NonNull @RequestHeader Long id) {
        cafeTableService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/tableSignUser")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<CafeTableDto> tableSignUser(@NonNull @RequestHeader Long cafeTableId, @NonNull @RequestHeader Long waiterId) {
        CafeTable cafeTable = cafeTableService.signTableWaiter(cafeTableId, waiterId);
        return new ResponseEntity<>(CafeTableToCafeTableDto(cafeTable), HttpStatus.OK);

    }

    private CafeTableDto CafeTableToCafeTableDto(CafeTable cafeTable) {
        CafeTableDto cafeTableDto = new CafeTableDto();
        cafeTableDto.setId(cafeTable.getId());
        cafeTableDto.setIsAttachOrder(cafeTable.getIsAttachOrder());
        cafeTableDto.setName(cafeTable.getName());
        cafeTableDto.setUserName(cafeTable.getUserName());
        cafeTableDto.setTableOrders(cafeTable.getTableOrders());
        return cafeTableDto;
    }

}
