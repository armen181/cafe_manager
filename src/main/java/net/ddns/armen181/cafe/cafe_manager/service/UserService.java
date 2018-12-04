package net.ddns.armen181.cafe.cafe_manager.service;


import net.ddns.armen181.cafe.cafe_manager.domain.User;
import net.ddns.armen181.cafe.cafe_manager.enums.Role;

import java.util.List;

public interface UserService {

    User create(String eMail, String firstName, String lastName, String password, Role role);

    User get(String eMail);

    User get(Long id);

    List<User> getAll();

    void remove(Long id);
}
