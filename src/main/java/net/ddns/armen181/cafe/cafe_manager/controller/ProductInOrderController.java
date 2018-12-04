package net.ddns.armen181.cafe.cafe_manager.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.Dto.ProductInOrderDto;
import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.ProductInOrderStatus;
import net.ddns.armen181.cafe.cafe_manager.service.ProductInOrderService;
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
public class ProductInOrderController {

    private final ProductInOrderService productInOrderService;

    public ProductInOrderController(ProductInOrderService productInOrderService) {
        this.productInOrderService = productInOrderService;
    }

    @GetMapping("/productInOrderCreate")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrderDto> productInOrderCreate(@NonNull @RequestHeader Integer amount, @NonNull @RequestHeader ProductInOrderStatus status) {
        ProductInOrder productInOrder = productInOrderService.create(amount, status);
        return new ResponseEntity<>(ProductInOrderToDto(productInOrder), HttpStatus.OK);
    }

    @GetMapping("/productInOrderGetByOrderName")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<List<ProductInOrderDto>> productInOrderGetByOrderName(@NonNull @RequestHeader String orderName) {
        final List<ProductInOrderDto> productsInOrder = productInOrderService.getAllByOrderName(orderName).stream().map(this::ProductInOrderToDto).collect(Collectors.toList());
        return new ResponseEntity<>(productsInOrder, HttpStatus.OK);
    }

    @GetMapping("/productInOrderGet")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrderDto> productInOrderGet(@NonNull @RequestHeader Long id) {
        ProductInOrder productsInOrder = productInOrderService.get(id);
        return new ResponseEntity<>(ProductInOrderToDto(productsInOrder), HttpStatus.OK);
    }

    @GetMapping("/productInOrderRemove")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity productInOrderRemove(@NonNull @RequestHeader Long id) {
        productInOrderService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/productInOrderEdit")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrderDto> productInOrderEdit(@NonNull @RequestHeader Long id, @NonNull @RequestHeader Integer amount, @NonNull @RequestHeader ProductInOrderStatus status) {
        ProductInOrder productInOrder = productInOrderService.edit(id, amount, status);
        return new ResponseEntity<>(ProductInOrderToDto(productInOrder), HttpStatus.OK);
    }

    @GetMapping("/productInOrderSignProduct")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrderDto> productInOrderSignProduct(@NonNull @RequestHeader Long praductinorderid, @NonNull @RequestHeader Long productId, @NonNull @RequestHeader Integer amount) {
        ProductInOrder productInOrder = productInOrderService.signProduct(praductinorderid, productId, amount);
        return new ResponseEntity<>(ProductInOrderToDto(productInOrder), HttpStatus.OK);
    }

    private ProductInOrderDto ProductInOrderToDto(ProductInOrder productInOrder) {
        ProductInOrderDto productInOrderDto = new ProductInOrderDto();
        productInOrderDto.setId(productInOrder.getId());
        productInOrderDto.setAmount(productInOrder.getAmount());
        productInOrderDto.setOrderName(productInOrder.getOrderName());
        productInOrderDto.setProduct(productInOrder.getProduct());
        productInOrderDto.setStatus(productInOrder.getStatus());
        return productInOrderDto;
    }
}
