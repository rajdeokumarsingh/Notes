package com.pekall.mdm.auth.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;

import java.util.Collection;

/**
 * Wrapper class of User, include mdm user id
 */
public class MdmUser extends User {

    private String mdmUserId;

    public MdmUser(String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
    }

    public MdmUser(String id, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        super(username, password, authorities);
        mdmUserId = id;
    }

    public MdmUser(String id, User user) {
        super(user.getUsername(), user.getPassword(), user.getAuthorities());
        mdmUserId = id;
    }

    public String getMdmUserId() {
        return mdmUserId;
    }
}
