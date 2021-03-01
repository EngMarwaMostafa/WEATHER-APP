package com.example.weatherapp.mar.view.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.weatherapp.R;
import com.example.weatherapp.mar.data.api.ApiClient;
import com.example.weatherapp.mar.data.api.ApiService;
import com.example.weatherapp.mar.data.model.Example;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class HomeFragment extends Fragment {

  private ImageView location;
  private View view;
  private TextView tempText,dateText,timeText,maxText,minText,windText;
  private EditText locat;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_home, container, false);


       tempText = view.findViewById(R.id.fragment_home_tv_temp);
        dateText = view.findViewById(R.id.fragment_home_tv_date);
        timeText = view.findViewById(R.id.fragment_home_tv_time);
        maxText = view.findViewById(R.id.fragment_home_tv_max);
        minText = view.findViewById(R.id.fragment_home_tv_min);
        windText = view.findViewById(R.id.fragment_home_tv_wind);
        location=view.findViewById(R.id.fragment_home_iv_loc);
        locat=view.findViewById(R.id.fragment_home_edt_locat);

        locat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            { getCurrentWeatherData(locat.getText().toString().trim());

            }

            private void getCurrentWeatherData(String name) {
                ApiService apiService = ApiClient.getClient().create(ApiService.class);
                Call<Example> call = apiService.getCurrentWeatherData(name);

                call.enqueue(new Callback<Example>() {
                    @Override
                    public void onResponse(Call<Example> call, Response<Example> response) {
                    tempText.setText("Temp"+" "+response.body().getMain().getTemp()+" C");
                    maxText.setText("Temp max"+" "+response.body().getMain().getTempMax());
                    minText.setText("Temp min"+" "+response.body().getMain().getTempMin());
                    windText.setText("Pressure"+" "+response.body().getMain().getPressure());

                    }

                    @Override
                    public void onFailure(Call<Example> call, Throwable t) {

                    }
                });
            }
        });

                return view;

            }}
