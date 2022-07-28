package com.yvkalume.learnapi;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textViewLogin;
    TextView textViewName;
    TextView textViewId;
    EditText editText;
    Button submitBtn;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initComponents();
    }

    private void initComponents() {
        textViewId = findViewById(R.id.userId);
        textViewName  = findViewById(R.id.name);
        textViewLogin = findViewById(R.id.login);
        editText = findViewById(R.id.editext);
        submitBtn = findViewById(R.id.submit);
        progressBar = findViewById(R.id.progress);

        submitBtn.setOnClickListener(v -> {
            int userId = Integer.parseInt(editText.getText().toString());
            getUser(userId);
        });

    }

    private void getUser(int id) {

        progressBar.setVisibility(View.VISIBLE);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GithubUserService userService = retrofit.create(GithubUserService.class);

        Call<GithubUser> userCallback = userService.getUser(id);

        userCallback.enqueue(new Callback<GithubUser>() {
            @Override
            public void onResponse(Call<GithubUser> call, Response<GithubUser> response) {
                if (response.isSuccessful()) {

                    GithubUser user = response.body();
                    if (user == null) {
                        Toast.makeText(MainActivity.this,"User no found",Toast.LENGTH_SHORT).show();
                    } else {
                        textViewLogin.setText("Login : " + user.getLogin());
                        textViewName.setText("Name : " + user.getName());
                        textViewId.setText("Id " + user.getId());

                    }
                } else {
                    Toast.makeText(MainActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<GithubUser> call, Throwable t) {
                Toast.makeText(MainActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}