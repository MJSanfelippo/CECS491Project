package cecs491.android.csulb.edu.cecs491project;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

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

    private void instantiateLayout() {
        selectEmployee = findViewById(R.id.selectEmployee);
        employeeNameSpinner = findViewById(R.id.employeeNameSpinner);
        editPageButton = findViewById(R.id.toEditPageButton);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_employee_selection);

        instantiateLayout();
    }

    private void setEmployeeNames() {

    }
}
