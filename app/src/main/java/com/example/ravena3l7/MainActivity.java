package com.example.ravena3l7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ravena3l7.data.RetrofitBuilder;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private Object[] keys;
    private ArrayList<String>values;
    private Spinner spinnerOne;
    private Spinner spinnerTwo;
    private EditText etCurrent;
    private TextView tvResult;
    private Double one, two;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupViews();
        fetchCurrencies();
        setupListener();
        getSpinnerPosition();

    }

    private void getSpinnerPosition() {
        spinnerOne.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                one = Double.parseDouble(values.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        spinnerTwo.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                two = Double.parseDouble(values.get(position));
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });



    }

    private void setupListener(){
        etCurrent.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s != null && s.length() > 0){
                String f = String.valueOf(s);
                Double r = ((Double.parseDouble(f)/one ) * two);
                tvResult.setText(r.toString());

                }else {
                    tvResult.setText(" ");
                }
            }
        });
    }

    private void setupViews(){
        spinnerOne = findViewById(R.id.spinnerOne);
        spinnerTwo = findViewById(R.id.spinnerTwo);
        etCurrent = findViewById(R.id.etCurrent);
        tvResult = findViewById(R.id.twCurrent);

    }

    private void fetchCurrencies(){
        RetrofitBuilder.getService().getCuurencies("1fc2a9bd0a85d3520261791025761f74")
                .enqueue(new Callback<JsonObject>() {
                    @Override
                    public void onResponse(Call<JsonObject> call, Response<JsonObject> response) {
                        if(response.isSuccessful() && response.body()!= null){
                            parseJson(response.body());



                        }else {

                        }
                    }

                    @Override
                    public void onFailure(Call<JsonObject> call, Throwable t) {
                        Toast.makeText(getApplicationContext(),t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
    }
    private void parseJson(JsonObject body){
        JsonObject rates = body.getAsJsonObject("rates");
        keys = rates.keySet().toArray();
        values = new ArrayList<>();
        for (Object item:keys) {
            values.add(rates.getAsJsonPrimitive(item.toString()).toString());
        }
        setupAdapter();



    }
    private void setupAdapter(){
        ArrayAdapter arrayAdapter = new ArrayAdapter<Object>
                (getApplicationContext(), android.R.layout.simple_spinner_item, keys);
        spinnerOne.setAdapter(arrayAdapter);
        spinnerTwo.setAdapter(arrayAdapter);
    }


}
