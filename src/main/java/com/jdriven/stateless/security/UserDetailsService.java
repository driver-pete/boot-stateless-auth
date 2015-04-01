package com.jdriven.stateless.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Override
	public final User loadUserByUsername(String username) throws UsernameNotFoundException { 
		
//		final SimpleGrantedAuthority authority = username.equals("admin") ? new SimpleGrantedAuthority("ROLE_ADMIN") : new SimpleGrantedAuthority("ROLE_USER");
//		final List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>() {
//			private static final long serialVersionUID = 1L;
//
//		{
//            this.add(authority);
//        }};
//        
//        //UserDAO.INSTANCE.getAuthoritiesByUser(username);
//        //User u=new User(username,password,);
//        
//        return new User(username, new BCryptPasswordEncoder().encode(username), true, true, true, true, authorities);
//        
        User user = new User();
		user.setUsername(username);
		user.setPassword(new BCryptPasswordEncoder().encode(username));
		user.grantRole(username.equals("admin") ? UserRole.ADMIN : UserRole.USER);
		return user;
	}
}
