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
import android.widget.TextView;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by pbour on 3/16/2018.
 */

public class EmployerScheduleActivity extends AppCompatActivity {

    //private Spinner dropDown;
    private TextView employerNameTextView;
    private TextView employerDisplayedWeekTextView;
    private TextView employerScheduleTextView;
    private Button employerBackButton;
    private Button employerForwardButton;

    private BottomNavigationView navigation;

    private FirebaseDatabase db;
    private DatabaseReference ref;
    private Calendar calendar;
    private SimpleDateFormat dayOfWeek;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_schedule);


        dayOfWeek = new SimpleDateFormat("MM/dd/yyyy");

        // How to get list of all users
        /*
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Users");

        ValueEventListener valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s: dataSnapshot.getChildren()){
                    Toast.makeText(EmployeeScheduleActivity.this, s.getKey(), Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addValueEventListener(valueEventListener);
        */

        employerNameTextView = (TextView) findViewById(R.id.employerTextView);
        employerDisplayedWeekTextView = (TextView) findViewById(R.id.employerDisplayedWeekTextView);
        employerScheduleTextView = (TextView) findViewById(R.id.employerScheduleTextView);
        employerBackButton = (Button) findViewById(R.id.employerBackButton);
        employerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastSunday = getLastSunday();
                String lastSaturday = getLastSaturday();
                employerDisplayedWeekTextView.setText(lastSunday + "  -  " + lastSaturday);
            }
        });
        employerForwardButton = (Button) findViewById(R.id.employerForwardButton);
        employerForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nextSunday = getNextSunday();
                String nextSaturday = getNextSaturday();
                employerDisplayedWeekTextView.setText(nextSunday + "  -  " + nextSaturday);
            }
        });

        calendar = Calendar.getInstance();

        navigation = (BottomNavigationView) findViewById(R.id.navigationEmployer);
        Menu menu = navigation.getMenu();
        MenuItem item = menu.getItem(2);
        item.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        intent = new Intent(EmployerScheduleActivity.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(EmployerScheduleActivity.this, EmployerProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:

                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
                        return true;
                }
                return false;
            }}
        );


    }

    public String getMostRecentSunday(){
        Date current = new Date();
        SimpleDateFormat dayOfWeek = new SimpleDateFormat("MM/dd/yyyy");
        int dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, Calendar.SUNDAY - dayOfTheWeek);
        String lastSunday = dayOfWeek.format(calendar.getTime());
        return lastSunday;
    }

    public String getUpcomingSaturday(){
        calendar.add(Calendar.DAY_OF_WEEK, 6);
        String upcomingSaturday = dayOfWeek.format(calendar.getTime());
        return upcomingSaturday;
    }

    public String getLastSaturday(){
        calendar.add(calendar.DAY_OF_YEAR, 6);
        String lastSaturday = dayOfWeek.format(calendar.getTime());
        return lastSaturday;
    }

    public String getNextSunday(){
        calendar.add(calendar.DAY_OF_YEAR, 1);
        String nextSunday = dayOfWeek.format(calendar.getTime());
        return nextSunday;
    }

    public String getNextSaturday(){
        calendar.add(calendar.DAY_OF_YEAR, 6);
        String nextSaturday = dayOfWeek.format(calendar.getTime());
        return nextSaturday;
    }
    public String getLastSunday(){
        String mostRecentSunday = getMostRecentSunday();
        calendar.add(calendar.DAY_OF_YEAR, -7);
        String lastSunday = dayOfWeek.format(calendar.getTime());
        return lastSunday;
    }
    @Override
    protected void onStart() {
        super.onStart();
        Date current = new Date();
        String formattedDate = dayOfWeek.format(current);
        employerDisplayedWeekTextView.setText(formattedDate);

        String lastSunday = getMostRecentSunday();
        String thisComingSaturday = getUpcomingSaturday();
        employerDisplayedWeekTextView.setText(lastSunday + "  -  " + thisComingSaturday);
    }
}
