package com.garden.admin.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.garden.guardian.entity.Guardian;
import com.garden.token.entity.Token;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Admin implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(unique = true, name = "full_name")
    private String fullName;
    private String email;
    private String password;

    @OneToMany(mappedBy = "admin")
    @JsonIgnore
    private Set<Guardian> guardians = new HashSet<>();

    @OneToMany(mappedBy = "admin")
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Set<Token> tokens;

    public Admin(String fullName, String email, String password) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.guardians = new HashSet<>();
        this.tokens = new HashSet<>();
    }

    public void setGuardians(Guardian guardian) {
        if(this.guardians == null) this.guardians = new HashSet<>();
        this.guardians.add(guardian);
    }

    public void setToken(Token token) {
        if(this.tokens == null || this.tokens.isEmpty()) {
            this.tokens = new HashSet<>();
        }
        this.tokens.add(token);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_ADMIN");
        return List.of(authority);
    }

    @Override
    public String getUsername() {
        return getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
