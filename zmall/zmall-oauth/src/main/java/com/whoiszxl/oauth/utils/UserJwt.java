package com.whoiszxl.oauth.utils;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;


@Data
public class UserJwt extends User {
    private String id;    //用户ID
    private String name;  //用户名字

    public UserJwt(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }
}
