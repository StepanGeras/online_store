package org.example.promenergosvet.entity.user;

import lombok.Data;
import jakarta.persistence.*;
import org.example.promenergosvet.converter.RoleSetConverter;
import org.springframework.security.core.GrantedAuthority;

import java.util.Set;


@Data
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String password;
    private String telephone;

    @Convert(converter = RoleSetConverter.class)
    private Set<Role> roles;

    public enum Role implements GrantedAuthority {
        ROLE_ADMIN, ROLE_USER;

        @Override
        public String getAuthority() {
            return this.name();
        }
    }
}
