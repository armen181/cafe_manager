package net.ddns.armen181.cafe.cafe_manager.controller;


import net.ddns.armen181.cafe.cafe_manager.enums.Role;
import net.ddns.armen181.cafe.cafe_manager.service.UserService;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckUserLoginController {

    private final UserService userService;

    public CheckUserLoginController(UserService userService) {
        this.userService = userService;
    }

    // =============  Check User Role =====================
    @GetMapping("/checkRole")
    public Boolean checkUserRole() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return !securityContext.getAuthentication().getName().equals("anonymousUser");
    }

    @GetMapping("/isAdmin")
    public Boolean isAdmin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return userService.get(securityContext.getAuthentication().getName()).getRole() == Role.MANAGER;
    }

}
