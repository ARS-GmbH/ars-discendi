/**
 *
 */
package de.ars.skilldb.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import de.ars.skilldb.domain.User;
import de.ars.skilldb.interactor.UserInteractor;

/**
 * Wird ausgeführt, wenn die Anmeldung am System erfolgreich war.
 */
public class AuthenticationSuccessHandlerImpl extends SavedRequestAwareAuthenticationSuccessHandler implements
        AuthenticationSuccessHandler {

    @Autowired
    private UserInteractor users;

    /**
     * {@inheritDoc}
     *
     * Erstellt einen neuen Benutzerknoten in der Datenbank, wenn sich ein registrierter
     * Angestellter zum ersten mal anmeldet.
     */
    @Override
    public void onAuthenticationSuccess(final HttpServletRequest request, final HttpServletResponse response,
            final Authentication auth) throws IOException, ServletException {
        String name = auth.getName();
        User found = users.findByUserName(name);
        if (found == null) {
            User newUser = new User();
            newUser.setUserName(name);
            users.save(newUser);
        }
        super.onAuthenticationSuccess(request, response, auth);
    }

}
