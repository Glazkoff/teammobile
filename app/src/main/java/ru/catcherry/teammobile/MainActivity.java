package ru.catcherry.teammobile;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {

    RecyclerView reviewsRecyclerView;
    ListAdapter adapter;
    List<Review> list;
    ApiInterface api;
    private CompositeDisposable disposables;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        list = new ArrayList<>();
        adapter = new ListAdapter(this, list);
        reviewsRecyclerView = findViewById(R.id.list);
        reviewsRecyclerView.setAdapter(adapter);
        reviewsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
        this.onClick(this.reviewsRecyclerView);
    }

    public void onClick(View view) {
        disposables.add(api.reviews()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe((reviewsList) -> {
                    list.clear();
                    list.addAll(reviewsList.reviews);
                    adapter.notifyDataSetChanged();
                }, (error) -> Toast.makeText(this, "При поиске возникла ошибка:\n" + error.getMessage(),
                        Toast.LENGTH_LONG).show()));
        Intent intent = new Intent(this, TabActivity.class);
        startActivity(intent);
    }
}
