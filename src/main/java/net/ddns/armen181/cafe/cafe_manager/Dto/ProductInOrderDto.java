package net.ddns.armen181.cafe.cafe_manager.Dto;

import lombok.Data;
import net.ddns.armen181.cafe.cafe_manager.domain.Product;
import net.ddns.armen181.cafe.cafe_manager.enums.ProductInOrderStatus;

@Data
public class ProductInOrderDto {

    private Long id;

    private int amount;

    private ProductInOrderStatus status;

    private String orderName;

    private Product product;

}
