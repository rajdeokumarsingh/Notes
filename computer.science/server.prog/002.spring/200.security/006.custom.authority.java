
core authority components:

    FilterSecurityInterceptor

        include:
        interface AccessDecisionManager

            void decide(Authentication authentication, Object object, Collection<ConfigAttribute> configAttributes)
                throws AccessDeniedException, InsufficientAuthenticationException;

            boolean supports(ConfigAttribute attribute);

            implements by:
            AbstractAccessDecisionManager
                AffirmativeBased
                    If any voter grants access, access is immediately granted, regardless of previous denials.
                ConsensusBased
                    The majority vote (grant or deny) governs the decision of the AccessDecisionManager. 
                    Tie-breaking and handling of empty votes (containing only abstentions) is configurable.
                UnanimousBased
                    All voters must grant access, otherwise access is denied.


            include:
            interface AccessDecisionVoter

                implements by:
                RoleVoter
                AuthenticatedVoter
                WebExpressionVoter
                    include:
                    SecurityExpressionOperations
                        SecurityExpressionRoot


