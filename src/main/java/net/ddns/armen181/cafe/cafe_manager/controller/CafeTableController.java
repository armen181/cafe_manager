package net.ddns.armen181.cafe.cafe_manager.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.Dto.UserDto;
import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;
import net.ddns.armen181.cafe.cafe_manager.service.CafeTableService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

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
    public ResponseEntity<CafeTable> userGetById(@NonNull @RequestHeader Long id) {
        Optional<CafeTable> cafeTable = cafeTableService.get(id);
        return cafeTable.map(cafeTable1 -> new ResponseEntity<>(cafeTable1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new CafeTable(), HttpStatus.OK));
    }



}
