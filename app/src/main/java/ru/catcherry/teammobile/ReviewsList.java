package ru.catcherry.teammobile;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReviewsList {

    @SerializedName("reviews")
    List<Review> reviews;
}
