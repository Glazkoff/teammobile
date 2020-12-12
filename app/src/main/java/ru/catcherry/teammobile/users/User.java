package ru.catcherry.teammobile.users;

import com.google.gson.annotations.SerializedName;

import java.util.Date;

public class User {

    @SerializedName("user_id")
    int user_id;

    @SerializedName("login")
    String login;

    @SerializedName("name")
    String name;

    @SerializedName("createdAt")
    Date createdAt;

}
