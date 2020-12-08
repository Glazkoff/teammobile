package ru.catcherry.teammobile;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

public class AddReviewActivity extends AppCompatActivity {
    EditText editNumberOfRoom, editTextReview;
    RadioGroup radio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_review);
        radio = findViewById(R.id.numberOfStars);
        radio.clearCheck();
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
        author_id = 1;
        comment = editTextReview.getText().toString();
        System.out.println("Комната: " + room_id);
        System.out.println("Автор: " + author_id);
        System.out.println("Кол-во звезд: " + rating + "/5");
        System.out.println("Текст комментария: " + comment);
        editNumberOfRoom.setText("");
        editTextReview.setText("");
        editNumberOfRoom.clearFocus();
        editTextReview.clearFocus();
        radio.clearCheck();

    }


    }