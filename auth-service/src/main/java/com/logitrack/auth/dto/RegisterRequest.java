package com.logitrack.auth.dto;

import com.logitrack.auth.entity.Role;

public record RegisterRequest(String nome, String email, String senha, Role role) {}
