package cecs491.android.csulb.edu.cecs491project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import android.widget.ToggleButton;

/**
 * Created by Michael on 3/3/2018.
 * editied by Peter 3/4/2018 - ongoing...
 */

public class EmployeeHomePageActivity extends AppCompatActivity {

    private TextView mTextMessage;
    private ToggleButton clockInButton;
    private ToggleButton breakButton;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    mTextMessage.setText(R.string.title_home);
                    return true;
                case R.id.navigation_profile:
                    mTextMessage.setText(R.string.title_profile);
                    return true;
                case R.id.navigation_schedule:
                    mTextMessage.setText(R.string.title_schedule);
                    Intent i = new Intent(EmployeeHomePageActivity.this, EmployeeScheduleActivity.class);
                    startActivity(i);
                    return true;
                case R.id.navigation_announcements:
                    mTextMessage.setText(R.string.title_announcement);
                    return true;
            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_home_page);

        mTextMessage = (TextView) findViewById(R.id.employeeTestTextView);
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        clockInButton = (ToggleButton) findViewById(R.id.toggleClockButton);
        breakButton = (ToggleButton) findViewById(R.id.breakButton);
        clockInButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    clockInButton.setTextOn("Clock out");
                    breakButton.setEnabled(true);
                } else {
                    clockInButton.setTextOff("Clock in");
                    breakButton.setEnabled(false);

                }
            }
        });
        breakButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if (isChecked){
                    breakButton.setTextOn("Start break");
                    clockInButton.setEnabled(true);
                } else {
                    breakButton.setTextOff("Stop break");
                    clockInButton.setEnabled(false);
                }
            }
        });
    }
}
