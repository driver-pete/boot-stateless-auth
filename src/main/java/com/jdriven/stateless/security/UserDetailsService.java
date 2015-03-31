package com.jdriven.stateless.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Override
	public final User loadUserByUsername(String username) throws UsernameNotFoundException { 
        User user = new User();
		user.setUsername(username);
		String password = username;
		user.setPassword(new BCryptPasswordEncoder().encode(password));
		user.grantRole(username.equals("admin") ? UserRole.ADMIN : UserRole.USER);
		return user;
	}
}
