package com.moreira.dscommerce.projections;

public interface UserDetailsProjection {

    String getUsername();
    String getPassword();
    Long getRoleID();
    String getAuthority();
}
