package com.example.weatherapp.mar.view.Fragment;

import android.app.NotificationManager;
import android.content.Context;
import android.os.Bundle;

import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ToggleButton;

import com.example.weatherapp.R;


public class NotificationFragment extends Fragment {

    private ToggleButton notification ;
    private View view;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_notification, container, false);

       notification=view.findViewById(R.id.fragment_notification_toggle) ;

       notification.setTextOn("");
       notification.setTextOff("");

        NotificationCompat.Builder nc = new NotificationCompat.Builder(getContext());
       // NotificationManager nm =(NotificationManager) getContext().getSystemService(Context.);
        return view;
    }
}