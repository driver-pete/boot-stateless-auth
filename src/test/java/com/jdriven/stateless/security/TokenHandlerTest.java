package com.jdriven.stateless.security;

import static javax.xml.bind.DatatypeConverter.printBase64Binary;
import static org.junit.Assert.*;

import java.security.SecureRandom;
import java.util.Date;

import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class TokenHandlerTest {

	private TokenHandler tokenHandler;

	@Before
	public void init() {
		byte[] secret = new byte[70];
		new SecureRandom().nextBytes(secret);
		tokenHandler = new TokenHandler(secret);
	}

	@Test
	public void testRoundTrip_ProperData() {
		final UserWithExpiration userWithExpiration = new UserWithExpiration("Robbert", new Date(new Date().getTime() + 10000));
		userWithExpiration.grantRole("ADMIN");

		final UserWithExpiration parsedUser = tokenHandler.parseUserFromToken(tokenHandler.createTokenForUser(userWithExpiration));

		assertEquals(userWithExpiration.getUsername(), parsedUser.getUsername());
		assertTrue(parsedUser.hasRole("ADMIN"));
	}

	@Test
	public void testCreateToken_SeparatorCharInUsername() {
		final UserWithExpiration userWithExpiration = new UserWithExpiration("R.bbert", new Date(new Date().getTime() + 10000));

		final UserWithExpiration parsedUser = tokenHandler.parseUserFromToken(tokenHandler.createTokenForUser(userWithExpiration));

		assertEquals(userWithExpiration.getUsername(), parsedUser.getUsername());
	}

	@Test
	public void testCreateToken_ExcludePasswords() {
		final UserWithExpiration userWithExpiration = new UserWithExpiration("Robbert", new Date(new Date().getTime() + 10000));
		userWithExpiration.setPassword("abc");
		userWithExpiration.setNewPassword("def");

		final UserWithExpiration parsedUser = tokenHandler.parseUserFromToken(tokenHandler.createTokenForUser(userWithExpiration));

		assertEquals(userWithExpiration.getUsername(), parsedUser.getUsername());
		assertNull(parsedUser.getPassword());
		assertNull(parsedUser.getNewPassword());
	}

	@Test
	public void testParseInvalidTokens_NoParseExceptions() throws JsonProcessingException {
		final String unsignedToken = printBase64Binary(new ObjectMapper().writeValueAsBytes(new UserWithExpiration("test")));

		testForNullResult("");
		testForNullResult(unsignedToken);
		testForNullResult(unsignedToken + ".");
		testForNullResult(unsignedToken + "." + unsignedToken);
	}

	private void testForNullResult(final String token) {
		final UserWithExpiration result = tokenHandler.parseUserFromToken(token);
		assertNull(result);
	}
}
