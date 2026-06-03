package com.logitrack.auth.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "usuarios")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private String nome;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Role   role;

    public Usuario() {}

    public Usuario(String email, String senha, String nome, Role role) {
        this.email = email;
        this.senha = senha;
        this.nome  = nome;
        this.role  = role;
    }

    public Long   getId()    { return id; }
    public String getEmail() { return email; }
    public String getSenha() { return senha; }
    public String getNome()  { return nome; }
    public Role   getRole()  { return role; }

    public void setSenha(String senha) { this.senha = senha; }
}
