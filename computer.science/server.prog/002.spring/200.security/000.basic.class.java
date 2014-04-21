
/* Simple strategy to match an <tt>HttpServletRequest</tt>. */
public interface RequestMatcher 
    /** Decides whether the rule implemented by the strategy matches the supplied request.  */
    boolean matches(HttpServletRequest request);

    implements by
        AntPathRequestMatcher
            // filter http request by its method and its url
