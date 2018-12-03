package net.ddns.armen181.cafe.cafe_manager.controller;


import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CheckUserLoginController {
    // =============  Check User Role =====================
    //@PreAuthorize("hasAnyRole('USER', 'MANAGER')")
    @GetMapping("/checkRole")
    public Boolean checkUserRole() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return !securityContext.getAuthentication().getName().equals("anonymousUser");
    }

}
