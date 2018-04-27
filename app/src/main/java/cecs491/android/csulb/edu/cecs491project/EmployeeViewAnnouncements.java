package cecs491.android.csulb.edu.cecs491project;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class EmployeeViewAnnouncements extends AppCompatActivity {

    private BottomNavigationView navigation;

    private TextView announcementOne;
    private TextView announcementTwo;
    private TextView announcementThree;
    private TextView announcementFour;
    private TextView announcementFive;


    private FirebaseDatabase db;
    private DatabaseReference ref;


    private void instantiateLayout(){
        announcementOne = findViewById(R.id.announcement_one_text);
        announcementTwo = findViewById(R.id.announcement_two_text);
        announcementThree = findViewById(R.id.announcement_three_text);
        announcementFour = findViewById(R.id.announcement_four_text);
        announcementFive = findViewById(R.id.announcement_five_text);
        navigation = findViewById(R.id.navigation);
    }

    private void handleNavMenu(){
        Menu menu = navigation.getMenu();
        MenuItem item = menu.getItem(3);
        item.setChecked(true);
        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Intent intent;
                switch (item.getItemId()) {
                    case R.id.navigation_home:
                        intent = new Intent(EmployeeViewAnnouncements.this, EmployeeHomePageActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_profile:
                        intent = new Intent(EmployeeViewAnnouncements.this, EmployeeProfileActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_schedule:
                        intent = new Intent(EmployeeViewAnnouncements.this, EmployeeScheduleActivity.class);
                        startActivity(intent);
                        return true;
                    case R.id.navigation_announcements:
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
        setContentView(R.layout.activity_employee_view_announcements);

        instantiateFirebase();
        instantiateLayout();
        handleNavMenu();
        fillAnnouncementsList();
    }

    private void instantiateFirebase(){
        db = FirebaseDatabase.getInstance();
        ref = db.getReference("Announcements");
    }

    private void setAnnouncements(List<Announcement> announcements){
        Announcement one = announcements.get(announcements.size()-1);
        Announcement two = announcements.get(announcements.size()-2);
        Announcement three = announcements.get(announcements.size()-3);
        Announcement four = announcements.get(announcements.size()-4);
        Announcement five = announcements.get(announcements.size()-5);

        announcementOne.setText(one.getMessage() + "\n\n" +
                "Posted by: " + one.getPostedBy() + "\n" + "Posted on: " + one.getTimestamp());
        announcementTwo.setText(two.getMessage() + "\n\n" +
                "Posted by: " + two.getPostedBy() + "\n" + "Posted on: " + two.getTimestamp());
        announcementThree.setText(three.getMessage() + "\n\n" +
                "Posted by: " + three.getPostedBy() + "\n" + "Posted on: " + three.getTimestamp());
        announcementFour.setText(four.getMessage() + "\n\n" +
                "Posted by: " + four.getPostedBy() + "\n" + "Posted on: " + four.getTimestamp());
        announcementFive.setText(five.getMessage() + "\n\n" +
                "Posted by: " + five.getPostedBy() + "\n" + "Posted on: " + five.getTimestamp());
    }
    private void fillAnnouncementsList(){
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Announcement> announcements = new ArrayList<>();
                if(dataSnapshot.hasChildren()){
                    for(DataSnapshot ds: dataSnapshot.getChildren()) {
                        Announcement ann = ds.getValue(Announcement.class);
                        announcements.add(ann);
                    }
                    setAnnouncements(announcements);
                }
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {}
        });
    }
}
