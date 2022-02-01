package com.shubham.CovidHelper;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
//import com.leo.simplearcloader.SimpleArcLoader;

import org.eazegraph.lib.charts.PieChart;
import org.eazegraph.lib.models.PieModel;
import org.json.JSONException;
import org.json.JSONObject;

public class CovidCases extends AppCompatActivity {
    ProgressBar simpleArcLoader;
    ScrollView scrollView;
    PieChart pieChart;
    TextView tvCases , tvRecovered , tvActive , tvCritical ;
    TextView tvTodayCases , tvTotalDeaths, tvTodayDeaths , tvAffectedCountries;
    protected void onCreate(@Nullable Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.covid_cases);

        tvCases = findViewById(R.id.tvCases);
        tvRecovered = findViewById(R.id.tvRecovered);
        tvCritical = findViewById(R.id.tvCritical);
        tvActive = findViewById(R.id.tvActive);
        tvTodayCases = findViewById(R.id.tvTodayCases);
        tvTotalDeaths = findViewById(R.id.tvTotalDeaths);
        tvTodayDeaths = findViewById(R.id.tvTodayDeaths);
        tvAffectedCountries = findViewById(R.id.tvAffectedCountries);

        simpleArcLoader = findViewById(R.id.loader);
        scrollView = findViewById(R.id.scrollStats);
        pieChart = findViewById(R.id.piechart);

        getSupportActionBar().setTitle("World Covid Cases");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);


        fetchData();

    }
     private void fetchData()
     {
         String url  = "https://corona.lmao.ninja/v3/covid-19/all/";

         simpleArcLoader.setVisibility(View.VISIBLE);

         StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
             @Override
             public void onResponse(String response) {
                 try {
                     JSONObject jsonObject = new JSONObject(response.toString());
                     tvCases.setText(jsonObject.getString("cases"));
                     tvRecovered.setText(jsonObject.getString("recovered"));
                     tvCritical.setText(jsonObject.getString("critical"));
                     tvActive.setText(jsonObject.getString("active"));
                     tvTodayCases.setText(jsonObject.getString("todayCases"));
                     tvTotalDeaths.setText(jsonObject.getString("deaths"));
                     tvTodayDeaths.setText(jsonObject.getString("todayDeaths"));
                     tvAffectedCountries.setText(jsonObject.getString("affectedCountries"));


                     pieChart.addPieSlice(new PieModel("Cases", Integer.parseInt(tvCases.getText().toString()) , Color.parseColor("#FFA726")));
                     pieChart.addPieSlice(new PieModel("Recoverd",Integer.parseInt(tvRecovered.getText().toString()), Color.parseColor("#66BB6A")));
                     pieChart.addPieSlice(new PieModel("Deaths",Integer.parseInt(tvTotalDeaths.getText().toString()), Color.parseColor("#EF5350")));
                     pieChart.addPieSlice(new PieModel("Active",Integer.parseInt(tvActive.getText().toString()), Color.parseColor("#29B6F6")));
                     pieChart.startAnimation();

                     simpleArcLoader.setVisibility(View.GONE);
                     scrollView.setVisibility(View.VISIBLE);

                 } catch (JSONException e) {

                     simpleArcLoader.setVisibility(View.GONE);
                     scrollView.setVisibility(View.VISIBLE);
                     e.printStackTrace();
                 }

             }

         },new Response.ErrorListener(){
             @Override
                public void onErrorResponse(VolleyError error){
                 Toast.makeText(getApplicationContext(),error.getMessage(), Toast.LENGTH_SHORT).show();
         }
         });

         RequestQueue requestQueue = Volley.newRequestQueue(this);
         requestQueue.add(request);
     }
    public void goTrackCountries(View view) {

        startActivity(new Intent(getApplicationContext(),AffectedCountries.class));

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();

        return super.onOptionsItemSelected(item);
    }

}
