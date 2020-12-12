package ru.catcherry.teammobile.reviews;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.catcherry.teammobile.ApiConfiguration;
import ru.catcherry.teammobile.ApiInterface;
import ru.catcherry.teammobile.R;
import ru.catcherry.teammobile.config.Config;

public class ReviewDetailActivity extends AppCompatActivity {

    TextView fullReviewId;
    TextView fullReviewText, fullReviewRating;
    ApiInterface api;
    private CompositeDisposable disposables;
    String html;
    int id;

    public static final int DELETE = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        fullReviewId = findViewById(R.id.fullReviewId);
        fullReviewText = findViewById(R.id.fullReviewText);
        fullReviewRating = findViewById(R.id.fullReviewRating);
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        if (getIntent().getExtras() != null){
            disposables.add(
                    api.review(getIntent().getIntExtra("reviewId", 1))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    (review) -> {
                                        fullReviewId.setText("Отзыв №"+review.review_id);
                                        id = review.review_id;
                                        fullReviewText.setText("Содержание: "+review.comment);
                                        fullReviewRating.setText("Количество звёзд: "+review.rating+"/5");
                                    },
                                    (error) -> {
                                        error.printStackTrace();
                                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    }));
        }

    }

    public void deleteReview(View view) {
        Call<Void> call = api.deleteReview(id);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(@NotNull Call<Void> call, @NotNull Response<Void> response) {
                int mStatusCode = response.code();
                System.out.println(mStatusCode);
            }
            @Override
            public void onFailure(@NotNull Call<Void> call, @NotNull Throwable t) {
            }});

        System.out.println("Удалено!");

        Intent intent = getIntent();
        setResult(DELETE, intent);
        finish();
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
