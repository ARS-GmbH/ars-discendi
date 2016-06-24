/**
 *
 */
package de.ars.skilldb.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.ldap.userdetails.DefaultLdapAuthoritiesPopulator;
import org.springframework.security.ldap.userdetails.LdapAuthoritiesPopulator;

/**
 * Eigener Rollen-Zuweiser für Spring Security.
 */
public class ArsLdapAuthoritiesPopulator implements LdapAuthoritiesPopulator {

    private DefaultLdapAuthoritiesPopulator delegatePopulator;

    @Override
    public Collection<? extends GrantedAuthority> getGrantedAuthorities(final DirContextOperations userData,
            final String username) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        SimpleGrantedAuthority certmanager = new SimpleGrantedAuthority("ROLE_CERTMANAGER");
        SimpleGrantedAuthority administrator = new SimpleGrantedAuthority("ROLE_ADMIN");
        authorities.add(administrator);
        authorities.add(certmanager);
        return delegatePopulator.getGrantedAuthorities(userData, username);
    }

    public DefaultLdapAuthoritiesPopulator getDelegatePopulator() {
        return delegatePopulator;
    }

    public void setDelegatePopulator(final DefaultLdapAuthoritiesPopulator delegatePopulator) {
        this.delegatePopulator = delegatePopulator;
    }

}
