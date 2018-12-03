package net.ddns.armen181.cafe.cafe_manager.bootstrap;


import net.ddns.armen181.cafe.cafe_manager.domain.*;
import net.ddns.armen181.cafe.cafe_manager.enums.ProductInOrderStatus;
import net.ddns.armen181.cafe.cafe_manager.enums.Role;
import net.ddns.armen181.cafe.cafe_manager.service.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
public class BootStrap implements CommandLineRunner {


    private final ProductInOrderService productInOrderService;
    private final CaffeTableService tableOrderService;
    private final CafeTableService cafeTableService;
    private final ProductService productService;
    private final UserService userService;
    private final Environment env;

    public BootStrap(ProductInOrderService productInOrderService, CaffeTableService tableOrderService,
                     CafeTableService cafeTableService,
                     ProductService productService,
                     UserService userService,
                     Environment env) {
        this.productInOrderService = productInOrderService;
        this.tableOrderService = tableOrderService;
        this.cafeTableService = cafeTableService;
        this.productService = productService;
        this.userService = userService;
        this.env = env;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        // ==============  Creat User Manager ====================
       User user = userService.create(env.getProperty("user.admin.eMail"),
                env.getProperty("user.admin.firstName"),
                env.getProperty("user.admin.lastName"),
                env.getProperty("user.admin.password"),
                Role.MANAGER);


        // ==============  Creat simple order ====================

        CafeTable cafeTable = cafeTableService.create("Number 1");
        user.addCafeTable(cafeTable);

        TableOrder order = tableOrderService.create("First chair");

        Product product_1 = productService.create("Cola");

        ProductInOrder productInOrder_1 = productInOrderService.create(2, ProductInOrderStatus.ACTIVE);
        productInOrder_1.addProduct(product_1);

        Product product_2 = productService.create("Coffee");

        ProductInOrder productInOrder_2 = productInOrderService.create(1, ProductInOrderStatus.ACTIVE);
        productInOrder_2.addProduct(product_2);

        order.addProductInOrder(productInOrder_1);
        order.addProductInOrder(productInOrder_2);


        cafeTable.addTableOrder(order);


    }



}







