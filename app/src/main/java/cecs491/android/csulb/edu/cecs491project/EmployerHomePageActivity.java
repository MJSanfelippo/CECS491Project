package cecs491.android.csulb.edu.cecs491project;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

/**
 * Created by Michael on 3/3/2018.
 */

public class EmployerHomePageActivity extends Activity {

    private TextView test;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_employer_home_page);

        test = (TextView) findViewById(R.id.employeeTestTextView);
    }
}
