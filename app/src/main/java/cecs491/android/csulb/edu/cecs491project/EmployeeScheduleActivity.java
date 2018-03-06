package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;
//import android.icu.text.SimpleDateFormat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class EmployeeScheduleActivity extends AppCompatActivity {

    //private Spinner dropDown;
    private TextView employeeNameTextView;
    private TextView displayedWeekTextView;
    private TextView scheduleTextView;
    private Button backButton;
    private Button forwardButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employee_schedule);

        employeeNameTextView = (TextView) findViewById(R.id.employeeTextView);
        displayedWeekTextView = (TextView) findViewById(R.id.displayedWeekTextView);
        //dropDown = (Spinner) findViewById(R.id.spinnerSchedule);
        scheduleTextView = (TextView) findViewById(R.id.scheduleTextView);
        backButton = (Button) findViewById(R.id.backButton);
        forwardButton = (Button) findViewById(R.id.forwardButton);

        Intent i = getIntent();
        Bundle b = i.getExtras();
        String firstName = b.getString("firstName");
        String lastName = b.getString("lastName");
        employeeNameTextView.setText("Employee:" + firstName + "  " + lastName);

//        dropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            @Override
//            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> adapterView) {
//
//            }
//        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        Date current = Calendar.getInstance().getTime();
        Date begin = current;
        SimpleDateFormat dayOfWeek = new SimpleDateFormat("EEE", Locale.US);

        while (!dayOfWeek.format(begin).equals("SUN")) {
            //Date test = new DateTime(current).minusDays(1).toDate();
            begin = changeDate(begin, -1);
        }

        Date end = changeDate(begin, 6);
        SimpleDateFormat proper = new SimpleDateFormat("MM/DD/YYYY", Locale.US);
        displayedWeekTextView.setText(proper.format(begin) + " - " + proper.format(end));
    }

    private Date changeDate(Date date, int days) {
        Calendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);
        return cal.getTime();
    }
}
