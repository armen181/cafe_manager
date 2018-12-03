package net.ddns.armen181.cafe.cafe_manager.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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
    public ResponseEntity<CafeTable> tableGetById(@NonNull @RequestHeader Long id) {
        Optional<CafeTable> cafeTable = cafeTableService.get(id);
        return cafeTable.map(cafeTable1 -> new ResponseEntity<>(cafeTable1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new CafeTable(), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/tableCreate")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<CafeTable> tableCreate(@NonNull @RequestHeader String name) {
        CafeTable cafeTable = cafeTableService.create(name);
        Optional<CafeTable> optionalCafeTable = cafeTableService.get(cafeTable.getId());
        return optionalCafeTable.map(cafeTable1 -> new ResponseEntity<>(cafeTable1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new CafeTable(), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/tableGetAll")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<Set<CafeTable>> tableGetAll() {
        return new ResponseEntity<>(cafeTableService.getAll(), HttpStatus.OK);

          }
    @GetMapping("/tableSignOrder")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<CafeTable> tableSignOrder(@NonNull @RequestHeader Long cafeTableId,@NonNull @RequestHeader Long tableOrderId) {
        CafeTable cafeTable = cafeTableService.signTableOrder(cafeTableId,tableOrderId);
        return new ResponseEntity<>(cafeTable, cafeTable.getId() != null ? HttpStatus.OK : HttpStatus.BAD_REQUEST);

    }

    @GetMapping("/tableGet")
    @PreAuthorize("hasAnyRole('WAITER', 'MANAGER')")
    public ResponseEntity<List<CafeTable>> tableGet() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        Optional<List<CafeTable>> cafeTable = cafeTableService.getAll(userName);
        return cafeTable.map(cafeTables -> new ResponseEntity<>(cafeTables, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<List<CafeTable>>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST));

    }

    @GetMapping("/tableRemoveById")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity tableRemoveById(@NonNull @RequestHeader Long id) {
        cafeTableService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }


}
