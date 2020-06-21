package com.gestion.stock.web.pojo;

import com.fasterxml.jackson.annotation.JsonProperty;

public class JWTToken {
    private String idToken;
    private Long expiresDate;

    public JWTToken(String idToken, Long expiresDate) {
        this.idToken = idToken;
        this.expiresDate = expiresDate;
    }

    @JsonProperty("id_token")
    public String getIdToken() {
        return idToken;
    }

    public void setIdToken(String idToken) {
        this.idToken = idToken;
    }

    @JsonProperty("expires_at")
    public Long getExpiresDate() {
        return expiresDate;
    }

    public void setExpiresDate(Long expiresDate) {
        this.expiresDate = expiresDate;
    }
}
