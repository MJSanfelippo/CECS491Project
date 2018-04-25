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
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EditScheduleSelection extends AppCompatActivity {

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
     * the back button
     */
    private Button backButton;

    /**
     * the navigation bar
     */
    private BottomNavigationView navigation;

    /**
     * the firebase database
     */
    private FirebaseDatabase db;

    /**
     * the database reference
     */
    private DatabaseReference ref;

    /**
     * the value event listener to get schedule info
     */
    private ValueEventListener valueEventListener;

    /**
     * list of users
     */
    private List<User> userList;

    /**
     * creates a bundle based on user
     * @param user the user to create the bundle from
     * @return the bundle containing user info
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
     * instantiate the layout components
     */
    private void instantiateLayout() {
        selectEmployee = findViewById(R.id.selectEmployeeSchedule);
        employeeNameSpinner = findViewById(R.id.employeeNameSpinnerSchedule);
        backButton = findViewById(R.id.employeeSelectionBackButtonSchedule);
        editPageButton = findViewById(R.id.toEditPageButtonSchedule);

        navigation = findViewById(R.id.navigationEmployer);

        setOnClickListeners();
    }

    /**
     * sets the on click listeners
     * if user clicks back, simply go back
     * if user clicks go, send them to the edit page with correct user info
     */
    private void setOnClickListeners(){
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(EditScheduleSelection.this, EmployerAdminActivity.class);
                startActivity(i);
            }
        });
        editPageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                User user = getUserFromSpinner();
                Intent i = new Intent(EditScheduleSelection.this, EditScheduleActivity.class); //TODO: I think this needs to be changed?
                Bundle b = getBundleFromUser(user);
                i.putExtras(b);
                startActivity(i);
            }
        });
    }

    /**
     * start the activity
     * @param savedInstanceState the previous activity state
      */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_schedule_selection);

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
                        intent = new Intent(EditScheduleSelection.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(EditScheduleSelection.this, EmployerProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EditScheduleSelection.this, EmployerScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
                        intent = new Intent(EditScheduleSelection.this, EmployerAdminActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }}
        );
    }

    /**
     * instantiate the firebase components
     */
    private void instantiateFirebase(){
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Users");
    }

    /**
     * get the user from the spinner, assume email is the same
     * @return the user chosen, null otherwise (never though)
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
     * set the employee names in the spinner
     */
    private void setEmployeeNames(){
        List<String> spinnerArray = new ArrayList<>();
        for (User user: userList){
            String fullName = user.getFirstName() + " " + user.getLastName() + " - " + user.getEmail();
            if (user.getDisabled().equalsIgnoreCase("false")) {
                spinnerArray.add(fullName);
            }
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(EditScheduleSelection.this, android.R.layout.simple_spinner_item, spinnerArray);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        employeeNameSpinner.setAdapter(adapter);
    }
    /**
     * get the users from the database and add to the list
     */
    private void addUsersToList() {
        userList = new ArrayList<User>();
        valueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot s: dataSnapshot.getChildren()){
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
