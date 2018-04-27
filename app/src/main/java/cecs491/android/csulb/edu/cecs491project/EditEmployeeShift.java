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

    private BottomNavigationView navigation;
    private TextView dateTextView;
    private TextView dayOfWeekTextView;

    private EditText startTimeEditTextView;
    private EditText endTimeEditTextView;

    private Button saveButton;
    private Button cancelButton;
    private Button deleteButton;

    private String realDate;
    private String prettyDate;

    private FirebaseDatabase db;
    private DatabaseReference ref;

    private ValueEventListener valueEventListener;
    private String uid;
    private String shiftId;
    private String startTime;
    private String endTime;


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
    private void instantiateFirebase(){
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Shifts");
    }

    private void goToEditScheduleActivity(){
        Intent i = new Intent(EditEmployeeShift.this, EditScheduleActivity.class);
        Bundle b = new Bundle();
        b.putString("uid", uid);
        i.putExtras(b);
        startActivity(i);
    }

    private boolean isTimeValid(String time){
        return time.matches("^([0-1]?[0-9]|2[0-3]):[0-5][0-9]$");
    }

    private void instantiateLayout(){
        Intent intent = getIntent();
        Bundle b = intent.getExtras();
        uid = b.getString("uid");
        startTimeEditTextView = findViewById(R.id.startTimeEditText);
        endTimeEditTextView = findViewById(R.id.endTimeEditText);
        saveButton = findViewById(R.id.saveButton);
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
        cancelButton = findViewById(R.id.cancelButton);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                goToEditScheduleActivity();
            }
        });

        deleteButton = findViewById(R.id.deleteShiftButton);
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
        realDate = b.getString("Real Date");
        prettyDate =  b.getString("Pretty Date");
        String day = b.getString("Day of Week");
        shiftId = uid + "@" + realDate;
        dateTextView = findViewById(R.id.dateTextView);
        dateTextView.setText("Date: " + prettyDate);
        dayOfWeekTextView = findViewById(R.id.dayOfWeekTextView);
        dayOfWeekTextView.setText("Day: " + day);
        navigation = findViewById(R.id.navigation);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee_shift);

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
                        intent = new Intent(EditEmployeeShift.this, EmployerViewAnnouncements.class);
                        startActivity(intent);
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
