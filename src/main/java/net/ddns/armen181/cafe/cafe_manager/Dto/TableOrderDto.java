package net.ddns.armen181.cafe.cafe_manager.Dto;

import lombok.Data;
import net.ddns.armen181.cafe.cafe_manager.domain.ProductInOrder;
import net.ddns.armen181.cafe.cafe_manager.enums.OrderStatus;

import java.util.Set;

@Data
public class TableOrderDto {


    private Long id;

    private String name;

    private String cafeTableName;

    private String userName;

    private OrderStatus orderStatus;

    private Set<ProductInOrder> productInOrders;
}
