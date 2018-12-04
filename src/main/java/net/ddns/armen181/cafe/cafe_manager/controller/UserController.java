package net.ddns.armen181.cafe.cafe_manager.controller;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.Dto.UserDto;
import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;
import net.ddns.armen181.cafe.cafe_manager.domain.User;
import net.ddns.armen181.cafe.cafe_manager.service.CafeTableService;
import net.ddns.armen181.cafe.cafe_manager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping("/rest")
@Slf4j
public class UserController {

    private final UserService userService;
    private final CafeTableService cafeTableService;

    public UserController(UserService userService, CafeTableService cafeTableService) {
        this.userService = userService;
        this.cafeTableService = cafeTableService;
    }

    @PostMapping("/userCreat")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<UserDto> userCreat(@Valid @NonNull @RequestBody final UserDto userDto) {
        return UserDtoToUserConverter(userDto);
    }

    @GetMapping("/userGetByEMail")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<UserDto> userGetByEMail(@NonNull @RequestHeader String eMail) {
        return UserToUserDtoConverter(userService.get(eMail));
    }

    @GetMapping("/userGetById")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<UserDto> userGetById(@NonNull @RequestHeader Long id) {
        return UserToUserDtoConverter(userService.get(id));
    }

    @GetMapping("/userAddCafeTable")
    @PreAuthorize("hasAnyRole('MANAGER')")
    @Transactional
    public ResponseEntity<UserDto> userAddCafeTable(@NonNull @RequestHeader Long userId, @NonNull @RequestHeader Long tableId) {
        User user = userService.get(userId);
        CafeTable table = cafeTableService.get(tableId);
        log.info("try to sign table {} to  user {} ", tableId, userId);
        user.addCafeTable(table);
        return UserToUserDtoConverter(userService.get(userId));
    }

    @GetMapping("/userRemoveById")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity userRemove(@NonNull @RequestHeader Long id) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        userService.remove(id);
        log.info("Manager deleted User by Id, Manager -> {}, deleted user id -> {}", userName, id);
        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/userGetAll")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<List<UserDto>> userGetAll() {
        List<UserDto> userDtos = new ArrayList<>();
        userService.getAll().forEach(x -> {
            UserDto userDto = UserDtoToUserMapping(x);
            userDtos.add(userDto);
        });
        log.info("Manager Try get all Users");
        return new ResponseEntity<>(userDtos, HttpStatus.OK);
    }


    private ResponseEntity<UserDto> UserToUserDtoConverter(User user) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        log.info("User run -> {}", userName);
        UserDto userDto = UserDtoToUserMapping(user);
        log.info("User  -> {}, get -> {} ", userName, userDto.getEMail());
        return new ResponseEntity<>(userDto, HttpStatus.OK);
    }

    private ResponseEntity<UserDto> UserDtoToUserConverter(UserDto userDto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        log.info("User run -> {}", userName);
        userService.create(userDto.getEMail(), userDto.getFirsName(), userDto.getLastName(), userDto.getUserPassword(), userDto.getRole());

        User user1 = userService.get(userDto.getEMail());
        log.info("created new user by -> {}, Object -> {} ", userName, userDto);
        return UserToUserDtoConverter(user1);
    }

    private UserDto UserDtoToUserMapping(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setEMail(user.getEMail());
        userDto.setFirsName(user.getFirsName());
        userDto.setLastName(user.getLastName());
        userDto.setRole(user.getRole());
        return userDto;
    }

}
