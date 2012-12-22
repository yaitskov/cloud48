package org.cc.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;

/**
 * daneel yaitskov
 */
public class DummyAuthenticationProvider implements AuthenticationProvider {

    private static final Logger logger = LoggerFactory.getLogger(DummyAuthenticationProvider.class);

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        logger.debug("authenticat");
        return null;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        logger.debug("supports");
        return true;
    }
}
