package com.example.weatherapp.mar.view.API;

import com.google.gson.annotations.SerializedName;

public class One {

        @SerializedName("alerts")
        private Alerts alerts;


        public Alerts getAlerts() {
            return alerts;
        }

        public void setAlerts(Alerts alerts) {
            this.alerts = alerts;
        }
    }

