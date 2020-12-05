package ru.catcherry.teammobile.config;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ConfigsList {
    @SerializedName("configs")
    List<Config> configs;
}
