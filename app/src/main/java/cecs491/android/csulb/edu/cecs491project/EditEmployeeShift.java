package cecs491.android.csulb.edu.cecs491project;

import android.app.TimePickerDialog;
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
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by phannachrin on 4/22/18.
 */

public class EditEmployeeShift extends AppCompatActivity {

    /**
     * the nav bar
     */
    private BottomNavigationView navigation;

    /**
     * the date text view
     */
    private TextView dateTextView;

    /**
     * the day of the week text view
     */
    private TextView dayOfWeekTextView;

    /**
     * the start time edit text view
     */
    private EditText startTimeEditTextView;

    /**
     * the end time edit text view
     */
    private EditText endTimeEditTextView;

    /**
     * the save button
     */
    private Button saveButton;

    /**
     * the cancel button
     */
    private Button cancelButton;

    /**
     * the delete button
     */
    private Button deleteButton;

    /**
     * the real date in form MMddYYYY
     */
    private String realDate;

    /**
     * the pretty date in form MM/dd/YYYY
     */
    private String prettyDate;

    /**
     * the firebase database
     */
    private FirebaseDatabase db;

    /**
     * the database reference
     */
    private DatabaseReference ref;

    /**
     * the value event listener to get the info from db
     */
    private ValueEventListener valueEventListener;

    /**
     * the user's uid
     */
    private String uid;

    /**
     * the shift id in form uid@MMddYYYY
     */
    private String shiftId;

    /**
     * the shift's start time
     */
    private String startTime;

    /**
     * the shift's end time
     */
    private String endTime;

    /**
     * the day of the week
     */
    private String day;

    /**
     * instantiate the value event listener, getting the start and end times
     */
    private void instantiateValueEventListener(){
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                startTime = dataSnapshot.child(shiftId).child("Start Time").getValue().toString().trim();
                endTime = dataSnapshot.child(shiftId).child("End Time").getValue().toString().trim();
                startTimeEditTextView.setText(startTime);
                endTimeEditTextView.setText(endTime);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        ref.addListenerForSingleValueEvent(valueEventListener);
    }

    /**
     * instantiate the firebase components
     */
    private void instantiateFirebase(){
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Shifts");
    }

    /**
     * Simply go to the edit schedule activity, attaching the uid
     */
    private void goToEditScheduleActivity(){
        Intent i = new Intent(EditEmployeeShift.this, EditScheduleActivity.class);
        Bundle b = new Bundle();
        b.putString("uid", uid);
        i.putExtras(b);
        startActivity(i);
    }

    /**
     * checks if it's a valid time
     * I found this regex online, hasn't failed me yet
     * @param time the time in question
     * @return true if it's valid, false otherwise
     */
    private boolean isTimeValid(String time){
        return time.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

    /**
     * instantiate all layout components
     */
    private void instantiateLayout(){
        startTimeEditTextView = findViewById(R.id.startTimeEditText);
        endTimeEditTextView = findViewById(R.id.endTimeEditText);

        saveButton = findViewById(R.id.saveButton);
        cancelButton = findViewById(R.id.cancelButton);
        deleteButton = findViewById(R.id.deleteShiftButton);

        dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText("Date: " + prettyDate);
        dayOfWeekTextView = findViewById(R.id.dayOfWeekTextView);
        dayOfWeekTextView.setText("Day: " + day);

        navigation = findViewById(R.id.navigation);

        setOnClickListeners();
    }

    /**
     * set the on click listeners
     * the save button should check the validity of the times, and if valid, update the database
     * delete button deletes the shift from the database
     * cancel button simply goes back
     */
    private void setOnClickListeners(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DatabaseReference shiftRef = ref.child(shiftId);
                Map<String, Object> shiftUpdate = new HashMap<>();
                startTime = startTimeEditTextView.getText().toString().trim();
                endTime = endTimeEditTextView.getText().toString().trim();
                if (isTimeValid(startTime) && isTimeValid(endTime)){
                    shiftUpdate.put("Start Time", startTime);
                    shiftUpdate.put("End Time", endTime);
                    shiftRef.updateChildren(shiftUpdate);
                    goToEditScheduleActivity();
                } else {
                    Toast.makeText(EditEmployeeShift.this, "Times are not valid", Toast.LENGTH_LONG).show();
                }
            }
        });

        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = uid + "@" + realDate;
                DatabaseReference ref = FirebaseDatabase.getInstance().getReference("Shifts").child(id);
                ref.removeValue();
                Toast.makeText(EditEmployeeShift.this, "Shift deleted", Toast.LENGTH_SHORT).show();
                goToEditScheduleActivity();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditScheduleActivity();
            }
        });

    }
    /**
     * get the info from the previous activity
     */
    private void getInfo(){
        Bundle b = getIntent().getExtras();
        uid = b.getString("uid");
        realDate = b.getString("Real Date");
        prettyDate =  b.getString("Pretty Date");
        day = b.getString("Day of Week");
        shiftId = uid + "@" + realDate;
    }

    /**
     * start the activity
     * @param savedInstanceState the previous activity state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee_shift);

        getInfo();
        instantiateLayout();
        instantiateFirebase();
        instantiateValueEventListener();
        handleNavMenu();
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
                        intent = new Intent(EditEmployeeShift.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(EditEmployeeShift.this, EmployerProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EditEmployeeShift.this, EmployerScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
                        intent = new Intent(EditEmployeeShift.this, EmployerAdminActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }}
        );
    }

}