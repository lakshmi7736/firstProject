package com.Ecom.Bee.Baa.Models;

import com.Ecom.Bee.Baa.Models.TOKEN.RefreshToken;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private boolean enabled;

    @Column(name = "account_non_locked")
    private boolean accountNonLocked;

    @Column(name = "failed_attempt")
    private int failedAttempt;

    @Column(name = "lock_time")
    private LocalDateTime lockTime;

    // Set the default role as "ROLE_USER"
    @Column(nullable = false)
    private String role;

    @OneToOne(mappedBy = "user")
    @JsonIgnore
    @ToString.Exclude
    private RefreshToken refreshToken;

}
