package com.ecom.userservice;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;

@RestController
@RequestMapping("/users")
public class UserController {

    private final Map<Long, UserDto> users = Map.of(
            1L, new UserDto(1L, "Amine Jamali", "amine@email.com", 1),
            2L, new UserDto(2L, "Sara Benani", "sara@email.com", 1),
            3L, new UserDto(3L, "Youssef Idrissi", "youssef@email.com", 0)
    );

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable Long id) {
        UserDto user = users.get(id);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Utilisateur introuvable");
        }
        return user;
    }
}