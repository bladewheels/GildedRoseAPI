package com.miw.homework.gildedrose.expanded.security.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

import static java.util.Objects.requireNonNull;

/**
 * An implementation of {@UserDetails} for users of this API.
 */
@Value
@Builder
public class User implements UserDetails {
    private static final long serialVersionUID = 1L;

    String id;

    /**
     * @implNote The following field meets the requirements of Spring's UserDetails
     * but the caller MUST supply an email parameter
     *   e.g. when POSTing to /public/users/register do send: email=a.b@c.de
     */
    String username;

    /**
     * @implNote The following field meets the requirements of Spring's UserDetails
     * but is not actually used i.e. only the id is used by the secured API(s),
     * in the Authorization HTTP Header.
     */
    String password;

    @JsonCreator
    User(@JsonProperty("id") final String id,
         @JsonProperty("email") final String username,
         @JsonProperty("password") final String password) {
        super();
        this.id = requireNonNull(id);
        this.username = requireNonNull(username);
        this.password = password;
    }

    @JsonIgnore
    @Override
    public Collection<GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    @JsonIgnore
    @Override
    public String getPassword() {
        return password;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

