package com.punksoft.weblab1.user;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.CreationTimestamp;

import java.util.*;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "users")
@Entity
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "id_label", nullable = true, length = 32)
    private String idLabel;

    @NotNull
    @ColumnDefault("'Dicksy'")
    @Column(name = "name", nullable = false, length = 32)
    private String name;

    @ColumnDefault("0.0")
    @Column(name = "gold_balance", nullable = false)
    private Double goldBalance = 0.0;

    @ColumnDefault("0.0")
    @Column(name = "coin_balance", nullable = false)
    private Double coinBalance = 0.0;

    @Column(name = "avatar_path", nullable = true, length = 255)
    private String avatarPath;

    @CreationTimestamp
    @Column(name = "registration_date", nullable = true)
    private Date registrationDate;

    @NotBlank
    @Column(name = "username", nullable = false, unique = true, length = 32)
    private String username;

    @NotBlank
    @Column(name = "password", nullable = false)
    private String password;

    @NotBlank
    @Column(name = "roles_csv", nullable = false)
    private String rolesCsv;

    @Column(name = "is_banned", nullable = false)
    private boolean isBanned = false;

    public List<String> getRoles() {
        if (rolesCsv.isBlank()) {
            return new ArrayList<>();
        }

        return Arrays.stream(rolesCsv.split(",")).toList();
    }

    public void setRoles(List<String> roles) {
        if (roles.isEmpty()) {
            rolesCsv = "";
            return;
        }

        var csv = "";
        for (int i = 0; i < roles.size(); i++) {
            csv += roles.get(i);
            if (i < roles.size() - 1) {
                csv += ",";
            }
        }

        rolesCsv = csv;
    }
}