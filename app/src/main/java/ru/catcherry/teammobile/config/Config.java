package ru.catcherry.teammobile.config;

import com.google.gson.annotations.SerializedName;

public class Config {

    @SerializedName("config_id")
    int config_id;

    @SerializedName("event_chance")
    double event_chance;
}
