core class:

    AbstractSecurityInterceptor
        is extended by FilterSecurityInterceptor


        include: FilterInvocationSecurityMetadataSource

            SecurityMetadataSource

        protected InterceptorStatusToken beforeInvocation(Object object) {
            Authentication authenticated = authenticateIfRequired();
            // Attempt authorization
            try {

                // object : FilterInvocation: URL: /api/foos
                // attributes: authenticated
                this.accessDecisionManager.decide(authenticated, object, attributes);
                    AffirmativeBased.decide(authenticated, object, attributes) {

                    }

            }
            catch (AccessDeniedException accessDeniedException) {
                publishEvent(new AuthorizationFailureEvent(object, attributes, authenticated, accessDeniedException));

                throw accessDeniedException;
            }

        }
 
    AccessDecisionManager
        // Makes a final access control (authorization) decision.
        // delegate work to a list of AccessDecisionVoter

        /**
         * Resolves an access control decision for the passed parameters.
         *
         * @param authentication the caller invoking the method (not null)
         * @param object the secured object being called
         * @param configAttributes the configuration attributes associated with the secured object being invoked
         *
         * @throws AccessDeniedException if access is denied as the authentication does not hold a required authority or
         *         ACL privilege
         * @throws InsufficientAuthenticationException if access is denied as the authentication does not provide a
         *         sufficient level of trust
         */
        void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
            throws AccessDeniedException, InsufficientAuthenticationException;

        AccessDecisionManager
                |
                V
            AbstractAccessDecisionManager
                    | was extent by:
                    |
                    |->  1. AffirmativeBased: if one voter votes yes, then pass
                    |->  2. ConsensusBased: majority-rule
                    |->  3. UnanimousBased: 一票否决
        
    AccessDecisionVoter
        /**
         * Indicates a class is responsible for voting on authorization decisions.
         * <p>
         * The coordination of voting (ie polling {@code AccessDecisionVoter}s,
         * tallying their responses, and making the final authorization decision) is
         * performed by an {@link org.springframework.security.access.AccessDecisionManager}.
         *
         * @author Ben Alex
         */

public class PreInvocationAuthorizationAdviceVoter implements AccessDecisionVoter<MethodInvocation> {

RoleVoter implements AccessDecisionVoter<Object> {
AuthenticatedVoter implements AccessDecisionVoter<Object> {



Jsr250Voter implements AccessDecisionVoter<Object> {
AbstractAclVoter implements AccessDecisionVoter<MethodInvocation> {
DenyAgainVoter implements AccessDecisionVoter<Object> {
MockStringOnlyVoter implements AccessDecisionVoter<Object> {
DenyVoter implements AccessDecisionVoter<Object> {
WebExpressionVoter implements AccessDecisionVoter<FilterInvocation> {
TestVoter implements AccessDecisionVoter {

element class
    FilterInvocation
        /**
         * Holds objects associated with a HTTP filter.<P>Guarantees the request and response are instances of
         * <code>HttpServletRequest</code> and <code>HttpServletResponse</code>, and that there are no <code>null</code>
         * objects.
         * <p>
         * Required so that security system classes can obtain access to the filter environment, as well as the request
         * and response.
         */

    ConfigAttribute
        WebExpressionConfigAttribute
            include a member "Expression"
