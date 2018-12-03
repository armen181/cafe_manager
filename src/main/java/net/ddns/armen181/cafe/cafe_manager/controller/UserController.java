package net.ddns.armen181.cafe.cafe_manager.controller;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.Dto.UserDto;
import net.ddns.armen181.cafe.cafe_manager.domain.User;
import net.ddns.armen181.cafe.cafe_manager.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;


@RestController
@RequestMapping("/rest")
@Slf4j
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/userCreat")
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

    @GetMapping("/userRemoveById")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity userRemove(@NonNull @RequestHeader Long id){
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        userService.remove(id);
        log.info("Manager deleted User by Id, Manager -> {}, deleted user id -> {}",userName, id );
        return new ResponseEntity(HttpStatus.OK);
    }


    private ResponseEntity<UserDto> UserToUserDtoConverter(Optional<User> user) {
     SecurityContext securityContext = SecurityContextHolder.getContext();
     String userName = securityContext.getAuthentication().getName();
     log.info("User run -> {}", userName);
     if (user.isPresent()) {
         UserDto userDto = new UserDto();
         userDto.setId(user.get().getId());
         userDto.setEMail(user.get().getEMail());
         userDto.setFirsName(user.get().getFirsName());
         userDto.setLastName(user.get().getLastName());
         userDto.setRole(user.get().getRole());
         log.info("User  -> {}, get -> {} ", userName, userDto);
         return new ResponseEntity<UserDto>(userDto, HttpStatus.OK);
     }
     log.info("Optional<User> wasn't preset ,  user -> {}", userName);
     return new ResponseEntity<UserDto>(new UserDto(), HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<UserDto> UserDtoToUserConverter(UserDto userDto) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        String userName = securityContext.getAuthentication().getName();
        log.info("User run -> {}", userName);
        User user = userService.create(userDto.getEMail(), userDto.getFirsName(),userDto.getLastName(),userDto.getUserPassword(),userDto.getRole());

        Optional<User> optionalUser = userService.get(userDto.getEMail());
        if (optionalUser.isPresent()) {
            log.info("created new user by -> {}, Object -> {} ", userName, userDto);
            return UserToUserDtoConverter(optionalUser);

        }
        log.info("Error creating new user by -> {}, Object -> {} ", userName, userDto);
        return new ResponseEntity<UserDto>(userDto, HttpStatus.BAD_REQUEST);
    }

}