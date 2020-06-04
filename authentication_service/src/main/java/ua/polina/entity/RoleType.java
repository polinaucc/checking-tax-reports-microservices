package ua.polina.entity;

import org.springframework.security.core.GrantedAuthority;

public enum RoleType implements GrantedAuthority {
    CLIENT,
    INSPECTOR,
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}