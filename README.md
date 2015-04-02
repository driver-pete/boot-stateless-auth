boot-stateless-auth
===================

Code for the article:
http://blog.jdriven.com/2014/10/stateless-spring-security-part-2-stateless-authentication/

Needs Gradle 2 and JDK 7

build with `gradle build`  
run with `gradle run`

===================

Some basics of Spring security.
Based on
http://docs.spring.io/autorepo/docs/spring-security/4.0.0.CI-SNAPSHOT/reference/htmlsingle/#technical-overview

Typical web application authentication process:
1. You visit the home page, and click on a link.
2. A request goes to the server, and the server decides that you’ve asked for a protected resource.
3. As you’re not presently authenticated, the server sends back a response indicating that you must authenticate. The response will either be an HTTP response code, or a redirect to a particular web page.
4. Depending on the authentication mechanism, your browser will either redirect to the specific web page so that you can fill out the form, or the browser will somehow retrieve your identity (via a BASIC authentication dialogue box, a cookie, a X.509 certificate etc.).
5. The browser will send back a response to the server. This will either be an HTTP POST containing the contents of the form that you filled out, or an HTTP header containing your authentication details.
6. Next the server will decide whether or not the presented credentials are valid. If they’re valid, the next step will happen. If they’re invalid, usually your browser will be asked to try again (so you return to step two above).
7. The original request that you made to cause the authentication process will be retried. Hopefully you’ve authenticated with sufficient granted authorities to access the protected resource. If you have sufficient access, the request will be successful. Otherwise, you’ll receive back an HTTP error code 403, which means "forbidden".
8. Next request is usually doesn't require authentication because the state is stored between requests


Basic objects of authentication:
SecurityContextHolder - holds current SecurityContext for current thread of execution (current request)
SecurityContext - holds current Authentication for this request
Authentication - holds UserDetails ("principal"), List<GrantedAuthority> (authorities==roles) and credentials
GrantedAuthority - holds string name of authority (or role) e.g. "ROLE_USER"
UserDetails - username, pass, authorities, enabled flags plus application specific user data

More specifically,
Step 2 == Authorization:
 -You access protected resource without authentication
 - AbstractSecurityInterceptor throws exception
 - ExceptionTranslationFilter catches the exception and launches AuthenticationEntryPoint
Step 3.
 - AuthenticationEntryPoint collects data from the user necessary to login
Step 6 == Authentication
 - Performs authentication below
Step 8.
 - SecurityContextPersistenceFilter restores SecurityContext from HttpSession
 - successful authorization below
 
 
Basic flow of authentication:
1. Credentials are obtained (e.g. username and pass)
2. Request of type Authentication is constructed. E.g.:
     Authentication request = new UsernamePasswordAuthenticationToken(name, password);
3. Request is passed to AuthenticationManager which constructs resulting Authentication
	(e.g. add authorities from database based on username):
     Authentication result = authManager.authenticate(request);
     Default implementation of AuthenticationManager is ProviderManager.
     ProviderManager calls a list of AuthenticationProviders.
     Default implementation of AuthenticationProvider is DaoAuthenticationProvider.
     DaoAuthenticaionProvider calls UserDetailsService.
     UserDetailsService returns UserDetails on loadUserByUsername(String username).
     DaoAuthenticaionProvider checks passwords from request Authentication and UserDetails and
     populates resulting Authentication
4. Resulting Authentication is passed to SecurityContextHolder:
     SecurityContextHolder.getContext().setAuthentication(result);
5. Web request is passed further and secured resources check the Authentication from the context
     and allow or restrict access



Basic flow of authorization:
1. Each secured object has associated AbstractSecurityInterceptor
2. AbstractSecurityInterceptor takes attributes of the secured object
3. AbstractSecurityInterceptor takes Authentication from SecurityContext
4. AbstractSecurityInterceptor asks AccessDecisionManager to decide whether is ok to proceed
5. Secured object is accessed
6. AfterInvocationManager is called



