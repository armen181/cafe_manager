package net.ddns.armen181.cafe.cafe_manager.controller;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
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

import java.util.Collections;
import java.util.List;
import java.util.Optional;

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
    public ResponseEntity<ProductInOrder> productInOrderCreate(@NonNull @RequestHeader Integer amount, @NonNull @RequestHeader ProductInOrderStatus status) {
        ProductInOrder productInOrder = productInOrderService.create(amount, status);
        return new ResponseEntity<>(productInOrder, productInOrder.getId()!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/productInOrderGetByOrderName")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<List<ProductInOrder>> productInOrderGetByOrderName(@NonNull @RequestHeader String orderName) {
        Optional<List<ProductInOrder>> productsInOrder = productInOrderService.getAllByOrderName(orderName);
        return productsInOrder.map(productInOrders -> new ResponseEntity<>(productInOrders, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<List<ProductInOrder>>(Collections.EMPTY_LIST, HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/productInOrderGet")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrder> productInOrderGet(@NonNull @RequestHeader Long id) {
        Optional<ProductInOrder> productsInOrder = productInOrderService.get(id);
        return productsInOrder.map(productInOrder -> new ResponseEntity<>(productInOrder, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(new ProductInOrder(), HttpStatus.BAD_REQUEST));
    }

    @GetMapping("/productInOrderRemove")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity productInOrderRemove(@NonNull @RequestHeader Long id) {
        productInOrderService.remove(id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/productInOrderEdit")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrder> productInOrderEdit(@NonNull @RequestHeader Long id,@NonNull @RequestHeader Integer amount,@NonNull @RequestHeader ProductInOrderStatus status ) {
        ProductInOrder productInOrder = productInOrderService.edit(id, amount,status);
        return new ResponseEntity<>(productInOrder, productInOrder.getId()!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST );
    }

    @GetMapping("/productInOrderSignProduct")
    @PreAuthorize("hasAnyRole('MANAGER', 'WAITER')")
    public ResponseEntity<ProductInOrder> productInOrderSignProduct(@NonNull @RequestHeader Long praductinorderid,@NonNull @RequestHeader Long productId, @NonNull @RequestHeader Integer amount) {
        ProductInOrder productInOrder = productInOrderService.signProduct(praductinorderid,productId,amount);
        return new ResponseEntity<>(productInOrder, productInOrder.getId()!=null?HttpStatus.OK:HttpStatus.BAD_REQUEST);
    }


}
