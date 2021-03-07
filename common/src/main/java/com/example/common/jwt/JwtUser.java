package com.example.common.jwt;

import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

@Getter
@Setter
public class JwtUser extends User {

    public JwtUser(String username, String password, Collection<? extends GrantedAuthority> authorities){
        super(username, password, authorities);
    }

}
