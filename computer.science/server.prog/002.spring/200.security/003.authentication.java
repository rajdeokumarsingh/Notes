

1. The username and password are obtained and combined into an instance of 
    UsernamePasswordAuthenticationToken (an instance of the Authentication interface, which we saw earlier).

2. The token is passed to an instance of AuthenticationManager for validation.

3. The AuthenticationManager returns a fully populated Authentication instance on successful authentication.

4. The security context is established by calling  
    SecurityContextHolder.getContext().setAuthentication(...), passing in the returned authentication object.

Authentication
    UsernamePasswordAuthenticationToken implement Authentication

    DaoAuthenticationProvider



AbstractAuthenticationProcessingFilter 
    deriving--> 
        UsernamePasswordAuthenticationFilter
            include -->
                ProviderManager
                    AbstractUserDetailsAuthenticationProvider
                        DaoAuthenticationProvider

    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
        throws IOException, ServletException {

        Authentication authResult;
        try {
            authResult = attemptAuthentication(request, response);
            if (authResult == null) {
                // return immediately as subclass has indicated that it hasn't completed authentication
                return;
            }
            sessionStrategy.onAuthentication(authResult, request, response);
        } catch(InternalAuthenticationServiceException failed) {
            logger.error("An internal error occurred while trying to authenticate the user.", failed);
            unsuccessfulAuthentication(request, response, failed);

            return;
        }
        catch (AuthenticationException failed) {
            // Authentication failed
            unsuccessfulAuthentication(request, response, failed);

            return;
        }

        // Authentication success
        if (continueChainBeforeSuccessfulAuthentication) {
            chain.doFilter(request, response);
        }

        successfulAuthentication(request, response, chain, authResult);
    }

}
