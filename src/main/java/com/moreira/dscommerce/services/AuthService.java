package com.moreira.dscommerce.services;

import com.moreira.dscommerce.entities.User;
import com.moreira.dscommerce.services.exceptions.ForbbidenException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    UserService userService;

    public void validateSelfOrAdmin(Long id) {
        User user = userService.authenticated();

        if (user.hasRole("ROLE_ADMIN")) {
            return;
        }
        if (!user.getId().equals(id)) {
            throw new ForbbidenException("Access denied");
        }
    }
}
