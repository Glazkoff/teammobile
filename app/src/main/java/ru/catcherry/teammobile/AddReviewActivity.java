package ru.catcherry.teammobile;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.auth0.android.jwt.Claim;
import com.auth0.android.jwt.JWT;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.catcherry.teammobile.reviews.Review;
import ru.catcherry.teammobile.reviews.ReviewDetailActivity;

public class AddReviewActivity extends AppCompatActivity {
    EditText editNumberOfRoom, editTextReview;
    RadioGroup radio;
    ApiInterface api;
    JWT jwt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review);
        radio = findViewById(R.id.numberOfStars);
        radio.clearCheck();
        api = ApiConfiguration.getApi();
        jwt = (JWT) getIntent().getParcelableExtra("jwt");
    }

    public void OnAddReview(View view){
        radio = findViewById(R.id.numberOfStars);
        int rating = -1;
        switch (radio.getCheckedRadioButtonId()){
            case  R.id.fiveStars:
                rating = 5;
                break;
            case  R.id.fourStars:
                rating = 4;
                break;
            case  R.id.threeStars:
                rating = 3;
                break;
            case  R.id.twoStars:
                rating = 2;
                break;
            case  R.id.oneStar:
                rating = 1;
                break;
            case  R.id.zeroStar:
                rating = 0;
                break;
        }
        int room_id, author_id;
        String comment;
        editTextReview = findViewById(R.id.textReview);
        editNumberOfRoom = findViewById(R.id.numberOfRoom);
        if (editNumberOfRoom.getText().toString().equals(""))
            room_id = 1;
            else
            room_id = Integer.parseInt(editNumberOfRoom.getText().toString());

        Claim subscriptionMetaData = jwt.getClaim("id");
        String user_id = subscriptionMetaData.asString();
        author_id = Integer.parseInt(user_id);
        comment = editTextReview.getText().toString();

        System.out.println("Комната: " + room_id);
        System.out.println("Автор: " + author_id);
        System.out.println("Кол-во звезд: " + rating + "/5");
        System.out.println("Текст комментария: " + comment);

        Review data = new Review(author_id, room_id, rating, comment);
        Call<Review> call = api.addReview(data);
        call.enqueue(new Callback<Review>() {
            @Override
            public void onResponse(Call<Review> call, Response<Review> response) {
                int mStatusCode = response.code();
                Review review = response.body();
                Log.d("AddReviewActivity","onResponse " + mStatusCode + ". ID new review: " + review.review_id);
            }

            @Override
            public void onFailure(Call<Review> call, Throwable t) {
                Log.d("AddReviewActivity","onFailure" + t);

            }});

        editNumberOfRoom.setText("");
        editTextReview.setText("");
        editNumberOfRoom.clearFocus();
        editTextReview.clearFocus();
        radio.clearCheck();
        Intent intent = getIntent();
        setResult(MainActivity.RESULT_OK, intent);
        finish();
    }


    }