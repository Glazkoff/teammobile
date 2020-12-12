package ru.catcherry.teammobile.config;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.jetbrains.annotations.NotNull;

import io.reactivex.disposables.CompositeDisposable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.catcherry.teammobile.ApiConfiguration;
import ru.catcherry.teammobile.ApiInterface;
import ru.catcherry.teammobile.MainActivity;
import ru.catcherry.teammobile.R;

public class EditConfigActivity extends AppCompatActivity {

    EditText eventChanceInput;
    ApiInterface api;
    private CompositeDisposable disposables;
    Double event_chance;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_config);
        eventChanceInput = findViewById(R.id.eventChanceInput);
        Bundle extras = getIntent().getExtras();
        double event_chance = extras.getDouble("event_chance");

        eventChanceInput.setText(Double.toString(event_chance));

        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
    }

    public void onSave(View view) {
        Config data = new Config(Double.parseDouble(eventChanceInput.getText().toString()));
        Call<Config> call = api.addConfig(data);
        call.enqueue(new Callback<Config>() {
            @Override
            public void onResponse(@NotNull Call<Config> call, @NotNull Response<Config> response) {
                int mStatusCode = response.code();
                Config config = response.body();
                System.out.println("Yes");
                finish();
            }
            @Override
            public void onFailure(@NotNull Call<Config> call, @NotNull Throwable t) {
                System.out.println("No");
            }});


    }
}