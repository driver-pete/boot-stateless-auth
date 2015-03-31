package com.jdriven.stateless.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AccountStatusUserDetailsChecker;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
//import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {

	@Autowired
	private UserRepository userRepo;

	@Override
	public final User loadUserByUsername(String username) throws UsernameNotFoundException {
		//User user = new User(username);
		//username.equals("admin") ? UserRole.ADMIN : UserRole.USER
        //user.grantRole(username.equals("admin") ? UserRole.valueOf("ROLE_ADMIN"): UserRole.valueOf("ROLE_USER"));
        
		final User user = userRepo.findByUsername(username);
		if (user == null) {
			throw new UsernameNotFoundException("user not found");
		}
		return user;
	}
}


//@Service
//public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
//
////	@Autowired
////	private UserRepository userRepo;
////
////	private final AccountStatusUserDetailsChecker detailsChecker = new AccountStatusUserDetailsChecker();
//
//	@Override
//	public final User loadUserByUsername(String username) throws UsernameNotFoundException {
////		final List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>() {
////			private static final long serialVersionUID = 1L;
////
////		{
////            this.add(new SimpleGrantedAuthority());
////        }};
////        
//        User user = new User(username);
//        
//        user.grantRole(UserRole.valueOf("ROLE_ADMIN"));
//        //UserRole.valueOf(authority)
//        
//        return new User(username);
//        
////		final User user = userRepo.findByUsername(username);
////		if (user == null) {
////			throw new UsernameNotFoundException("user not found");
////		}
//		//detailsChecker.check(user);
////		return user;
//	}
//}

