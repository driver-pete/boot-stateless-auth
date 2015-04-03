package com.jdriven.stateless.security;

import java.security.Principal;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController {

	@RequestMapping(value = "/api/users/current", method = RequestMethod.GET)
	public User getCurrent(Principal principal) {
		/*
		 * Principal is our Authentication object from SecurityContext.
		 */
		if (principal != null) 
		{
			return (User)((Authentication) principal).getPrincipal();
		} else
		{
			return new User("anonymousUser");
		}
	}
}
