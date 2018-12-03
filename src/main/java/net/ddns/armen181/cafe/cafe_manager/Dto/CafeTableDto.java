package net.ddns.armen181.cafe.cafe_manager.Dto;

import lombok.Data;

@Data
public class CafeTableDto {


    private Long id;

    private String name;

    private String userName;

    private Boolean isOrder;

    private Boolean isAttachOrder;
}
