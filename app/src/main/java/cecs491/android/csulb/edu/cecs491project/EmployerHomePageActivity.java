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

    /**
     * the clock in button reference
     */
    private ToggleButton clockInButton;

    /**
     * the break button reference
     */
    private ToggleButton breakButton;

    /**
     * today's shift text view reference
     */
    private TextView todaysShift;

    /**
     * the firebase database
     */
    private FirebaseDatabase db;

    /**
     * the database reference
     */
    private DatabaseReference ref;

    /**
     * the possible shift id in the form of [userid]@[date]
     */
    private String possibleShiftId;

    /**
     * the firebase user reference
     */
    private FirebaseUser user;

    /**
     * the navigation bar
     */
    private BottomNavigationView navigation;

    /**
     * the value event listener
     */
    private ValueEventListener valueEventListener;

    /**
     * instantiate all the layout componenets including the event listeners
     */
    private void instantiateLayout(){
        clockInButton = findViewById(R.id.toggleClockButtonEmployer);
        todaysShift = findViewById(R.id.DailyShiftEmployer);
        breakButton = findViewById(R.id.breakButtonEmployer);
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
        navigation = findViewById(R.id.navigationEmployer);
        breakButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    breakButton.setTextOn("Stop break");
                    clockInButton.setEnabled(false);
                    String timestamp = new SimpleDateFormat("HH:mm").format(new Date());
                    DatabaseReference shiftRef = ref.child(possibleShiftId);
                    Map<String, Object> shiftUpdate = new HashMap<>();
                    shiftUpdate.put("Start Break Time", timestamp);
                    shiftRef.updateChildren(shiftUpdate);
                } else {
                    breakButton.setTextOff("Start break");
                    clockInButton.setEnabled(true);
                    String timestamp = new SimpleDateFormat("HH:mm").format(new Date());
                    DatabaseReference shiftRef = ref.child(possibleShiftId);
                    Map<String, Object> shiftUpdate = new HashMap<>();
                    shiftUpdate.put("End Break Time", timestamp);
                    shiftRef.updateChildren(shiftUpdate);
                }
            }
        });
    }

    /**
     * instantiate all the firebase componenets
     */
    private void instantiateFirebase(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Shifts");
    }

    /**
     * get the possible shift id based on user id and today's date
     */
    private void getPossibleShiftId(){
        Date d = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MMddyyyy");
        String today = simpleDateFormat.format(d).toString();
        possibleShiftId = user.getUid()+"@"+today;
    }

    /**
     * instantiate the value event listener
     * check for changes in the data for the possible shift
     * if the shift exists, check if the user has not clocked in yet... if so, enable the clock in button
     */
    private void instantiateValueEventListener(){
        valueEventListener = new ValueEventListener() {
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
                } else {
                    todaysShift.setText("No shift today! Enjoy your day off!");
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) { }
        };

        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    /**
     * handle what happens when the user presses on each navigation button
     */
    private void handleNavMenu(){
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
                        intent = new Intent(EmployerHomePageActivity.this, EmployerProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EmployerHomePageActivity.this, EmployerScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
                        intent = new Intent(EmployerHomePageActivity.this, EmployerAdminActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }
        }
        );
    }

    /**
     * create the activity
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home_page);

        instantiateLayout();
        instantiateFirebase();
        getPossibleShiftId();
        instantiateValueEventListener();
        handleNavMenu();
    }

    @Override
    protected void onResume(){
        super.onResume();
        handleNavMenu();
    }
}