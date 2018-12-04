package net.ddns.armen181.cafe.cafe_manager.controller;


import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import net.ddns.armen181.cafe.cafe_manager.Dto.UserDto;
import net.ddns.armen181.cafe.cafe_manager.domain.CafeTable;
import net.ddns.armen181.cafe.cafe_manager.domain.TableOrder;
import net.ddns.armen181.cafe.cafe_manager.domain.User;
import net.ddns.armen181.cafe.cafe_manager.service.CafeTableService;
import net.ddns.armen181.cafe.cafe_manager.service.CaffeTableService;
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
import java.util.Optional;


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
    public ResponseEntity<UserDto> userAddCafeTable(@NonNull @RequestHeader Long userId,@NonNull @RequestHeader Long tableId) {
            Optional<User> user = userService.get(userId);
        if (user.isPresent()) {
             Optional<CafeTable> table = cafeTableService.get(tableId);
            if (table.isPresent()) {
                log.info("try to sign table {} to  user {} ", tableId, userId);
                user.get().addCafeTable(table.get());

            }

        }
        return UserToUserDtoConverter(userService.get(userId));
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

    @GetMapping("/userGetAll")
    @PreAuthorize("hasAnyRole('MANAGER')")
    public ResponseEntity<List<UserDto>> userGetAll(){
        List<UserDto> userDtos = new ArrayList<>();
        userService.getAll().forEach(x->{
            UserDto userDto = new UserDto();
            userDto.setId(x.getId());
            userDto.setEMail(x.getEMail());
            userDto.setFirsName(x.getFirsName());
            userDto.setLastName(x.getLastName());
            userDto.setRole(x.getRole());
            userDtos.add(userDto);
        });
        log.info("Manager Try get all Users");
        return new ResponseEntity<>(userDtos,HttpStatus.OK);
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
         return new ResponseEntity<>(userDto, HttpStatus.OK);
     }
     log.info("Optional<User> wasn't preset ,  user -> {}", userName);
     return new ResponseEntity<>(new UserDto(), HttpStatus.BAD_REQUEST);
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
        return new ResponseEntity<>(userDto, HttpStatus.BAD_REQUEST);
    }

}
