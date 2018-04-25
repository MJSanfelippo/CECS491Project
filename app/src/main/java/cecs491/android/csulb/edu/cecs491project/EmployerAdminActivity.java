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
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by pbour on 3/16/2018.
 */

public class EmployerAdminActivity extends AppCompatActivity {

    /**
     * the shift button reference
     */
    private Button shiftButton;

    /**
     * the employee button reference
     */
    private Button employeeButton;

    /**
     * the announcements button reference
     */
    private Button announcementsButton;

    /**
     * the bottom nav view reference
     */
    private BottomNavigationView navigation;

    /**
     * instantiate all layout components
     */
    private void instantiateLayout(){
        shiftButton = findViewById(R.id.editShiftButton);
        employeeButton = findViewById(R.id.editEmployeeButton);
        announcementsButton = findViewById(R.id.editAnnouncementsButton);

        shiftButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployerAdminActivity.this, EditScheduleSelection.class);
                startActivity(i);

            }
        });

        employeeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployerAdminActivity.this, EditEmployeeSelection.class);
                startActivity(i);
            }
        });

        announcementsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(EmployerAdminActivity.this, PostAnnouncementActivity.class);
                startActivity(i);
            }
        });

        navigation = findViewById(R.id.navigationEmployer);
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
                        intent = new Intent(EmployerAdminActivity.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(EmployerAdminActivity.this, EmployerProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EmployerAdminActivity.this, EmployerScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
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
        setContentView(R.layout.activity_employer_admin);

        instantiateLayout();
        handleNavMenu();
    }

    @Override
    protected void onResume(){
        super.onResume();
        handleNavMenu();
    }
}