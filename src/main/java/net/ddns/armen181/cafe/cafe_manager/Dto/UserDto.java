package net.ddns.armen181.cafe.cafe_manager.Dto;

import lombok.Data;
import net.ddns.armen181.cafe.cafe_manager.enums.Role;

@Data
public class UserDto {

    private Long id;
    private String eMail;
    private String firsName;
    private String lastName;
   // @Getter(AccessLevel.NONE)
    //@JsonIgnore
    private String userPassword;
    private Role role;

}
