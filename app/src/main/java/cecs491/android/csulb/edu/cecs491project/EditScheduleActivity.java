package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class EditScheduleActivity extends AppCompatActivity {

    /**
     * the displayed week text view reference
     */
    private TextView displayedWeekTextView;

    /**
     * the back button reference
     */
    private Button backButton;

    /**
     * the forward button reference
     */
    private Button forwardButton;

    /**
     * the bottom navigation bar reference
     */
    private BottomNavigationView navigation;

    /**
     * the database reference itself
     */
    private DatabaseReference ref;

    /**
     * the firebase user
     */
    private FirebaseUser fbUser;

    /**
     * the value event listener
     */
    private ValueEventListener valueEventListener;

    /**
     * the calendar reference to keep track of weeks/dates
     */
    private Calendar calendar;
    private Calendar tempCalendar;

    /**
     * the date formatter
     */
    private SimpleDateFormat dayOfWeek;
    private SimpleDateFormat sdf;

    /**
     * the date for the selected days of the week
     */
    private String selectedSunday;
    private String selectedMonday;
    private String selectedTuesday;
    private String selectedWednesday;
    private String selectedThursday;
    private String selectedFriday;
    private String selectedSaturday;

    /**
     * the display text view for the days of the week
     */
    private TextView sundayTextView;
    private TextView mondayTextView;
    private TextView tuesdayTextView;
    private TextView wednesdayTextView;
    private TextView thursdayTextView;
    private TextView fridayTextView;
    private TextView saturdayTextView;

    /**
     * the buttons to edit if shift exists
     */
    private ImageButton sundayEditButton;
    private ImageButton mondayEditButton;
    private ImageButton tuesdayEditButton;
    private ImageButton wednesdayEditButton;
    private ImageButton thursdayEditButton;
    private ImageButton fridayEditButton;
    private ImageButton saturdayEditButton;

    /**
     * the buttons to add a shift if one doesn't exist
     */
    private ImageButton sundayAddButton;
    private ImageButton mondayAddButton;
    private ImageButton tuesdayAddButton;
    private ImageButton wednesdayAddButton;
    private ImageButton thursdayAddButton;
    private ImageButton fridayAddButton;
    private ImageButton saturdayAddButton;

    /**
     * arrays to hold the edit and add buttons
     */
    private ImageButton[] editButtonList;
    private ImageButton[] addButtonList;

    /**
     * array to hold days of the week
     */
    private String[] daysOfWeek;

    /**
     * the user's id
     */
    private String uid;

    /**
     * instantiate all layout components
     */
    private void instantiateLayout(){
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        uid = b.getString("uid");
        displayedWeekTextView = findViewById(R.id.displayedWeekTextView);
        backButton = findViewById(R.id.backButton);

        forwardButton = findViewById(R.id.forwardButton);

        sundayTextView = findViewById(R.id.sundayTextView);
        mondayTextView = findViewById(R.id.mondayTextView);
        tuesdayTextView = findViewById(R.id.tuesdayTextView);
        wednesdayTextView = findViewById(R.id.wednesdayTextView);
        thursdayTextView = findViewById(R.id.thursdayTextView);
        fridayTextView = findViewById(R.id.fridayTextView);
        saturdayTextView = findViewById(R.id.saturdayTextView);

        sundayEditButton = findViewById(R.id.sunEdit);
        mondayEditButton = findViewById(R.id.monEdit);
        tuesdayEditButton = findViewById(R.id.tueEdit);
        wednesdayEditButton = findViewById(R.id.wedEdit);
        thursdayEditButton = findViewById(R.id.thuEdit);
        fridayEditButton = findViewById(R.id.friEdit);
        saturdayEditButton = findViewById(R.id.satEdit);

        sundayAddButton = findViewById(R.id.sundayAdd);
        mondayAddButton = findViewById(R.id.mondayAdd);
        tuesdayAddButton = findViewById(R.id.tuesdayAdd);
        wednesdayAddButton = findViewById(R.id.wednesdayAdd);
        thursdayAddButton = findViewById(R.id.thursdayAdd);
        fridayAddButton = findViewById(R.id.fridayAdd);
        saturdayAddButton = findViewById(R.id.saturdayAdd);

        navigation = findViewById(R.id.navigationEmployer);

        daysOfWeek = new String[] {"Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"};
        editButtonList = new ImageButton[]{sundayEditButton, mondayEditButton, tuesdayEditButton, wednesdayEditButton, thursdayEditButton, fridayEditButton, saturdayEditButton};
        addButtonList = new ImageButton[]{sundayAddButton, mondayAddButton, tuesdayAddButton, wednesdayAddButton, thursdayAddButton, fridayAddButton, saturdayAddButton};

        setOnClickListeners();

    }

    /**
     * sets the on click listeners
     * the only tricky thing here is the edit and add buttons
     * basically loop 7 times and on click, fire off an intent and include the dates, uid, and day of week depending on what day they choose
     */
    private void setOnClickListeners(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String lastSunday = getLastSunday();
                String lastSaturday = getLastSaturday();
                displayedWeekTextView.setText(lastSunday + "  -  " + lastSaturday);
                selectedSunday = lastSunday;
                setSchedule();
                instantiateValueEventListener();
            }
        });

        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nextSunday = getNextSunday();
                String nextSaturday = getNextSaturday();
                displayedWeekTextView.setText(nextSunday + "  -  " + nextSaturday);
                selectedSunday = nextSunday;
                setSchedule();
                instantiateValueEventListener();
            }
        });
        for (int i=0; i<7; i++){
            final int localI = i;
            editButtonList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EditScheduleActivity.this, EditEmployeeShift.class);
                    Bundle b = new Bundle();
                    b.putString("Pretty Date", getPrettyDate(localI));
                    b.putString("Real Date", getRealDate(localI));
                    b.putString("uid", uid);
                    b.putString("Day of Week", daysOfWeek[localI]);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
            addButtonList[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(EditScheduleActivity.this, AddShift.class);
                    Bundle b = new Bundle();
                    b.putString("uid", uid);
                    b.putString("Pretty Date", getPrettyDate(localI));
                    b.putString("Real Date", getRealDate(localI));
                    b.putString("Day of Week", daysOfWeek[localI]);
                    intent.putExtras(b);
                    startActivity(intent);
                }
            });
        }
    }

    /**
     * gets the pretty date which is MM/dd/YYYY
     * @param day the day of week to get the date of
     * @return the date
     */
    private String getPrettyDate(int day){
        calendar.add(Calendar.DAY_OF_YEAR, day-6);
        String date = dayOfWeek.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -(day-6));
        return date;
    }

    /**
     * gets the real date in form MMddYYYY
     * @param day the day of the week
     * @return the date in form MMddYYYY
     */
    private String getRealDate(int day){
        calendar.add(Calendar.DAY_OF_YEAR, day-6);
        String date = sdf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_YEAR, -(day-6));
        return date;
    }

    /**
     * handle what happens when the user clicks stuff in the nav bar
     */
    private void handleNavMenu(){
        Menu menu = navigation.getMenu();
        MenuItem item = menu.getItem(4);
        item.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        intent = new Intent(EditScheduleActivity.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(EditScheduleActivity.this, EmployerProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EditScheduleActivity.this, EmployerScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
                        intent = new Intent(EditScheduleActivity.this, EmployerAdminActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }}
        );
    }

    /**
     * instantiate the calendar components
     */
    private void instantiateCalendarComponents(){
        dayOfWeek = new SimpleDateFormat("MM/dd/yyyy");
        sdf = new SimpleDateFormat("MMddyyyy");
        calendar = Calendar.getInstance();
        tempCalendar = Calendar.getInstance();
    }

    /**
     * create the activity
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule);

        instantiateLayout();
        handleNavMenu();
        instantiateCalendarComponents();
        setCurrentWeek();
        instantiateFirebase();
        setSchedule();
        instantiateValueEventListener();
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
        displayedWeekTextView.setText(formattedDate);
        String lastSunday = getMostRecentSunday();
        String thisComingSaturday = getUpcomingSaturday();
        displayedWeekTextView.setText(lastSunday + "  -  " + thisComingSaturday);
        selectedSunday = lastSunday;
    }

    /**
     * get the current schedule for the whole week from the
     * selected Sunday.
     */
    private void setSchedule(){
        try {
            Date d = dayOfWeek.parse(selectedSunday);
            selectedSunday = sdf.format(d);
            tempCalendar.setTime(d);
            tempCalendar.add(Calendar.DAY_OF_YEAR,1);
            selectedMonday = sdf.format(tempCalendar.getTime());
            tempCalendar.add(Calendar.DAY_OF_YEAR,1);
            selectedTuesday = sdf.format(tempCalendar.getTime());
            tempCalendar.add(Calendar.DAY_OF_YEAR,1);
            selectedWednesday = sdf.format(tempCalendar.getTime());
            tempCalendar.add(Calendar.DAY_OF_YEAR,1);
            selectedThursday = sdf.format(tempCalendar.getTime());
            tempCalendar.add(Calendar.DAY_OF_YEAR,1);
            selectedFriday = sdf.format(tempCalendar.getTime());
            tempCalendar.add(Calendar.DAY_OF_YEAR,1);
            selectedSaturday = sdf.format(tempCalendar.getTime());
        }
        catch(ParseException ex){
            ex.printStackTrace();
        }
    }

    /**
     * instantiate the firebase components
     */
    private void instantiateFirebase(){
        ref = FirebaseDatabase.getInstance().getReference("Shifts");
    }

    private boolean setShiftView(DataSnapshot dataSnapshot, String selectedDate, TextView dateTextView, String dayOfWeek){
        String userShift = uid + "@" + selectedDate;
        if (dataSnapshot.hasChild(userShift)){
            String startTime = dataSnapshot.child(userShift).child("Start Time").getValue().toString();
            String endTime = dataSnapshot.child(userShift).child("End Time").getValue().toString();
            dateTextView.setText(dayOfWeek + ": " + startTime + " to " + endTime);

            return true;
        } else {
            dateTextView.setText(dayOfWeek + ": No shift");
            return false;
        }
    }
    /**
     * instantiate the value event listener to get all schedule time for a given week
     * for the current user and attach it to the corresponding text view
     * also sets the visibility for each button
     */
    private void instantiateValueEventListener(){
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                boolean sunday = setShiftView(dataSnapshot, selectedSunday, sundayTextView, "Sunday");
                boolean monday = setShiftView(dataSnapshot, selectedMonday, mondayTextView, "Monday");
                boolean tuesday = setShiftView(dataSnapshot, selectedTuesday, tuesdayTextView, "Tuesday");
                boolean wednesday = setShiftView(dataSnapshot, selectedWednesday, wednesdayTextView, "Wednesday");
                boolean thursday = setShiftView(dataSnapshot, selectedThursday, thursdayTextView, "Thursday");
                boolean friday = setShiftView(dataSnapshot, selectedFriday, fridayTextView, "Friday");
                boolean saturday = setShiftView(dataSnapshot, selectedSaturday, saturdayTextView, "Saturday");
                setVisibility(sunday, sundayEditButton, sundayAddButton);
                setVisibility(monday, mondayEditButton, mondayAddButton);
                setVisibility(tuesday, tuesdayEditButton, tuesdayAddButton);
                setVisibility(wednesday, wednesdayEditButton, wednesdayAddButton);
                setVisibility(thursday, thursdayEditButton, thursdayAddButton);
                setVisibility(friday, fridayEditButton, fridayAddButton);
                setVisibility(saturday, saturdayEditButton, saturdayAddButton);
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    /**
     * if there is a shift, set the add button to be invisible and the edit button to be visible
     * othewrwise do the opposite
     * @param hasShift if a shift exists
     * @param editButton the edit button
     * @param addButton the add button
     */
    private void setVisibility(boolean hasShift, ImageButton editButton, ImageButton addButton){
        if (hasShift){
            addButton.setVisibility(View.INVISIBLE);
            editButton.setVisibility(View.VISIBLE);
        } else {
            addButton.setVisibility(View.VISIBLE);
            editButton.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * I forget why I need this but yeah
     */
    @Override
    protected void onResume(){
        super.onResume();
        handleNavMenu();
    }

}
