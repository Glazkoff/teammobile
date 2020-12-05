package ru.catcherry.teammobile.users;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import ru.catcherry.teammobile.Review;

public class UsersList {
    @SerializedName("users")
    List<User> users;
}
