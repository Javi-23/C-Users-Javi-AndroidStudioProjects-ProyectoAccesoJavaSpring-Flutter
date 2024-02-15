package com.vedruna.persistence.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
@Entity
@Table(name = "T_TWT_USERS")
public class User implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "C_TWT_USERS_ID")
    private Long id;

    @Column(name = "C_TWT_USERS_USERNAME", unique = true, nullable = false)
    @NotBlank(message = "El nombre de usuario no puede estar vacío")
    private String username;

    @Column(name = "C_TWT_USERS_MESSAGE", unique = true, nullable = false)
    @Email(message = "El formato del correo electrónico no es válido")
    private String email;

    @Column(name = "C_TWT_USERS_PASS", nullable = false)
    @NotBlank(message = "La contraseña no puede estar vacía")
    @Size(min = 6, message = "La contraseña debe tener al menos 6 caracteres")
    private String password;

    @Column(name = "C_TWT_USERS_DESCRIPTION", nullable = false)
    @NotBlank(message = "La descripción no puede estar en blanco")
    private String description;

    @CreationTimestamp
    @Column(name = "C_TWT_USERS_CREATION_DATE", nullable = false, updatable = false)
    private LocalDateTime creationDate;

    @Column(nullable = false, updatable = false)
    @OneToMany(mappedBy = "authorId", cascade = CascadeType.ALL)
    @JsonBackReference
    private List<Posts> posts;

    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String username, String email, String description, String password, Role role) {
        this.username = username;
        this.email = email;
        this.description = description;
        this.password = password;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
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