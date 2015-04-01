package com.jdriven.stateless.security;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public enum UserRole {
	USER, ADMIN;

	public SimpleGrantedAuthority asAuthorityFor(final User user) {
		return new SimpleGrantedAuthority("ROLE_" + toString());
	}

	public static UserRole valueOf(final SimpleGrantedAuthority authority) {
		switch (authority.getAuthority()) {
		case "ROLE_USER":
			return USER;
		case "ROLE_ADMIN":
			return ADMIN;
		}
		throw new IllegalArgumentException("No role defined for authority: " + authority.getAuthority());
	}
}
