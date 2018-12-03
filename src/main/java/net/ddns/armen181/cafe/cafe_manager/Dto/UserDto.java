package net.ddns.armen181.cafe.cafe_manager.Dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import net.ddns.armen181.cafe.cafe_manager.enums.Role;

import javax.persistence.*;

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
