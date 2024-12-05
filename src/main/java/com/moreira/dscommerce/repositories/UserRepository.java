package com.moreira.dscommerce.repositories;

import com.moreira.dscommerce.entities.User;
import com.moreira.dscommerce.projections.UserDetailsProjection;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(nativeQuery = true, value = """
            SELECT user.email AS username, user.password, role.id AS roleId, role.authority
            FROM tb_user AS user
            INNER JOIN tb_user_role ON user.id = tb_user_role.user_id
            INNER JOIN tb_role AS role ON tb_user_role.role_id = role.id
            WHERE user.email = :email
            """)
    List<UserDetailsProjection> searchUserAndRolesByEmail(String email);
}
