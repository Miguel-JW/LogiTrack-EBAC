package com.logitrack.user.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "perfis")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long   id;
    private String email;
    private String nome;
    private String telefone;
    private String endereco;

    public Perfil() {}

    public Perfil(String email, String nome, String telefone, String endereco) {
        this.email    = email;
        this.nome     = nome;
        this.telefone = telefone;
        this.endereco = endereco;
    }

    public Long   getId()       { return id; }
    public String getEmail()    { return email; }
    public String getNome()     { return nome; }
    public String getTelefone() { return telefone; }
    public String getEndereco() { return endereco; }

    public void setNome(String nome)         { this.nome     = nome; }
    public void setTelefone(String telefone) { this.telefone = telefone; }
    public void setEndereco(String endereco) { this.endereco = endereco; }
}
