package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Spinner;
import android.widget.TextView;

public class EmployeeScheduleActivity extends AppCompatActivity {

    private Spinner dropDown;
    private TextView employeeNameTextView;
    private TextView scheduleTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_schedule);

        employeeNameTextView = (TextView) findViewById(R.id.employeeTextView);
        dropDown = (Spinner) findViewById(R.id.spinnerSchedule);
        scheduleTextView = (TextView) findViewById(R.id.scheduleTextView);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String firstName = b.getString("firstName");
        String lastName = b.getString("lastName");
        employeeNameTextView.setText("Employee:" + firstName + "  " + lastName);

        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

//    @Override
//    protected void onStart() {
//        String[] items =
//    }
}
