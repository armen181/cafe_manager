package net.ddns.armen181.cafe.cafe_manager.service;


import net.ddns.armen181.cafe.cafe_manager.domain.User;
import net.ddns.armen181.cafe.cafe_manager.enums.Role;

import java.util.List;
import java.util.Optional;

public interface UserService {

    User create(String eMail, String firstName, String lastName, String password, Role role);
    Optional<User> get(String eMail);
    Optional<User> get(Long id);
    List<User> getAll();
    void remove(Long id);
}
