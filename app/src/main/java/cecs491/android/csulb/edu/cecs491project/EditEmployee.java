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
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditEmployee extends AppCompatActivity {

    /**
     * the first name edit text reference
     */
    private EditText editTextFirstName;

    /**
     * the last name edit text reference
     */
    private EditText editTextLastName;

    /**
     * the phone number edit text reference
     */
    private EditText editTextPhoneNumber;

    /**
     * the instance of the firebase db
     */
    private FirebaseDatabase db;

    /**
     * the database reference
     */
    private DatabaseReference ref;
    /**
     * button to submit edits reference
     */
    private Button saveButton;

    /**
     * the button to cancel editing an employee
     */
    private Button cancelButton;

    /**
     * the button to disable an employee
     */

    private Button disableButton;

    /**
     * the value event listener to get employee info
     */
    private ValueEventListener valueEventListener;
    /**
     * the navigation bar
     */
    private BottomNavigationView navigation;

    /**
     * the user's first name
     */
    private String firstName;

    /**
     * the user's last name
     */
    private String lastName;

    /**
     * the user's phone
     */
    private String phone;

    /**
     * the user's uid
     */
    private String uid;

    /**
     * instantiate the firebase components to users
     */
    private void instantiateFirebase(){
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Users");
    }

    /**
     * handle navigation menu choices
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
                        intent = new Intent(EditEmployee.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(EditEmployee.this, EmployerProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EditEmployee.this, EmployerScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
                        intent = new Intent(EditEmployee.this, EmployerAdminActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }}
        );
    }

    /**
     * instantiate the layouts
     */
    private void instantiateLayout() {
        editTextFirstName = findViewById(R.id.editTextFirstName);
        editTextFirstName.setText(firstName);
        editTextLastName = findViewById(R.id.editTextLastName);
        editTextLastName.setText(lastName);
        editTextPhoneNumber = findViewById(R.id.editTextPhoneNumber);
        editTextPhoneNumber.setText(phone);

        navigation = findViewById(R.id.navigationEmployer);

        saveButton = findViewById(R.id.saveButton);
        disableButton = findViewById(R.id.disableButton);
        cancelButton = findViewById(R.id.cancelButton);

        setOnClickListeners();
    }

    /**
     * sets the on click listeners
     * if they save, update the info and update the database
     * if they disable, then update the user to be disabled
     * if they cancel, go back
     */
    private void setOnClickListeners(){
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userRef = ref.child(uid);
                Map<String, Object> userUpdate = new HashMap<>();
                String firstName = editTextFirstName.getText().toString();
                String lastName = editTextLastName.getText().toString();
                String phone = editTextPhoneNumber.getText().toString();
                userUpdate.put("First Name", firstName);
                userUpdate.put("Last Name", lastName);
                userUpdate.put("Phone", phone);
                userRef.updateChildren(userUpdate);
                Toast.makeText(EditEmployee.this, "Employee saved", Toast.LENGTH_LONG).show();
                Intent i = new Intent(EditEmployee.this, EmployerAdminActivity.class);
                startActivity(i);
            }
        });

        disableButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseReference userRef = ref.child(uid);
                Map<String, Object> userUpdate = new HashMap<>();
                userUpdate.put("Disabled", true);
                userRef.updateChildren(userUpdate);
                Toast.makeText(EditEmployee.this, "User disabled", Toast.LENGTH_LONG).show();
                Intent i = new Intent(EditEmployee.this, EmployerAdminActivity.class);
                startActivity(i);
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EditEmployee.this, EditEmployeeSelection.class);
                startActivity(i);
            }
        });
    }

    /**
     * gets the user info from previous intent
     */
    private void getInfo(){
        Bundle b = getIntent().getExtras();
        firstName = b.getString("firstName");
        lastName = b.getString("lastName");
        phone = b.getString("phone");
        uid = b.getString("uid");
    }
    /**
     * creates the activity
     * @param savedInstanceState the state before this activity
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee);

        getInfo();
        instantiateLayout();
        handleNavMenu();
        instantiateFirebase();
    }
}