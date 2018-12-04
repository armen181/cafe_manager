package net.ddns.armen181.cafe.cafe_manager.service.impl;

import net.ddns.armen181.cafe.cafe_manager.enums.Role;
import net.ddns.armen181.cafe.cafe_manager.repository.UserRepository;
import net.ddns.armen181.cafe.cafe_manager.service.UserService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;


    @Before
    public void setUp() throws Exception {
    }


    @Test
    @DirtiesContext
    public void create() {
        userService.create("UserServiceImplTest","UserServiceImplTest","UserServiceImplTest","123456", Role.WAITER);
        assertTrue(userRepository.findByEMail("UserServiceImplTest").isPresent());
    }

}