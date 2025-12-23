package com.TMS.Auth_Service.models.dtos.requests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.Set;

public record UserRequest(
       @NotBlank String username,
       @NotBlank String password,
       @Email String email,
       @NotEmpty Set<String> roles
) {}
