package ru.catcherry.teammobile;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiInterface {

    @GET("reviews/{reviewId}")
    Observable<Review> review(@Path("reviewId") int reviewId);

    @GET("reviewslist")
    Observable<ReviewsList> reviews();
}
