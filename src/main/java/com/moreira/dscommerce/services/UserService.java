package com.moreira.dscommerce.services;

import com.moreira.dscommerce.dto.UserDTO;
import com.moreira.dscommerce.entities.Role;
import com.moreira.dscommerce.entities.User;
import com.moreira.dscommerce.projections.UserDetailsProjection;
import com.moreira.dscommerce.repositories.UserRepository;
import com.moreira.dscommerce.util.CustomUserUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CustomUserUtil customUserUtil;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        List<UserDetailsProjection> result = userRepository.searchUserAndRolesByEmail(username);

        if (result.isEmpty())
            throw new UsernameNotFoundException("User not found");

        User user = new User();
        user.setEmail(username);
        user.setPassword(result.getFirst().getPassword());

        for (UserDetailsProjection proj : result) {
            user.addRole(new Role(proj.getRoleID(), proj.getAuthority()));
        }

        return user;
    }

    protected User authenticated() {
        try {
            String username = customUserUtil.getLoggedUsername();
            return userRepository.findByEmail(username).get();
        } catch (Exception e) {
            throw new UsernameNotFoundException("User not found");
        }
    }

    @Transactional(readOnly = true)
    public UserDTO getMe() {
        User user = authenticated();
        return new UserDTO(user);
    }
}
