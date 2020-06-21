package com.gestion.stock.web.pojo;

import javax.validation.constraints.NotNull;

public class LoginModel {
    @NotNull
    private String login;
    @NotNull
    private String password;

    private Boolean rememberMe;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Boolean getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(Boolean rememberMe) {
        this.rememberMe = rememberMe;
    }

    @Override
    public String toString() {
        return "LoginModel{" +
                "login='" + login + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
