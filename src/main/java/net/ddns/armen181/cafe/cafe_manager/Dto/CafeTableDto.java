package net.ddns.armen181.cafe.cafe_manager.Dto;

import lombok.Data;
import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;

import java.util.Set;

@Data
public class CafeTableDto {


    private Long id;

    private String name;

    private String userName;

    private Boolean isAttachOrder;

    private Set<TableOrder> tableOrders;
}
