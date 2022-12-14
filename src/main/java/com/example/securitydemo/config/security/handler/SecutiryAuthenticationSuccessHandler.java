package com.example.securitydemo.config.security.handler;

import com.example.securitydemo.config.security.roles.Role;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Collection;

@Component
public class SecutiryAuthenticationSuccessHandler implements AuthenticationSuccessHandler {

    private final Log logger = LogFactory.getLog(this.getClass());

    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public SecutiryAuthenticationSuccessHandler() {
        super();
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    protected void handle(final HttpServletRequest request, final HttpServletResponse response, final Authentication authentication) throws IOException {
        final String targetUrl = determineTargetUrl(authentication);

        if (response.isCommitted()) {
            logger.debug("Response has already been committed. Unable to redirect to " + targetUrl);
            return;
        }

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    protected String determineTargetUrl(final Authentication authentication) {

        boolean isAdmin, isDeveloper;
        String resultPage;
        final Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        isAdmin    = authorities.stream().filter(au -> au.getAuthority().equalsIgnoreCase(Role.ROLE_ADMIN.name())).findAny().isPresent();
        isDeveloper  = authorities.stream().filter(au -> au.getAuthority().equalsIgnoreCase(Role.ROLE_DEVELOPER.name())).findAny().isPresent();

        if(isAdmin) {
            resultPage = "/admin";
        } else if (isDeveloper) {
            resultPage = "/developer";
        } else {
            resultPage = "/login";
        }

        return resultPage;
    }

    protected final void clearAuthenticationAttributes(final HttpServletRequest request) {
        final HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }
}
