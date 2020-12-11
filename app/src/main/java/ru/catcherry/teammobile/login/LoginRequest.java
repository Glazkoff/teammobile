package ru.catcherry.teammobile.login;

import com.google.gson.annotations.SerializedName;

public class LoginRequest {
    @SerializedName("login")
    String login;

    @SerializedName("password")
    String password;

    public LoginRequest(String login, String password) {
        this.login = login;
        this.password = password;
    }
}
