package org.cc.security;

import org.cc.ent.UserSession;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * daneel yaitskov
 */
public class CloudAuthenticationToken extends AbstractAuthenticationToken {

    private UserSession session;

    /**
     * Creates a token with the supplied array of authorities.
     *
     * @param authorities the collection of <tt>GrantedAuthority</tt>s for the
     *                    principal represented by this authentication object.
     */
    public CloudAuthenticationToken(UserSession session,
                                    Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.session = session;
    }

    /**
     * Returns hivext session.
     *
     * @return hivext session
     */
    @Override
    public Object getCredentials() {
        return session.getKey();
    }

    @Override
    public Object getPrincipal() {
        return session.getOwner();
    }
}
