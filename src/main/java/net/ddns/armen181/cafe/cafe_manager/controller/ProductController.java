package net.ddns.armen181.cafe.cafe_manager.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.Dto.ProductDto;
import net.ddns.armen181.cafe.cafe_manager.domain.Product;
import net.ddns.armen181.cafe.cafe_manager.service.ProductService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/rest")
@Slf4j
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/productCreate")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<ProductDto> productInOrderCreate(@NonNull @RequestHeader String name) {
        Product product = productService.create(name);
        return new ResponseEntity<>(ProductToDto(product), HttpStatus.OK);
    }

    @GetMapping("/productRemove")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity productRemove(@NonNull @RequestHeader Long id) {
        productService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/productGet")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductDto> productInOrderCreate(@NonNull @RequestHeader Long id) {
        Product product = productService.get(id);
        return new ResponseEntity<>(ProductToDto(product), HttpStatus.OK);
    }

    @GetMapping("/productGetByName")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<ProductDto> productGetByName(@NonNull @RequestHeader String productName) {
        Product product = productService.get(productName);
        return new ResponseEntity<>(ProductToDto(product), HttpStatus.OK);
    }

    @GetMapping("/productGetAll")
    @PreAuthorize("hasAnyRole('MANAGER','WAITER')")
    public ResponseEntity<List<ProductDto>> productGetAll() {
        final List<ProductDto> products = productService.getAll().stream().map(this::ProductToDto).collect(Collectors.toList());
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/productEdit")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<ProductDto> productGetAll(@NonNull @RequestHeader Long id, @NonNull @RequestHeader String productName) {
        Product product = productService.edit(id, productName);
        return new ResponseEntity<>(ProductToDto(product), HttpStatus.OK);
    }

    private ProductDto ProductToDto(Product product) {
        ProductDto productDto = new ProductDto();
        productDto.setId(product.getId());
        productDto.setName(product.getName());
        return productDto;
    }
}
