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
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddShift extends AppCompatActivity
{

    /**
     * the bottom navigation bar reference
     */
    private BottomNavigationView navigation;

    /**
     * the text view for the date
     */
    private TextView dateTextView;

    /**
     * the text view for the day of the week
     */
    private TextView dayOfWeekTextView;

    /**
     * the edit text for the start time
     */
    private EditText startTimeEditTextView;

    /**
     * the edit text for the
     */
    private EditText endTimeEditTextView;

    /**
     * the button to add a shift
     */
    private Button addButton;

    /**
     * the button to cancel the operation
     */
    private Button cancelButton;

    /**
     * instance of a firebase database
     */
    private FirebaseDatabase db;

    /**
     * instance of a database reference for shifts
     */
    private DatabaseReference shiftsRef;

    /**
     * instance of a database reference for users
     */
    private DatabaseReference usersRef;

    /**
     * the real date in the form MMddYYYY
     */
    private String realDate;

    /**
     * the pretty date in the form MM/dd/YYYY
     */
    private String prettyDate;

    /**
     * the day of the week
     */
    private String dayOfWeek;

    /**
     * the first name of the person
     */
    private String firstName;

    /**
     * the last name of the person
     */
    private String lastName;

    /**
     * the uid of the person
     */
    private String uid;

    /**
     * value event listener to get values from database
     */
    private ValueEventListener valueEventListenerUser;

    /**
     * get the info from the previous intent's bundle
     */
    private void getInfo(){
        Intent i = getIntent();
        Bundle b = i.getExtras();
        realDate = b.getString("Real Date");
        prettyDate = b.getString("Pretty Date");
        dayOfWeek = b.getString("Day of Week");
        uid = b.getString("uid");
    }

    /**
     * instantiate the layout including button on click listeners
     */
    private void instantiateLayout(){
        dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText("Date: " + prettyDate);
        dayOfWeekTextView = findViewById(R.id.dayOfWeekTextView);
        dayOfWeekTextView.setText("Day: " + dayOfWeek);

        startTimeEditTextView = findViewById(R.id.startTimeEditText);
        endTimeEditTextView = findViewById(R.id.endTimeEditText);

        addButton = findViewById(R.id.addButton);
        cancelButton = findViewById(R.id.cancelButton);

        navigation = findViewById(R.id.navigation);

        setOnClickListeners();
    }

    /**
     * sets the on click listeners for the buttons
     * if they cancel, simply go back to previous activity
     * if they add it, put it in the database after checking that it is valid
     */
    private void setOnClickListeners(){
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(AddShift.this, EditScheduleActivity.class);
                Bundle b = new Bundle();
                b.putString("uid", uid);
                i.putExtras(b);
                startActivity(i);
            }
        });

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String startTime = startTimeEditTextView.getText().toString().trim();
                String endTime = endTimeEditTextView.getText().toString().trim();
                if (isTimeValid(startTime) && isTimeValid(endTime)){
                    Shifts s = new Shifts(startTime, endTime, firstName, lastName, realDate);
                    addShiftToDatabase(s);
                    goToEditSchedule();
                } else {
                    Toast.makeText(AddShift.this, "Times are not valid", Toast.LENGTH_LONG).show();
                }
            }
        });
    }
    /**
     * Intent to go to the edit schedule activity, passing in uid
     */
    private void goToEditSchedule(){
        Intent i = new Intent(AddShift.this, EditScheduleActivity.class);
        Bundle b = new Bundle();
        b.putString("uid", uid);
        i.putExtras(b);
        startActivity(i);
    }

    /**
     * validation checking for a time
     * @param time the time value in question
     * @return true if it's valid, false otherwise
     */
    private boolean isTimeValid(String time){
        return time.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

    /**
     * adds the shift to the database with the id of uid@date
     * @param shift the shift to be added
     */
    private void addShiftToDatabase(Shifts shift){
        String id = uid + "@" + realDate;
        shiftsRef.child(id).child("Date").setValue(shift.getDate());
        shiftsRef.child(id).child("End Break Time").setValue(shift.getBreakEnd());
        shiftsRef.child(id).child("End Time").setValue(shift.getEndTime());
        shiftsRef.child(id).child("First Name").setValue(shift.getFirstName());
        shiftsRef.child(id).child("Last Name").setValue(shift.getLastName());
        shiftsRef.child(id).child("Real End Time").setValue(shift.getRealEndTime());
        shiftsRef.child(id).child("Real Start Time").setValue(shift.getRealStartTime());
        shiftsRef.child(id).child("Start Break Time").setValue(shift.getBreakStart());
        shiftsRef.child(id).child("Start Time").setValue(shift.getStartTime());

    }

    /**
     * handle what happens when the user clicks on the navigation menu
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
                        intent = new Intent(AddShift.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(AddShift.this, EmployerProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(AddShift.this, EmployerScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
                        intent = new Intent(AddShift.this, EmployerAdminActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }}
        );
    }

    /**
     * instantiate all firebase components
     */
    private void instantiateFirebase(){
        db = FirebaseDatabase.getInstance();
        shiftsRef = db.getReference("Shifts");
        usersRef = db.getReference("Users");
    }
    /**
     * create the activity
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shift);

        getInfo();
        instantiateLayout();
        instantiateFirebase();
        instantiateValueEventListener();
        handleNavMenu();
    }

    /**
     * gets the first and last name of the given uid
     */
    private void instantiateValueEventListener(){
        valueEventListenerUser = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                firstName = dataSnapshot.child("First Name").getValue().toString();
                lastName = dataSnapshot.child("Last Name").getValue().toString();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersRef.child(uid).addListenerForSingleValueEvent(valueEventListenerUser);
    }

}
