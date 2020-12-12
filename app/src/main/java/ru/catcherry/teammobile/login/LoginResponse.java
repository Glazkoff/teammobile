package ru.catcherry.teammobile.login;

import com.google.gson.annotations.SerializedName;

public class LoginResponse {

    @SerializedName("token")
    public String token;

    public LoginResponse(String token) {
        this.token = token;
    }
}
