
package org.robbins.flashcards.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.inject.Inject;

import org.robbins.flashcards.dto.UserDto;
import org.robbins.flashcards.facade.UserFacade;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * A custom {@link UserDetailsService} where user information is retrieved from a JPA
 * repository.
 */
@Service
@Transactional(readOnly = true)
public class CustomUserDetailsService implements UserDetailsService {

    @Inject
    private UserFacade userFacade;

    /**
     * Returns a populated {@link UserDetails} object. The username is first retrieved
     * from the database and then mapped to a {@link UserDetails} object.
     */
    @Override
    public UserDetails loadUserByUsername(String username) {
        try {
            UserDto domainUser = userFacade.findUserByOpenid(username);

            boolean enabled = true;
            boolean accountNonExpired = true;
            boolean credentialsNonExpired = true;
            boolean accountNonLocked = true;

            return new org.springframework.security.core.userdetails.User(
                    domainUser.getOpenid(), "apiuserpassword", enabled,
                    accountNonExpired, credentialsNonExpired, accountNonLocked,
                    getAuthorities());

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Retrieves a collection of {@link GrantedAuthority} based on a numerical role.
     *
     * @return a collection of {@link GrantedAuthority

     */
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authList = getGrantedAuthorities(getRoles());
        return authList;
    }

    /**
     * Converts a numerical role to an equivalent list of roles.
     *
     * @return list of roles as as a list of {@link String}
     */
    public List<String> getRoles() {
        List<String> roles = new ArrayList<String>();

        roles.add("ROLE_USER");

        return roles;
    }

    /**
     * Wraps {@link String} roles to {@link SimpleGrantedAuthority} objects.
     *
     * @param roles {@link String} of roles
     * @return list of granted authorities
     */
    public static List<GrantedAuthority> getGrantedAuthorities(List<String> roles) {
        List<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();
        for (String role : roles) {
            authorities.add(new SimpleGrantedAuthority(role));
        }
        return authorities;
    }
}