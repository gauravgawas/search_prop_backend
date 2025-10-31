package com.gaurav.searchProp.model;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;

import lombok.NoArgsConstructor;
import jakarta.persistence.*;

@Entity

@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String username;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;
}
