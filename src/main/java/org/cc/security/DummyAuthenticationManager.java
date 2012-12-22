package org.cc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * daneel yaitskov
 */
public class DummyAuthenticationManager implements AuthenticationManager {

    private static final Logger logger = LoggerFactory.getLogger(DummyAuthenticationManager.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug("authenticate");
        return authentication;
    }
}
