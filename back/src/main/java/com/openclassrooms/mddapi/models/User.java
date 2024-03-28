package com.openclassrooms.mddapi.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;


@Entity
@Table(name = "USERS")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "email", unique = true)
    @NotNull(message = "Email ne peut pas être null")
    private String email;

    @Column(name = "username")
    @NotNull(message = "Nom ne peut pas être null")
    private String username;

    @Column(name = "password")
    @NotNull(message = "Mot de passe ne peut pas être null")
    private String password;

    // Un utilisateur peut avoir plusieurs posts
    @OneToMany(mappedBy = "author")
    private Set<Post> posts = new HashSet<>();

    // Un utilisateur peut avoir plusieurs commentaires
    @OneToMany(mappedBy = "author")
    private Set<Comment> comments = new HashSet<>();

    @Column(name = "created_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date created_at;

    @Column(name = "updated_at")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updated_at;
}
