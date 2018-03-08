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

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class EmployeeScheduleActivity extends AppCompatActivity {

    //private Spinner dropDown;
    private TextView employeeNameTextView;
    private TextView displayedWeekTextView;
    private TextView scheduleTextView;
    private Button backButton;
    private Button forwardButton;

    private BottomNavigationView navigation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_schedule);

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
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        Intent i = new Intent(EmployeeScheduleActivity.this, EmployeeHomePageActivity.class);
                        startActivity(i);
                        return true;
                    case R.id.navigation_profile:

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
        /*
        Date current = Calendar.getInstance().getTime();
        Date begin = current;
        SimpleDateFormat dayOfWeek = new SimpleDateFormat("EEE", Locale.US);

        while (!dayOfWeek.format(begin).equals("SUN")) {
            //Date test = new DateTime(current).minusDays(1).toDate();
            begin = changeDate(begin, -1);
        }

        Date end = changeDate(begin, 6);
        SimpleDateFormat proper = new SimpleDateFormat("MM/DD/YYYY", Locale.US);
        displayedWeekTextView.setText(proper.format(begin) + " - " + proper.format(end));
        */

    }

    private Date changeDate(Date date, int days) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
