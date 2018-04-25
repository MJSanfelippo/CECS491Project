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

    private TextView dateTextView;
    private TextView dayOfWeekTextView;

    private EditText startTimeEditTextView;
    private EditText endTimeEditTextView;

    private Button addButton;
    private Button cancelButton;

    private FirebaseDatabase db;
    private DatabaseReference shiftsRef;
    private DatabaseReference usersRef;

    private String realDate;
    private String prettyDate;
    private String dayOfWeek;
    private String firstName;
    private String lastName;
    private String uid;

    private ValueEventListener valueEventListenerUser;

    private void getInfo(){
        Intent i = getIntent();
        Bundle b = i.getExtras();
        realDate = b.getString("Real Date");
        prettyDate = b.getString("Pretty Date");
        dayOfWeek = b.getString("Day of Week");
        uid = b.getString("uid");
    }

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

    private void goToEditSchedule(){
        Intent i = new Intent(AddShift.this, EditScheduleActivity.class);
        Bundle b = new Bundle();
        b.putString("uid", uid);
        i.putExtras(b);
        startActivity(i);
    }
    private boolean isTimeValid(String time){
        return time.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
    }
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
