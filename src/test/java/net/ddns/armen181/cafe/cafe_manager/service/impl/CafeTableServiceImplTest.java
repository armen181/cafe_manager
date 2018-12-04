package net.ddns.armen181.cafe.cafe_manager.service.impl;

import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;
import net.ddns.armen181.cafe.cafe_manager.domain.User;
import net.ddns.armen181.cafe.cafe_manager.enums.Role;
import net.ddns.armen181.cafe.cafe_manager.repository.CafeTableRepository;
import net.ddns.armen181.cafe.cafe_manager.repository.UserRepository;
import net.ddns.armen181.cafe.cafe_manager.service.CafeTableService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CafeTableServiceImplTest {
    @Autowired
   private  CafeTableService cafeTableService;
    @Autowired
   private CafeTableRepository cafeTableRepository;
    @Autowired
    private UserRepository userRepository;

    @Before
    @Transactional
    public void setUp() throws Exception {

      User user = new User();
      user.setId(1L);
      user.setUserPassword("1234");
      user.setLastName("CafeTableServiceImplTest");
      user.setFirsName("CafeTableServiceImplTest");
      user.setEMail("CafeTableServiceImplTest");
      user.setRole(Role.WAITER);
     CafeTable cafeTable = cafeTableService.create("TestCafeTableService");
     user.addCafeTable(cafeTable);
     userRepository.save(user);

    }

    @Test
    @DirtiesContext
    public void create() {
     cafeTableService.create("TestCafeTableService_1");
     assertTrue(cafeTableRepository.findByName("TestCafeTableService_1").isPresent());
    }

    @Test
    public void getAll() {
     List<CafeTable> list = cafeTableService.getAll();
     assertTrue(list.size()>0);
    }

    @Test
    public void getAll1() {
     List<CafeTable> set = cafeTableService.getAll("CafeTableServiceImplTest");
     assertTrue(set.size()>0);
    }

    @Test
    public void get() {

     CafeTable cafeTable = cafeTableService.get(cafeTableService.getAll().get(0).getId());
        assertNotNull(cafeTable.getId());
    }

    @Test
    public void remove() {
     cafeTableService.remove(1L);
     Optional<CafeTable> set = cafeTableRepository.findById(1L);
     assertTrue(!set.isPresent());

    }

    @Test
    public void signTableOrder() {


    }
}