package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditEmployeeSelection extends AppCompatActivity {

    /**
     * instruction text view reference
     */
    private TextView selectEmployee;

    /**
     * spinner of all employee names reference
     */
    private Spinner employeeNameSpinner;

    /**
     * button to Edit Employee Page reference
     */
    private Button editPageButton;

    /**
     * button to go back
     */
    private Button backButton;

    /**
     * the navigation bar
     */
    private BottomNavigationView navigation;

    /**
     * instance of the firebase database
     */
    private FirebaseDatabase db;

    /**
     * the database reference
     */
    private DatabaseReference ref;

    /**
     * the value event listener to get the user info
     */
    private ValueEventListener valueEventListener;

    /**
     * list to hold all users
     */
    private List<User> userList;

    /**
     * create a bundle based on the user
     * @param user the user to be used in creating the bundle
     * @return the bundle made from the user
     */
    private Bundle getBundleFromUser(User user){
        Bundle b = new Bundle();
        b.putString("firstName", user.getFirstName());
        b.putString("lastName", user.getLastName());
        b.putString("email", user.getEmail());
        b.putString("phone", user.getPhoneNumber());
        b.putString("uid", user.getUid());
        return b;
    }

    /**
     *
     */
    private void instantiateLayout() {
        selectEmployee = findViewById(R.id.selectEmployee);
        employeeNameSpinner = findViewById(R.id.employeeNameSpinner);
        backButton = findViewById(R.id.employeeSelectionBackButton);
        editPageButton = findViewById(R.id.toEditPageButton);

        navigation = findViewById(R.id.navigationEmployer);

        setOnClickListeners();
    }

    /**
     * set the on click listeners
     * if they go back, just go back
     * if they click go, take them to the edit page and send in user info
     */
    private void setOnClickListeners(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditEmployeeSelection.this, EmployerAdminActivity.class);
                startActivity(i);
            }
        });
        editPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = getUserFromSpinner();
                Intent i = new Intent(EditEmployeeSelection.this, EditEmployee.class);
                Bundle b = getBundleFromUser(user);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    /**
     * start the activity
     * @param savedInstanceState the previous activity's state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee_selection);

        instantiateLayout();
        instantiateFirebase();
        addUsersToList();
        handleNavMenu();
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
                        intent = new Intent(EditEmployeeSelection.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(EditEmployeeSelection.this, EmployerProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EditEmployeeSelection.this, EmployerScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
                        intent = new Intent(EditEmployeeSelection.this, EmployerAdminActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }}
        );
    }

    /**
     * instantiate the firebase components to users
     */
    private void instantiateFirebase(){
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Users");
    }

    /**
     * get the user from the spinner, assume email is the same
     * @return the user whose email matches the one in the dropdown, null otherwise (should never return null, honestly)
     */
    private User getUserFromSpinner(){
        String userChosen = employeeNameSpinner.getSelectedItem().toString();
        String[] info = userChosen.split(" - ");
        String email = info[1].trim();
        for (User user: userList){
            if (user.getEmail().equals(email)){
                return user;
            }
        }
        return null;
    }

    /**
     * set the employee names in the dropdown
     */
    private void setEmployeeNames(){
        List<String> spinnerArray = new ArrayList<>();
        for (User user: userList){
            String fullName = user.getFirstName() + " " + user.getLastName() + " - " + user.getEmail();
            if (user.getDisabled().equalsIgnoreCase("false")) {
                spinnerArray.add(fullName);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditEmployeeSelection.this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeNameSpinner.setAdapter(adapter);
    }
    /**
     * get all users from database and add to the list
     */
    private void addUsersToList() {
        userList = new ArrayList<User>();
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s: dataSnapshot.getChildren()) {
                    User user = new User();
                    String uid = s.getKey();
                    user.setUid(uid);
                    String firstName = s.child("First Name").getValue().toString();
                    String lastName = s.child("Last Name").getValue().toString();
                    String email = s.child("Email").getValue().toString();
                    String phone = s.child("Phone").getValue().toString();
                    String disabled = s.child("Disabled").getValue().toString();
                    user.setDisabled(disabled);
                    user.setFirstName(firstName);
                    user.setLastName(lastName);
                    user.setEmail(email);
                    user.setPhoneNumber(phone);
                    userList.add(user);
                }
                setEmployeeNames();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
            }
        };

        ref.addListenerForSingleValueEvent(valueEventListener);
    }
}
