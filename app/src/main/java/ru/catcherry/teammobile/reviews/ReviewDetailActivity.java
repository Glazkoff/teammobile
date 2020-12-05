package ru.catcherry.teammobile.reviews;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import ru.catcherry.teammobile.ApiConfiguration;
import ru.catcherry.teammobile.ApiInterface;
import ru.catcherry.teammobile.R;

public class ReviewDetailActivity extends AppCompatActivity {

    TextView fullReviewId;
    TextView fullReviewText;
    ImageView fullReviewImg;
    ApiInterface api;
    private CompositeDisposable disposables;
    String html;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review_detail);
        fullReviewId = findViewById(R.id.fullReviewId);
        fullReviewText = findViewById(R.id.fullReviewText);
        fullReviewImg = findViewById(R.id.fullReviewImg);
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
                                        fullReviewText.setText("Содержание: "+review.comment);
                                    },
                                    (error) -> {
                                        error.printStackTrace();
                                        Toast.makeText(this, error.getMessage(), Toast.LENGTH_LONG).show();
                                    }));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (disposables.isDisposed()) {
            disposables.dispose();
        }
    }
}
