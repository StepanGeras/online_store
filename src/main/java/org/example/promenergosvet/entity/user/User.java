package org.example.promenergosvet.entity.user;

import lombok.Data;
import jakarta.persistence.*;
import org.example.promenergosvet.converter.UserRoleSetConverter;
import org.springframework.security.core.GrantedAuthority;
import java.util.Set;


@Data
@Entity
@Table(name = "users")
public class User{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;
    private String name;
    private String password;
    private String telephone;

    @Column(unique = true, nullable = false)
    private String email;

    @Convert(converter = UserRoleSetConverter.class)
    private Set<Role> roles;

    public enum Role implements GrantedAuthority {
        ROLE_ADMIN, ROLE_USER;

        @Override
        public String getAuthority() {
            return this.name();
        }
    }
}
