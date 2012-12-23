package org.cc.util;

import org.cc.ent.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Access to spring security context.
 * Daneel Yaitskov
 */
public class SecurityUtil {

    /**
     * Returns user who send current HTTP request.
     * @return user who send current HTTP request
     * @throws IllegalStateException
     */
    public static User getCurrent() {
        SecurityContext context = SecurityContextHolder.getContext();
        Authentication auth = context.getAuthentication();
        if (auth == null) {
            throw new IllegalStateException("authentication null");
        }
        Object principal = auth.getPrincipal();

        if (principal instanceof User) {
            return (User) principal;
        }
        throw new IllegalStateException("principal is not User");
    }
}
