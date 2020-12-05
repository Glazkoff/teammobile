package ru.catcherry.teammobile.users;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UsersList {
    @SerializedName("users")
    List<User> users;
}
