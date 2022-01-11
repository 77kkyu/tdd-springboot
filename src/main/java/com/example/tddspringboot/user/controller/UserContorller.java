package com.example.tddspringboot.user.controller;

import com.example.tddspringboot.user.domain.User;
import com.example.tddspringboot.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Log4j2
@RestController
@RequiredArgsConstructor
public class UserContorller {

    private final UserService userService;

    @PostMapping("/user/{name}")
    private User add(@PathVariable String name) {
        log.info("name : " + name);
        User user = User.builder()
                .name(name)
                .build();
        User saveUser = userService.add(user);
        return saveUser;
    }

    @GetMapping("/user/{id}")
    private User getOne(@PathVariable Long id) {
        return userService.getOne(id);
    }

    @ExceptionHandler
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void userNotFoundHandler(Exception e) {
        log.info(e);
    }

}
