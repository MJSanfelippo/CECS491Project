package cecs491.android.csulb.edu.cecs491project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.internal.BottomNavigationMenu;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Michael on 3/3/2018.
 * editied by Peter 3/4/2018 - ongoing...
 */

public class EmployeeHomePageActivity extends AppCompatActivity {

    /**
     * the clock in button reference
     */
    private ToggleButton clockInButton;

    /**
     * the break button reference
     */
    private ToggleButton breakButton;

    /**
     * the textview for today's shift reference
     */
    private TextView todaysShift;

    private TextView announcementTextView;

    /**
     * firebase database
     */
    private FirebaseDatabase db;

    /**
     * firebase user
     */
    private FirebaseUser user;

    /**
     * reference to the database
     */
    private DatabaseReference ref;
    /**
     * the database reference for the announcement.
     */
    private DatabaseReference announcementRef;
    /**
     * possible shift id will be in the form of [user id]@[date]
     */
    private String possibleShiftId;

    /**
     * the value event listener
     */
    private ValueEventListener valueEventListener;

    /**
     * the bottom navigation bar
     */
    private BottomNavigationView navigation;

    /**
     * instantiate all the layout componenets including the event listeners
     */
    private void instantiateLayout(){
        navigation = findViewById(R.id.navigation);
        clockInButton = findViewById(R.id.toggleClockButton);
        todaysShift = findViewById(R.id.DailyShift);
        breakButton = findViewById(R.id.breakButton);
        announcementTextView = findViewById(R.id.Announcement_Display);
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

    /**
     * instantiate all the firebase componenets
     */
    private void instantiateFirebase(){
        user = FirebaseAuth.getInstance().getCurrentUser();
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Shifts");
        announcementRef = db.getReference("Announcements");
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
            public void onCancelled(DatabaseError databaseError) {

            }
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
                        intent = new Intent(EmployeeHomePageActivity.this, EmployeeProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EmployeeHomePageActivity.this, EmployeeScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        intent = new Intent(EmployeeHomePageActivity.this, EmployeeViewAnnouncements.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }}
        );
    }
    /**
     * Method that retrieves all the announcement in the database and store it in an arrayList.
     */
    private void getAllAnnouncements() {
        announcementRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if(dataSnapshot.hasChildren()){
                    ArrayList<Announcement> announcementList = new ArrayList<>();
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        Announcement ann = ds.getValue(Announcement.class);
                        announcementList.add(ann);
                    }
                    setLatestAnnouncement(announcementList);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
    /**
     * Method that sets the latest announcement into the announcement text view field..
     * @param announcement
     */
    private void setLatestAnnouncement(ArrayList<Announcement> announcement) {
        int lastAnnouncement = announcement.size() - 1;
        Announcement ann = announcement.get(lastAnnouncement);
        if(ann.getMessage().length() > 0 ) {
            announcementTextView.setText(ann.getMessage() + "\n\n" +
                    "Posted by: " + ann.getPostedBy() + "\n" + "Posted on: " + ann.getTimestamp());
        }
    }

    /**
     * instantiate all components of the activity
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home_page);

        instantiateLayout();
        instantiateFirebase();
        getPossibleShiftId();
        instantiateValueEventListener();
        handleNavMenu();
        getAllAnnouncements();
    }

    @Override
    protected void onResume(){
        super.onResume();
        handleNavMenu();
    }
}