SecurityContextHolder-->SecurityContext-->Authentication--->{ principal, password, roles }
UserDetailsService-->{UserDetails...}

userDetailsService deriving:
    userDetailsManager

Authentication deriving:
    AbstractAuthenticationToken
    UsernamePasswordAuthenticationToken

SecurityContextHolder
    SecurityContextHolderStrategy
        interface
        wrap a SecurityContext
        provide get/set method

        implemented by 
            ThreadLocalSecurityContextHolderStrategy
            InheritableThreadLocalSecurityContextHolderStrategy
            GlobalSecurityContextHolderStrategy

        // get current user
        {
            Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

            if (principal instanceof UserDetails) {
                String username = ((UserDetails)principal).getUsername();
            } else {
                String username = principal.toString();
            }
        }


SecurityContext
    interface
    wrap a Authentication
    provide get/set method

Authentication
    extends Principal

    include 
        a list of GrantedAuthority
            which just a string
        a principal object
            for example username

        a credentials object
            for example password

UserDetails
    a list of GrantedAuthority
    a username
    a password

UserDetailsService
    provide interface:
        UserDetails loadUserByUsername(String username) throws UsernameNotFoundException;

1.2.4. Summary
    SecurityContextHolder, 
        to provide access to the SecurityContext.
    SecurityContext, 
        to hold the Authentication and possibly request-specific security information.
    Authentication, 
        to represent the principal in a Spring Security-specific manner.
    GrantedAuthority, 
        to reflect the application-wide permissions granted to a principal.
    UserDetails, 
        to provide the necessary information to build an Authentication object from your application’s DAOs or other source of security data.
    UserDetailsService, 
        to create a UserDetails when passed in a String-based username (or certificate ID or the like).

Example:
    // create user bu UserDetailsManager

    @Repository
    public class DefaultCalendarService implements CalendarService {
        @Autowired
        public DefaultCalendarService(EventDao eventDao, CalendarUserDao userDao, UserDetailsManager userDetailsManager) {
            ...
            this.eventDao = eventDao;
            this.userDao = userDao;
            this.userDetailsManager = userDetailsManager;
        }

        public int createUser(CalendarUser user) {
            List<GrantedAuthority> authorities = AuthorityUtils.createAuthorityList("ROLE_USER");
            UserDetails userDetails = new User(user.getEmail(), user.getPassword(), authorities);
            userDetailsManager.createUser(userDetails);
            return userDao.createUser(user);
        }
    }

    // login current user
    @Override
    public void setCurrentUser(CalendarUser user) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(user.getEmail());
        UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                user.getPassword(),userDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(authentication);
    }
