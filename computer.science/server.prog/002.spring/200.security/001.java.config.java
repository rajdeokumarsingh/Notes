
AbstractSecurityWebApplicationInitializer
    // create the context like the web.xml

        @Create
        AnnotationConfigWebApplicationContext

        @Create
        ContextLoaderListener(rootAppContext)

        @Create
        DelegatingFilterProxy(filterName);
                 
WebSecurityConfigurerAdapter.java
    // create the spring config like the spring-security.xml

    AuthenticationConfiguration
            ApplicationContext

            @Create
            AuthenticationManagerBuilder config:
                InMemoryUserDetailsManagerConfigurer
                JdbcUserDetailsManagerConfigurer
                DaoAuthenticationConfigurer
                LdapAuthenticationProviderConfigurer


            AuthenticationConfiguration
                create
                @Create
                AuthenticationManager

            maintains:
                private List<AuthenticationProvider> authenticationProviders = new ArrayList<AuthenticationProvider>();
                 

            @Create
            private AuthenticationTrustResolver trustResolver = new AuthenticationTrustResolverImpl();

            @Create
            private HttpSecurity http;
                     

            @Create
            UserDetailsService


WebSecurity

HttpSecurity
    create OpenIDLoginConfigurer
    create HeadersConfigurer
    create SessionManagementConfigurer
    create PortMapperConfigurer

    new HeadersConfigurer
    new SessionManagementConfigurer
    new PortMapperConfigurer
    new JeeConfigurer
    new X509Configurer
    new RememberMeConfigurer
    new ExpressionUrlAuthorizationConfigurer
    new RequestCacheConfigurer
    new ExceptionHandlingConfigurer
    new SecurityContextConfigurer
    new ServletApiConfigurer
    new CsrfConfigurer
    new LogoutConfigurer
    new AnonymousConfigurer
    new FormLoginConfigurer
    new ChannelSecurityConfigurer
    new HttpBasicConfigurer
    new DefaultSecurityFilterChain

