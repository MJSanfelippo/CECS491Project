package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;

//import android.icu.text.SimpleDateFormat;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class EmployeeScheduleActivity extends AppCompatActivity {

    //private Spinner dropDown;
    private TextView employeeNameTextView;
    private TextView displayedWeekTextView;
    private TextView scheduleTextView;
    private Button backButton;
    private Button forwardButton;

    private BottomNavigationView navigation;

    private FirebaseDatabase db;
    private DatabaseReference ref;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_schedule);



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

        employeeNameTextView = (TextView) findViewById(R.id.employeeTextView);
        displayedWeekTextView = (TextView) findViewById(R.id.displayedWeekTextView);
        scheduleTextView = (TextView) findViewById(R.id.scheduleTextView);
        backButton = (Button) findViewById(R.id.backButton);
        forwardButton = (Button) findViewById(R.id.forwardButton);

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        Menu menu = navigation.getMenu();
        MenuItem item = menu.getItem(2);
        item.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        intent = new Intent(EmployeeScheduleActivity.this, EmployeeHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(EmployeeScheduleActivity.this, EmployeeProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:

                        return true;
                    case R.id.navigation_announcements:
                        return true;
                }
                return false;
            }}
        );


    }

    @Override
    protected void onStart() {
        super.onStart();
        Date current = new Date();
        SimpleDateFormat dayOfWeek = new SimpleDateFormat("MM/dd/yyyy");
        String formattedDate = dayOfWeek.format(current);
        displayedWeekTextView.setText(formattedDate);

        Calendar cal = Calendar.getInstance();
        int dayOfTheWeek = cal.get(Calendar.DAY_OF_WEEK);
        cal.add(Calendar.DAY_OF_WEEK, Calendar.SUNDAY - dayOfTheWeek);
        String lastSunday = dayOfWeek.format(cal.getTime());
        String thisComingSaturday;
        cal.add(Calendar.DAY_OF_WEEK, 6);
        thisComingSaturday = dayOfWeek.format(cal.getTime());
        displayedWeekTextView.setText(lastSunday + "  -  " + thisComingSaturday);
    }
}
