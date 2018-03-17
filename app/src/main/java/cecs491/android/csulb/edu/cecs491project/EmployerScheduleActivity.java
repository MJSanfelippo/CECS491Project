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

    /**
     * the employer displayed week text view reference
     */
    private TextView employerDisplayedWeekTextView;

    /**
     * the back button reference
     */
    private Button employerBackButton;

    /**
     * the forward button reference
     */
    private Button employerForwardButton;

    /**
     * the bottom nav view reference
     */
    private BottomNavigationView navigation;

    /**
     * the firebase database reference
     */
    private FirebaseDatabase db;

    /**
     * the database reference itself
     */
    private DatabaseReference ref;

    /**
     * the calendar reference to keep track of weeks/dates
     */
    private Calendar calendar;

    /**
     * the date formatter
     */
    private SimpleDateFormat dayOfWeek;

    /**
     * instantiate all layout components
     */
    private void instantiatelayout(){
        employerDisplayedWeekTextView = findViewById(R.id.employerDisplayedWeekTextView);
        employerBackButton = findViewById(R.id.employerBackButton);
        employerBackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastSunday = getLastSunday();
                String lastSaturday = getLastSaturday();
                employerDisplayedWeekTextView.setText(lastSunday + "  -  " + lastSaturday);
            }
        });
        employerForwardButton = findViewById(R.id.employerForwardButton);
        employerForwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nextSunday = getNextSunday();
                String nextSaturday = getNextSaturday();
                employerDisplayedWeekTextView.setText(nextSunday + "  -  " + nextSaturday);
            }
        });
        navigation = findViewById(R.id.navigationEmployer);
    }

    /**
     * instantiate the calendar components
     */
    private void instantiateCalendarComponents(){
        dayOfWeek = new SimpleDateFormat("MM/dd/yyyy");
        calendar = Calendar.getInstance();
    }

    /**
     * handle what happens when the user clicks on the navigation menu
     */
    private void handleNavMenu(){
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
                        intent = new Intent(EmployerScheduleActivity.this, EmployerAdminActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }}
        );
    }

    /**
     * create the activity
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_schedule);

        instantiatelayout();
        instantiateCalendarComponents();
        handleNavMenu();
        setCurrentWeek();
    }

    /**
     * gets the most recent sunday using magic
     * @return the date of the most recent sunday
     */
    public String getMostRecentSunday(){
        Date current = new Date();
        SimpleDateFormat dayOfWeek = new SimpleDateFormat("MM/dd/yyyy");
        int dayOfTheWeek = calendar.get(Calendar.DAY_OF_WEEK);
        calendar.add(Calendar.DAY_OF_WEEK, Calendar.SUNDAY - dayOfTheWeek);
        String lastSunday = dayOfWeek.format(calendar.getTime());
        return lastSunday;
    }

    /**
     * get the upcoming saturday by adding 6 to current date
     * @return the date of the upcoming saturday
     */
    public String getUpcomingSaturday(){
        calendar.add(Calendar.DAY_OF_YEAR, 6);
        String upcomingSaturday = dayOfWeek.format(calendar.getTime());
        return upcomingSaturday;
    }

    /**
     * get the last saturday by adding 6 to the current date
     * the date before this will be the last sunday, so sunday+6 = saturday
     * @return the date that is the last saturday
     */
    public String getLastSaturday(){
        calendar.add(calendar.DAY_OF_YEAR, 6);
        String lastSaturday = dayOfWeek.format(calendar.getTime());
        return lastSaturday;
    }

    /**
     * get the date of the next sunday by adding 1 to the current date
     * the date before this will be the previous saturday, so saturday+1=sunday
     * @return the date that is the next sunday
     */
    public String getNextSunday(){
        calendar.add(calendar.DAY_OF_YEAR, 1);
        String nextSunday = dayOfWeek.format(calendar.getTime());
        return nextSunday;
    }

    /**
     * get the date of the next saturday by adding 6
     * the date before this will be a sunday, so sunday+6=saturday
     * @return the date of the next saturday
     */
    public String getNextSaturday(){
        calendar.add(calendar.DAY_OF_YEAR, 6);
        String nextSaturday = dayOfWeek.format(calendar.getTime());
        return nextSaturday;
    }

    /**
     * get the date of the last sunday by adding -7 to the current date which is sunday
     * @return the date of the last sunday
     */
    public String getLastSunday(){
        String mostRecentSunday = getMostRecentSunday();
        calendar.add(calendar.DAY_OF_YEAR, -7);
        String lastSunday = dayOfWeek.format(calendar.getTime());
        return lastSunday;
    }
    /**
     * get the current dates for the week
     * get most recent sunday and upcoming saturday
     */
    private void setCurrentWeek(){
        Date current = new Date();
        String formattedDate = dayOfWeek.format(current);
        employerDisplayedWeekTextView.setText(formattedDate);
        String lastSunday = getMostRecentSunday();
        String thisComingSaturday = getUpcomingSaturday();
        employerDisplayedWeekTextView.setText(lastSunday + "  -  " + thisComingSaturday);
    }

}
