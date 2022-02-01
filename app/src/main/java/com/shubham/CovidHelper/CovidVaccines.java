package com.shubham.CovidHelper;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.shubham.CovidHelper.adapter.VaccinationInfoAdapter;
import com.shubham.CovidHelper.model.VaccineModel;
import com.shubham.CovidHelper.view.PickDates;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class CovidVaccines extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {
    String baseURL = "https://cdn-api.co-vin.in/api/v2/appointment/sessions/public/calendarByPin?pincode=";
    private EditText areaPINcode;
    private Button forwardbtn;
    ProgressBar progressBar;
    private ArrayList<VaccineModel> vaccination_centers;
    private RecyclerView resultRecylerView;
    String avldate;
    protected void onCreate(@Nullable Bundle saveInstanceState)
    {
        super.onCreate(saveInstanceState);

        getSupportActionBar().setTitle("Vaccination Centers");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        // If I want a full screen
        // requestWindowFeature(Window.FEATURE_NO_TITLE);
        // getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        setContentView(R.layout.cvoid_vaccine);
        mapViews();
        onClickSetup();
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId()==android.R.id.home)
            finish();
        return super.onOptionsItemSelected(item);
    }

    private void onClickSetup() {
        forwardbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogFragment dp = new PickDates();
                dp.show(getSupportFragmentManager(),"Pick A Date");

            }
        });
    }

    private  void mapViews()
    {
        forwardbtn = (Button) findViewById(R.id.getResult);
        progressBar = (ProgressBar)findViewById(R.id.vaccineLoader);
        areaPINcode = findViewById(R.id.enterPincode);
        resultRecylerView = findViewById(R.id.recyclerSearch);
        resultRecylerView.setHasFixedSize(true);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(CovidVaccines.this, LinearLayoutManager.VERTICAL, false);
        resultRecylerView.setLayoutManager(layoutManager);
        vaccination_centers = new ArrayList<VaccineModel>();

    }
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar k = Calendar.getInstance();
        k.set(Calendar.YEAR,year);
        k.set(Calendar.MONTH,month);
        k.set(Calendar.DAY_OF_MONTH,dayOfMonth);

        SimpleDateFormat dataFormat = new SimpleDateFormat("dd-MM-YYYY");
        dataFormat.setTimeZone(k.getTimeZone());
        String d = dataFormat.format(k.getTime());
        setup(d);

    }

    private void setup(String d) {
        avldate = d;
        fetchDataNow();
    }

    private  void fetchDataNow()
    {
        vaccination_centers.clear();
        String areaPIN = areaPINcode.getText().toString();
        String url_api = baseURL+areaPIN+"&date="+avldate;
        progressBar.setVisibility(View.VISIBLE);

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url_api,null,
                new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray sessionArray = response.getJSONArray("centers");
                    for(int i = 0 ; i < sessionArray.length() ; i++)
                    {
                        JSONObject sesObject = sessionArray.getJSONObject(i);
                        VaccineModel vaccineModel = new VaccineModel();
                        vaccineModel.setVaccineCenter(sesObject.getString("name"));
                        vaccineModel.setVaccineCenterAddress(sesObject.getString("address"));
                        vaccineModel.setVaccinationTimings(sesObject.getString("from"));
                        vaccineModel.setVaccineCenterTime(sesObject.getString("to"));
                        vaccineModel.setVaccinationCharges(sesObject.getString("fee_type"));
                        JSONObject sessionObj = sesObject.getJSONArray("sessions").getJSONObject(0);
                        vaccineModel.setVaccineName(sessionObj.getString("vaccine"));
                        vaccineModel.setVaccinationAge(sessionObj.getString("min_age_limit"));
                        vaccineModel.setVaccineAvailable(sessionObj.getString("available_capacity"));
                        vaccination_centers.add(vaccineModel);

                    }
                    if(vaccination_centers.size()==0){
                        Toast.makeText(getApplicationContext(), "Cannot Found Data", Toast.LENGTH_SHORT).show();
                    }
                    VaccinationInfoAdapter vaccinationInfoAdapter = new VaccinationInfoAdapter(getApplicationContext(),vaccination_centers);
                    resultRecylerView.setAdapter(vaccinationInfoAdapter);
                     progressBar.setVisibility(View.GONE);


                } catch (JSONException e) {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(getApplicationContext(), "Cannot Connect to Server", Toast.LENGTH_SHORT).show();
                }

            }

        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError error){
                Toast.makeText(getApplicationContext(),"Cannot Connect to Internet", Toast.LENGTH_SHORT).show();
            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(request);
    }
}
