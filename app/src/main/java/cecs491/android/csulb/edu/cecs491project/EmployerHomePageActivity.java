package cecs491.android.csulb.edu.cecs491project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
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
 * Created by Michael on 3/3/2018.
 */

public class EmployerHomePageActivity extends AppCompatActivity {


    private ToggleButton clockInButton;
    private ToggleButton breakButton;
    private TextView todaysShift;

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private String possibleShiftId;

    private FirebaseUser user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home_page);


        clockInButton = (ToggleButton) findViewById(R.id.toggleClockButtonEmployer);
        todaysShift = (TextView) findViewById(R.id.DailyShiftEmployer);
        user = FirebaseAuth.getInstance().getCurrentUser();

        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyy");
        String today = simpleDateFormat.format(d).toString();
        possibleShiftId = user.getUid()+"@"+today;

        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Shifts");
        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild(possibleShiftId)){
                    String startTime = dataSnapshot.child(possibleShiftId).child("Start Time").getValue().toString();
                    String endTime = dataSnapshot.child(possibleShiftId).child("End Time").getValue().toString();
                    String shift = startTime + "   to   " + endTime;
                    String realStartTime = dataSnapshot.child(possibleShiftId).child("Real Start Time").getValue().toString().trim();
                    if (realStartTime.equalsIgnoreCase("") || realStartTime.equals(null)){
                        clockInButton.setEnabled(true);
                    }
                    todaysShift.setText("Today's shift: " + shift + "\n\n" + "Enjoy work today - don't be late!");
                    //Toast.makeText(EmployeeHomePageActivity.this, shift, Toast.LENGTH_LONG).show();
                } else {
                    //Toast.makeText(EmployeeHomePageActivity.this, "No shift today", Toast.LENGTH_LONG).show();
                    todaysShift.setText("No shift today! Enjoy your day off!");
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ref.addListenerForSingleValueEvent(valueEventListener);


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
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(EmployerHomePageActivity.this, EmployeeProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EmployerHomePageActivity.this, EmployeeScheduleActivity.class);
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


        breakButton = (ToggleButton) findViewById(R.id.breakButtonEmployer);
        clockInButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {

                if (isChecked){
                    clockInButton.setTextOn("Clock out");
                    breakButton.setEnabled(true);
                    String timestamp = new SimpleDateFormat("HH:mm").format(new Date());
                    DatabaseReference shiftRef = ref.child(possibleShiftId);
                    Map<String, Object> shiftUpdate = new HashMap<>();
                    shiftUpdate.put("Real Start Time", timestamp);
                    shiftRef.updateChildren(shiftUpdate);
                } else {
                    clockInButton.setTextOff("Clock in");
                    breakButton.setEnabled(false);
                    String timestamp = new SimpleDateFormat("HH:mm").format(new Date());
                    DatabaseReference shiftRef = ref.child(possibleShiftId);
                    Map<String, Object> shiftUpdate = new HashMap<>();
                    shiftUpdate.put("Real End Time", timestamp);
                    shiftRef.updateChildren(shiftUpdate);
                    clockInButton.setEnabled(false);

                }
            }
        });
        breakButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    breakButton.setTextOn("Start break");
                    clockInButton.setEnabled(true);
                } else {
                    breakButton.setTextOff("Stop break");
                    clockInButton.setEnabled(false);
                }
            }
        });


    }
}
