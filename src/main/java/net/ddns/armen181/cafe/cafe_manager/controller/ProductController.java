package net.ddns.armen181.cafe.cafe_manager.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
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
import java.util.Optional;

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
    public ResponseEntity<Product> productInOrderCreate(@NonNull @RequestHeader String name) {
       Product product = productService.create(name);
        return new ResponseEntity<>(product,product.getId()!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/productRemove")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity productRemove(@NonNull @RequestHeader Long id) {
        productService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/productGet")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<Product> productInOrderCreate(@NonNull @RequestHeader Long id) {
        Optional<Product> product = productService.get(id);
        return product.map(product1 -> new ResponseEntity<>(product1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new Product(), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/productGetByName")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<Product> productGetByName(@NonNull @RequestHeader String productName) {
        Optional<Product> product = productService.get(productName);
        return product.map(product1 -> new ResponseEntity<>(product1, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new Product(), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/productGetAll")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<List<Product>> productGetAll() {
        return new ResponseEntity<>(productService.getAll(),HttpStatus.OK);
    }

    @GetMapping("/productEdit")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<Product> productGetAll(@NonNull @RequestHeader Long id, @NonNull @RequestHeader String productName) {
       Product product = productService.edit(id,productName);
        return new ResponseEntity<>(product, product.getId()!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

}
