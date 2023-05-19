package com.api.pipeline.token;

import lombok.Builder;

@Builder
public record TokenRequestBody(String grant_type, String username, String password){
    public String toUrlFormEncoded() {
        return "grant_type=" + grant_type +  "&" +
                "username=" + username + "&" +
                "password=" + password;
    }
}
