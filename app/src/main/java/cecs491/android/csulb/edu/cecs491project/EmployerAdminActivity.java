package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pbour on 3/16/2018.
 */

public class EmployerAdminActivity extends AppCompatActivity {
    Button editShiftButton, editEmployeeButton, editAnnouncementsButton;

    //private FirebaseDatabase db;
    //private DatabaseReference ref;
    //private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_admin);
        editShiftButton = (Button) findViewById(R.id.editShiftButton);
        editEmployeeButton = (Button) findViewById(R.id.editEmployeeButton);
        editAnnouncementsButton = (Button) findViewById(R.id.editAnnouncementsButton);

        editShiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                
            }
        });

        editEmployeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        editAnnouncementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigationEmployer);
        Menu menu = navigation.getMenu();
        MenuItem item = menu.getItem(0);
        item.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
                                                           @Override
                                                           public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                                                               Intent intent;
                                                               switch (item.getItemId()) {
                                                                   case R.id.navigation_home:
                                                                       intent = new Intent(EmployerAdminActivity.this, EmployerHomePageActivity.class);
                                                                       startActivity(intent);
                                                                       return true;
                                                                   case R.id.navigation_profile:
                                                                       intent = new Intent(EmployerAdminActivity.this, EmployerProfileActivity.class);
                                                                       startActivity(intent);
                                                                       return true;
                                                                   case R.id.navigation_schedule:
                                                                       intent = new Intent(EmployerAdminActivity.this, EmployerScheduleActivity.class);
                                                                       startActivity(intent);
                                                                       return true;
                                                                   case R.id.navigation_announcements:
                                                                       return true;
                                                                   case R.id.navigation_admin:
                                                                       return true;
                                                               }
                                                               return false;
                                                           }
                                                       }
        );




    }
}
