package net.ddns.armen181.cafe.cafe_manager.service.impl;

import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.User;
import net.ddns.armen181.cafe.cafe_manager.enums.Role;
import net.ddns.armen181.cafe.cafe_manager.repository.UserRepository;
import net.ddns.armen181.cafe.cafe_manager.service.UserService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class UserServiceImpl implements UserService {
    //============== wiring beans ==================
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    //============== wiring beans ==================



    @Override
    public User create(String eMail, String firstName, String lastName, String password, Role role) {

        User user = new User();
        user.setEMail(eMail);
        user.setFirsName(firstName);
        user.setLastName(lastName);
        user.setUserPassword(BCrypt.hashpw(password, BCrypt.gensalt(12)));
        user.setRole(role);
        log.info("user created by eMail -> {}", eMail);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> get(String eMail) {
        log.info("Get user by eMail -> {}", eMail);
        return userRepository.findByEMail(eMail);
    }

    @Override
    public Optional<User> get(Long id) {
        log.info("Get user by id -> {}", id);
        return userRepository.findById(id);
    }

    @Override
    public void remove(Long id) {

        Optional<User> user = userRepository.findById(id);
        user.ifPresent(userRepository::delete);
        log.info("Try to remove user by id -> {}", id);
    }


}
