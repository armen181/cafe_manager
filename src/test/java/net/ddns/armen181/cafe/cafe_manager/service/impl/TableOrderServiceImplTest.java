package net.ddns.armen181.cafe.cafe_manager.service.impl;

import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;
import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.domain.User;
import net.ddns.armen181.cafe.cafe_manager.enums.Role;
import net.ddns.armen181.cafe.cafe_manager.repository.TableOrderRepository;
import net.ddns.armen181.cafe.cafe_manager.repository.UserRepository;
import net.ddns.armen181.cafe.cafe_manager.service.CafeTableService;
import net.ddns.armen181.cafe.cafe_manager.service.CaffeTableService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
@RunWith(SpringRunner.class)
@SpringBootTest
public class TableOrderServiceImplTest {

    @Autowired
    TableOrderRepository tableOrderRepository;

    @Autowired
    private CafeTableService cafeTableService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    CaffeTableService tableOrderService;
    @Before
    @Transactional
    public void setUp() throws Exception {
        User user = new User();
        user.setId(1L);
        user.setUserPassword("1234");
        user.setLastName("TableOrderServiceImplTest");
        user.setFirsName("TableOrderServiceImplTest");
        user.setEMail("TableOrderServiceImplTest");
        user.setRole(Role.WAITER);

        CafeTable cafeTable = cafeTableService.create("TestCafeTableService");
        user.addCafeTable(cafeTable);
        TableOrder tableOrder = tableOrderService.create("OrderForTest");
        cafeTable.addTableOrder(tableOrder);
        userRepository.save(user);




    }

    @Test
    @DirtiesContext
    public void create() {
        tableOrderService.create("OrderForTest_1");
        assertTrue(tableOrderRepository.findByName("OrderForTest_1").isPresent());
    }

    @Test
    public void get() {
        List<TableOrder> tableOrderByName = tableOrderService.get("TableOrderServiceImplTest");
        TableOrder tableOrder = tableOrderService.get(tableOrderByName.get(0).getId());
        assertNotNull(tableOrder.getId());
    }

    @Test
    public void get1() {
        List<TableOrder> tableOrder = tableOrderService.get("TableOrderServiceImplTest");
        assertTrue(tableOrder.size()>0);
    }

    @Test
    public void getByTableName() {
        List<TableOrder> tableOrder = tableOrderService.getByTableName("TestCafeTableService");
        assertTrue(tableOrder.size()>0);
    }

    @Test
    public void getAll() {
        List<TableOrder> tableOrder = tableOrderService.getAll();
        assertTrue(tableOrder.size()>0);
    }

    @Test
    @DirtiesContext
    public void remove() {
        tableOrderService.remove(1L);
        assertTrue(!tableOrderRepository.findById(1L).isPresent());
    }

    @Test
    public void signProductInOrder() {

    }

    @Test
    @Transactional
    public void edit() {

    }
}