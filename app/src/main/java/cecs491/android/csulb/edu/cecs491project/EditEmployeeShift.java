package cecs491.android.csulb.edu.cecs491project;

import android.app.TimePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;

/**
 * Created by phannachrin on 4/22/18.
 */

public class EditEmployeeShift extends AppCompatActivity {


    private TextView dateTextView;
    private TextView dayOfWeekTextView;

    private EditText startTimeEditTextView;
    private EditText endTimeEditTextView;

    private Button editButton;
    private Button cancelButton;


    private void instantiateLayout(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee_shift);

        instantiateLayout();
        //instantiateFirebase();
        //setEmployeeNames();
        //handleNavMenu();
    }

}
