package com.TMS.Auth_Service.models.dtos.responses;

import java.util.List;

public class UserResponse {
    private String username;
    private String password;
    private String email;
    private List<String> roles;
    private Long id;

    public UserResponse(String username, String password, String email, List<String> roles, Long id) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.roles = roles;
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
