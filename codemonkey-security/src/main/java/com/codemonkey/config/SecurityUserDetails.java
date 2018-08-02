package com.codemonkey.config;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.codemonkey.domain.AppRole;
import com.codemonkey.domain.AppUser;
import com.codemonkey.utils.SysUtils;

public class SecurityUserDetails extends AppUser implements UserDetails {

    private static final long serialVersionUID = 1L;

    public SecurityUserDetails(AppUser user) {

        if(user!=null) {
            this.setUsername(user.getUsername());
            this.setPassword(user.getPassword());
            this.setRoles(user.getRoles());
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {

        List<GrantedAuthority> authorityList = new ArrayList<>();
        Set<AppRole> roles = this.getRoles();
        if(SysUtils.isNotEmpty(roles)){
            for (AppRole role : roles) {
                authorityList.add(new SimpleGrantedAuthority(role.getName()));
            }
        }
        return authorityList;
    }

	/**
     * 账户是否过期
     * @return
     */
    @Override
    public boolean isAccountNonExpired() {

        return true;
    }

    /**
     * 是否禁用
     * @return
     */
    @Override
    public boolean isAccountNonLocked() {

        return true;
    }

    /**
     * 密码是否过期
     * @return
     */
    @Override
    public boolean isCredentialsNonExpired() {

        return true;
    }

    /**
     * 是否启用
     * @return
     */
    @Override
    public boolean isEnabled() {

        return true;
    }
}