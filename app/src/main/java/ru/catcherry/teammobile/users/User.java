package ru.catcherry.teammobile.users;

import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("user_id")
    int user_id;

    @SerializedName("login")
    String login;

    @SerializedName("name")
    String name;

    @SerializedName("createdAt")
    String createdAt;

}
