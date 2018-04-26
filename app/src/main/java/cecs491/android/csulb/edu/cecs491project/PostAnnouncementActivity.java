package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by phannachrin on 4/24/18.
 */

public class PostAnnouncementActivity extends AppCompatActivity {

    /**
     * Edit text field for user input.
     */
    private EditText announcementEditText;

    /**
     * Button for posting and canceling announcement.
     */
    private Button postButton;
    private Button cancelButton;

    /**
     * Reference to the bottom navigation bar.
     */
    private BottomNavigationView navigation;

    /**
     * Firebase database.
     */
    private FirebaseDatabase db;
    /**
     * Firebase authorization.
     */
    private FirebaseAuth auth;
    /**
     * Firebase database reference.
     */
    private DatabaseReference announcementRef;
    private DatabaseReference userRef;

    /**
     * Fields that holds the user ID and user's full name.
     */
    private String userID;
    private String userName;

    /**
     * Instantiate all firebase components.
     */
    private void instantiateFirebase() {
        db = FirebaseDatabase.getInstance();
        auth = FirebaseAuth.getInstance();
        userID = auth.getUid();
        announcementRef = db.getReference("Announcements");
        userRef = db.getReference("Users");
    }
    /**
     * Instantiate all the layout components.
     */
    private void instantiateLayout(){
        announcementEditText = findViewById(R.id.announcementEditText);
        postButton = findViewById(R.id.postButton);
        cancelButton = findViewById(R.id.cancelButton);
        navigation = findViewById(R.id.navigation);
        postButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newAnnouncement = announcementEditText.getText().toString();
                Calendar c = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("MM-dd-yyyy KK:mm:ss");
                sdf.setTimeZone(TimeZone.getTimeZone("America/Los_Angeles"));
                String timeStamp = sdf.format(c.getTime());
                announcementRef.child(timeStamp).child("message").setValue(newAnnouncement);
                announcementRef.child(timeStamp).child("postedBy").setValue(userName);
                announcementRef.child(timeStamp).child("timestamp").setValue(timeStamp);
                postSuccessful();
            }
        });
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                Intent i = new Intent(PostAnnouncementActivity.this, EmployerAdminActivity.class);
                startActivity(i);
            }
        });
    }
    /**
     * Method that gets the user full name and sets it.
     */
    private void setUserName(){
        DatabaseReference ref = userRef.child(userID);
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                String firstName = dataSnapshot.child("First Name").getValue().toString();
                String lastName = dataSnapshot.child("Last Name").getValue().toString();
                userName = firstName + " " + lastName;
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }

    /**
     * Method that handle user's input on the navigation bar.
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
                        intent = new Intent(PostAnnouncementActivity.this, EmployerHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(PostAnnouncementActivity.this, EmployerProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(PostAnnouncementActivity.this, EmployerScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
                        return true;
                    case R.id.navigation_admin:
                        intent = new Intent(PostAnnouncementActivity.this, EmployerAdminActivity.class);
                        startActivity(intent);
                        return true;
                }
                return false;
            }}
        );
    }

    /**
     * Methods that shows a pop up dialog box and clear the announcement edit text field after a successful post.
     */
    private void postSuccessful(){
        AlertDialog alertDialog = new AlertDialog.Builder(PostAnnouncementActivity.this).create();
        alertDialog.setMessage("Post Successful.");
        alertDialog.show();
        Intent i = new Intent(PostAnnouncementActivity.this, PostAnnouncementActivity.class);
        startActivity(i);
    }
    /**
     * instantiate all components of the activity
     * @param savedInstanceState the saved instance state
     */
    @Override
    protected void onCreate (Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_announcement);
        instantiateFirebase();
        instantiateLayout();
        setUserName();
        handleNavMenu();
    }

}