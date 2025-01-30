package com.moreira.dscommerce.tests;

import com.moreira.dscommerce.entities.Role;
import com.moreira.dscommerce.entities.User;

import java.time.LocalDate;

public class UserFactory {

    public static User createClientUser() {
        User user = new User(1L, "Maria", "maria@gmail.com", "988888888", LocalDate.parse("2001-07-25"), "$2a$10$kNlXYm5TR3O.gJVC0MoeQOAyq.87jA0Qj4T/tyLdUIX4t6PjUIUiq");
        user.addRole(new Role(1L, "ROLE_CLIENT"));
        return user;
    }

    public static User createAdminUser() {
        User user = new User(2L, "Alex", "alex@gmail.com", "977777777", LocalDate.parse("1987-12-13"), "$2a$10$kNlXYm5TR3O.gJVC0MoeQOAyq.87jA0Qj4T/tyLdUIX4t6PjUIUiq");
        user.addRole(new Role(2L, "ROLE_ADMIN"));
        return user;
    }

    public static User createCustomClientUser(Long id, String username) {
        User user = new User(id, "", username, "977777777", LocalDate.parse("2001-07-25"), "$2a$10$kNlXYm5TR3O.gJVC0MoeQOAyq.87jA0Qj4T/tyLdUIX4t6PjUIUiq");
        user.addRole(new Role(1L, "ROLE_CLIENT"));
        return user;
    }

    public static User createCustomAdminUser(Long id, String username) {
        User user = new User(id, "", username, "977777777", LocalDate.parse("1987-12-13"), "$2a$10$kNlXYm5TR3O.gJVC0MoeQOAyq.87jA0Qj4T/tyLdUIX4t6PjUIUiq");
        user.addRole(new Role(2L, "ROLE_ADMIN"));
        return user;
    }


}
