package net.ddns.armen181.cafe.cafe_manager.service.impl;

import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.domain.User;
import net.ddns.armen181.cafe.cafe_manager.enums.Role;
import net.ddns.armen181.cafe.cafe_manager.repository.UserRepository;
import net.ddns.armen181.cafe.cafe_manager.service.UserService;
import net.ddns.armen181.cafe.cafe_manager.util.NotFoundException;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public User get(String eMail) {
        log.info("Get user by eMail -> {}", eMail);
        return userRepository.findByEMail(eMail).orElseThrow(() -> new NotFoundException("User is not found by eMail"));
    }

    @Override
    public User get(Long id) {
        log.info("Get user by id -> {}", id);
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not found by Id"));
    }

    @Override
    public List<User> getAll() {
        return Lists.newArrayList(userRepository.findAll());
    }

    @Override
    public void remove(Long id) {
        log.info("Try to remove user by id -> {}", id);
        User user = userRepository.findById(id).orElseThrow(() -> new NotFoundException("User Not found User by Id for remove"));
        userRepository.delete(user);
    }


}
