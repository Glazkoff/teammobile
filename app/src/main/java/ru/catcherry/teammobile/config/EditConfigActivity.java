package ru.catcherry.teammobile.config;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_config);
        eventChanceInput = findViewById(R.id.eventChanceInput);
        Bundle extras = getIntent().getExtras();
        Double event_chance = extras.getDouble("event_chance");

        eventChanceInput.setText(event_chance.toString());

        api = ApiConfiguration.getApi();
        disposables = new CompositeDisposable();
    }

    public void onSave(View view) {
        Config data = new Config(Double.valueOf(eventChanceInput.getText().toString()));
        Call<Config> call = api.addConfig(data);
        call.enqueue(new Callback<Config>() {
            @Override
            public void onResponse(Call<Config> call, Response<Config> response) {
                int mStatusCode = response.code();
                Config config = response.body();
                System.out.println("Yes");
                turnBack();
            }
            @Override
            public void onFailure(Call<Config> call, Throwable t) {
                System.out.println("No");
            }});


    }

    public void turnBack() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}