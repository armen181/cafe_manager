package net.ddns.armen181.cafe.cafe_manager.domain;

import net.ddns.armen181.cafe.cafe_manager.enums.Role;
import net.ddns.armen181.cafe.cafe_manager.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
//@DataJpaTest
@SpringBootTest
public class UserTest {

    @Autowired
    private UserRepository userRepository;

    @Before
    public void setUp() throws Exception {
        if( !userRepository.findByEMail("Armen.181@gmail.com").isPresent()) {
            User user = new User();
            user.setId(1L);
            user.setUserPassword("1234");
            user.setLastName("Hovhannisyan");
            user.setFirsName("Armen");
            user.setEMail("Armen.181@gmail.com");
            user.setRole(Role.WAITER);
            System.out.println(userRepository.save(user));
        }
    }

    @Test
    public void getPassword() {
        Optional<User> user = userRepository.findByEMail("Armen.181@gmail.com");
        assertTrue(user.isPresent());
        user.ifPresent(user1 -> assertEquals(user1.getPassword(), "1234"));
    }

    @Test
    @DirtiesContext
    public void getUsername() {
        Optional<User> user = userRepository.findByEMail("Armen.181@gmail.com");
        assertTrue(user.isPresent());
        user.ifPresent(user1 -> assertEquals(user1.getUsername(), "Armen.181@gmail.com"));
    }

    @Test
    @DirtiesContext
    public void getId() {
        Optional<User> user = userRepository.findByEMail("Armen.181@gmail.com");
        assertTrue(user.isPresent());
        user.ifPresent(user1 -> assertEquals(1L, (long) user1.getId()));


    }

    @Test
    public void getEMail() {
        Optional<User> user = userRepository.findByEMail("Armen.181@gmail.com");
        assertTrue(user.isPresent());
        user.ifPresent(user1 -> assertEquals(user1.getEMail(), "Armen.181@gmail.com"));
    }

    @Test
    public void getFirsName() {
        Optional<User> user = userRepository.findByEMail("Armen.181@gmail.com");
        assertTrue(user.isPresent());
        user.ifPresent(user1 -> assertEquals(user1.getFirsName(), "Armen"));
    }

    @Test
    public void getLastName() {
        Optional<User> user = userRepository.findByEMail("Armen.181@gmail.com");
        assertTrue(user.isPresent());
        user.ifPresent(user1 -> assertEquals(user1.getFirsName(), "Armen"));
    }

    @Test
    public void getUserPassword() {
        Optional<User> user = userRepository.findByEMail("Armen.181@gmail.com");
        assertTrue(user.isPresent());
        user.ifPresent(user1 -> assertEquals(user1.getUserPassword(), "1234"));
    }

    @Test
    public void getRole() {
        Optional<User> user = userRepository.findByEMail("Armen.181@gmail.com");
        assertTrue(user.isPresent());
        user.ifPresent(user1 -> assertEquals(user1.getRole(), Role.WAITER));
    }
}