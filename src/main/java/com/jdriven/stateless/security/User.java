package com.jdriven.stateless.security;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;


public class User implements UserDetails {

	public User() {
	}

	public User(String username) {
		this.username = username;
	}

	public User(String username, Date expires) {
		this.username = username;
		this.expires = expires.getTime();
	}

	private Long id;

	private String username;

	private String password;

	private long expires;

	private boolean accountExpired;

	private boolean accountLocked;

	private boolean credentialsExpired;

	private boolean accountEnabled;

	private String newPassword;

	private Set<SimpleGrantedAuthority> authorities;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	@JsonIgnore
	public String getPassword() {
		return password;
	}

	@JsonProperty
	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	public String getNewPassword() {
		return newPassword;
	}

	@JsonProperty
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}

	@Override
	@JsonIgnore
	public Set<SimpleGrantedAuthority> getAuthorities() {
		return authorities;
	}

	// Use Roles as external API
	public Set<String> getRoles() {
		Set<String> roles = new HashSet<String>();
		if (authorities != null) {
			for (SimpleGrantedAuthority authority : authorities) {
				String authorityStr = authority.getAuthority();
				authorityStr = authorityStr.substring(authorityStr.lastIndexOf("_") + 1);
				roles.add(authorityStr);
			}
		}
		return roles;
	}

	public void setRoles(Set<String> roles) {
		for (String role : roles) {
			this.grantRole(role);
		}
	}

	public void grantRole(String role) {
		if (authorities == null) {
			authorities = new HashSet<SimpleGrantedAuthority>();
		}
		authorities.add(new SimpleGrantedAuthority("ROLE_" + role));
	}

	public boolean hasRole(String role) {
		return authorities.contains(new SimpleGrantedAuthority("ROLE_" + role));
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonExpired() {
		return !accountExpired;
	}

	@Override
	@JsonIgnore
	public boolean isAccountNonLocked() {
		return !accountLocked;
	}

	@Override
	@JsonIgnore
	public boolean isCredentialsNonExpired() {
		return !credentialsExpired;
	}

	@Override
	@JsonIgnore
	public boolean isEnabled() {
		return !accountEnabled;
	}

	public long getExpires() {
		return expires;
	}

	public void setExpires(long expires) {
		this.expires = expires;
	}

	@Override
	public String toString() {
		return getClass().getSimpleName() + ": " + getUsername();
	}
}
