package com.yvkalume.learnapi.meteo;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.yvkalume.learnapi.GithubUser;
import com.yvkalume.learnapi.GithubUserService;
import com.yvkalume.learnapi.MainActivity;
import com.yvkalume.learnapi.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Query;

public class MeteoActivity extends AppCompatActivity {

    TextView tvData;
    EditText edLon;
    EditText edLat;
    EditText edUnit;
    EditText edOutput;
    EditText edtZshift;
    EditText edAcc;
    ProgressBar progressBar;


    Button buttonSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_meteo);
        initComponents();
    }

    private void initComponents() {
        tvData = findViewById(R.id.data);
        edLon = findViewById(R.id.lon);
        edLat = findViewById(R.id.lat);
        edUnit = findViewById(R.id.unit);
        edOutput = findViewById(R.id.output);
        edtZshift = findViewById(R.id.tzshift);
        progressBar = findViewById(R.id.progress);
        buttonSubmit = findViewById(R.id.submit);
        edAcc = findViewById(R.id.acc);


        buttonSubmit.setOnClickListener(v -> {
            double longitude = Double.parseDouble(edLon.getText().toString());
            double latitude = Double.parseDouble(edLat.getText().toString());
            int acc = Integer.parseInt(edAcc.getText().toString());
            String unit = edUnit.getText().toString();
            String output = edOutput.getText().toString();
            int tzshift = Integer.parseInt(edtZshift.getText().toString());


            getMeteo(longitude,latitude,acc,unit,output,tzshift);
        });


    }

    private void getMeteo(double longitude, double latitude,int acceleration,String unit, String output, int tzshift) {

        progressBar.setVisibility(View.VISIBLE);
//https://www.7timer.info/bin/astro.php?lon=113.2&lat=23.1&ac=0&unit=metric&output=json&tzshift=0
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://www.7timer.info")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MeteoService meteoService = retrofit.create(MeteoService.class);

        Call<Meteo> meteoCallback = meteoService.getMeteo(longitude, latitude, acceleration, unit, output, tzshift);

        meteoCallback.enqueue(new Callback<Meteo>() {
            @Override
            public void onResponse(Call<Meteo> call, Response<Meteo> response) {
                if (response.isSuccessful()) {

                    Meteo meteo = response.body();
                    if (meteo == null) {
                        Toast.makeText(MeteoActivity.this,"User no found",Toast.LENGTH_SHORT).show();
                    } else {
                        try {
                            tvData.setText("Temp : " + meteo.getDataSeries().get(0).temp2m + "\nTrans : " + meteo.getDataSeries().get(0).transparency );
                            tvData.setVisibility(View.VISIBLE);
                        }  catch (Exception e) {
                            Toast.makeText(MeteoActivity.this,"Error",Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(MeteoActivity.this,"Error",Toast.LENGTH_SHORT).show();
                }

                progressBar.setVisibility(View.GONE);

            }

            @Override
            public void onFailure(Call<Meteo> call, Throwable t) {
                Toast.makeText(MeteoActivity.this,t.getMessage(),Toast.LENGTH_LONG).show();
                progressBar.setVisibility(View.GONE);
            }
        });

    }
}