package com.shubham.CovidHelper;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void covidCases(View view) {
        Intent intent = new Intent(getApplicationContext(),CovidCases.class);
        startActivity(intent);
    }

    public void vaccineAvailability(View view) {
        Intent intent = new Intent(getApplicationContext(),CovidVaccines.class);
        startActivity(intent);
    }


}