/**
 *
 */
package de.ars.skilldb.security;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.UserInteractor;

/**
 * Fügt den vollen Namen des Benutzers zu jeder Seite hinzu.
 */
public class UserPrincipalInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserInteractor users;

    @Override
    public void postHandle(final HttpServletRequest request, final HttpServletResponse response, final Object handler,
            final ModelAndView modelAndView) throws Exception {
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null || context.getAuthentication() == null || context.getAuthentication().getName() == null) {
            super.postHandle(request, response, handler, modelAndView);
            return;
        }
        User loggedInUser = users.findByUserName(context.getAuthentication().getName());
        if (loggedInUser != null && modelAndView != null) {
            modelAndView.addObject("loggedInUser", loggedInUser);
        }

        super.postHandle(request, response, handler, modelAndView);
    }

}
