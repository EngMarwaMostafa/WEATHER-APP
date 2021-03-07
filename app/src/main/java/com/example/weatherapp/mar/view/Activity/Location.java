//package absoapps.numberlocator.activities;
//
//import android.content.Intent;
//import android.location.Address;
//import android.location.Geocoder;
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.FrameLayout;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//
//import com.example.weatherapp.mar.view.LocationClass.GPSTracker;
//import com.google.android.gms.maps.model.LatLng;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Locale;
//
//
//public class CurrentLocation extends AppCompatActivity {
//
//
//    FrameLayout frameLayout;
//
//    @Override
//
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//
//        edlocation = findViewById(R.id.edlocation);
//
//
//        Button Location = findViewById(R.id.Location);
//        Location.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                try {
//
//
//                    latitude = position.latitude;
//                    longitude = position.longitude;
//                    Intent intent = new Intent(CurrentLocation.this, MyWebview.class);
//
//                    startActivity(intent);
//                } catch (Exception ee) {
//
//                }
//            }
//        });
//
//    }
//
//
//}
