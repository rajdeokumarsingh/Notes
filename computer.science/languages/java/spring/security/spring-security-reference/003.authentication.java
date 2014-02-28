

1. The username and password are obtained and combined into an instance of 
    UsernamePasswordAuthenticationToken (an instance of the Authentication interface, which we saw earlier).

2. The token is passed to an instance of AuthenticationManager for validation.

3. The AuthenticationManager returns a fully populated Authentication instance on successful authentication.

4. The security context is established by calling  
    SecurityContextHolder.getContext().setAuthentication(...), passing in the returned authentication object.

Authentication
    UsernamePasswordAuthenticationToken implement Authentication



