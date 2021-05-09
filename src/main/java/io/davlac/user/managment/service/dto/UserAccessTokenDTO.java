package io.davlac.user.managment.service.dto;

import io.davlac.user.managment.domain.Role;

public class UserAccessTokenDTO {

    private String name;

    private Role role;

    private Long expirationTime;

    private Long refreshTokenTime;

    public UserAccessTokenDTO(String name, Role role, Long expirationTime, Long refreshTokenTime) {
        this.name = name;
        this.role = role;
        this.expirationTime = expirationTime;
        this.refreshTokenTime = refreshTokenTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Long getExpirationTime() {
        return expirationTime;
    }

    public void setExpirationTime(Long expirationTime) {
        this.expirationTime = expirationTime;
    }

    public Long getRefreshTokenTime() {
        return refreshTokenTime;
    }

    public void setRefreshTokenTime(Long refreshTokenTime) {
        this.refreshTokenTime = refreshTokenTime;
    }

    @Override
    public String toString() {
        return "UserTokenDTO{" +
                "name='" + name + '\'' +
                ", role=" + role +
                ", expirationTime=" + expirationTime +
                ", refreshTokenTime=" + refreshTokenTime +
                '}';
    }
}
