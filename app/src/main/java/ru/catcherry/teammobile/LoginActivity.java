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
import android.widget.ProgressBar;
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
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        progressBar = findViewById(R.id.loginProgressBar);
        loginEditText = findViewById(R.id.loginEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        btnLogIn = findViewById(R.id.logInBtn);

        btnLogIn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                loginEditText.setVisibility(View.INVISIBLE);
                passwordEditText.setVisibility(View.INVISIBLE);
                btnLogIn.setVisibility(View.INVISIBLE);
                try {
                    onLogIn(view);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                progressBar.setVisibility(View.INVISIBLE);
                loginEditText.setVisibility(View.VISIBLE);
                passwordEditText.setVisibility(View.VISIBLE);
                btnLogIn.setVisibility(View.VISIBLE);
                loginEditText.setText("");
                passwordEditText.setText("");
                loginEditText.clearFocus();
                passwordEditText.clearFocus();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public void onLogIn(View view) throws IOException {
        String login = loginEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        if (checkAuth(login, password)) {
            Toast.makeText(LoginActivity.this, "Привет!", Toast.LENGTH_LONG).show();
            progressBar.setVisibility(View.INVISIBLE);
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            progressBar.setVisibility(View.INVISIBLE);
            Toast.makeText(LoginActivity.this, "Неправильный логин или пароль!", Toast.LENGTH_LONG).show();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private Boolean checkAuth(String login, String password) {
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
            if (response != null) {
                if (response.token != null) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            return false;
        }
    }
}