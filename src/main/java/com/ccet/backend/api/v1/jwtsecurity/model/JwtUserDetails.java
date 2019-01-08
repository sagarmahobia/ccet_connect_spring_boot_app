package com.ccet.backend.api.v1.jwtsecurity.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class JwtUserDetails implements UserDetails {


    private int id;
    private Collection<GrantedAuthority> authorities;


    public JwtUserDetails(JwtUser jwtUser) {

        this.id = jwtUser.getId();
        authorities = AuthorityUtils.commaSeparatedStringToAuthorityList(jwtUser.getRole());
    }

    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return authorities;
    }


    @Override
    public String getPassword() {
        return null;
    }

    @Override
    public String getUsername() {
        return null;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

}
