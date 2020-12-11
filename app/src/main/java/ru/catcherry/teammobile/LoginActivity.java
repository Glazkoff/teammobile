package ru.catcherry.teammobile;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.io.IOException;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import ru.catcherry.teammobile.login.LoginRequest;
import ru.catcherry.teammobile.login.LoginResponse;
import ru.catcherry.teammobile.reviews.Review;

public class LoginActivity extends AppCompatActivity {

    EditText loginEditText, passwordEditText;
    Button btnLogIn;
    ApiInterface api;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        btnLogIn = findViewById(R.id.logInBtn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                try {
                    onLogIn(view);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onLogIn(View view) throws IOException {

//        findViewById(R.id.list).setVisibility(View.GONE);
//        findViewById(R.id.progressBar).setVisibility(View.VISIBLE);

        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();

        if (checkAuth(login, password)) {
            Toast.makeText(LoginActivity.this, "Привет!", Toast.LENGTH_LONG).show();
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            Toast.makeText(LoginActivity.this, "Неправильный логин или пароль!", Toast.LENGTH_LONG).show();
        }

//        editNumberOfRoom.setText("");
//        editTextReview.setText("");
//        editNumberOfRoom.clearFocus();
//        editTextReview.clearFocus();


    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Boolean checkAuth(String login, String password) {
        // Include REST API
        api = ApiConfiguration.getApi();
        boolean bool = false;

        CompletableFuture<LoginResponse> auth = CompletableFuture.supplyAsync(new Supplier<LoginResponse>() {
            @Override
            public LoginResponse get() {
                try {
                    LoginRequest dataAuth = new LoginRequest(login, password);
                    LoginResponse resp = api.logIn(dataAuth).execute().body();
                    return resp;
                } catch (IOException e) {
                    throw new IllegalStateException(e);
                }
            }
        });

        LoginResponse response = null;
        try {
            response = auth.get();
            System.out.println("Я НАХУЙ НЕ ВЫДЕРЖИВАЮ!: "+response.token);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }

        if (response != null) {
            return true;
        } else {
            return false;
        }
    }
}