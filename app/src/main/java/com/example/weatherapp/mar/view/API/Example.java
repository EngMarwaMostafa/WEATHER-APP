package com.example.weatherapp.mar.view.API;

import com.google.gson.annotations.SerializedName;

public class Example {

    @SerializedName("main")
    private Main main;


    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }
}