package edu.gatech.projectyasuo.projectyasuoapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

/**
 * Implementation of user detail
 * @author Gyung Yoon Yoo
 */

public class userDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        final UserList users = UserList.getUserList();
        int position = getIntent().getIntExtra("position", 1);
        final User u = users.getUserAt(position);

        TextView emailText = (TextView)findViewById(R.id.emailText);
        emailText.setText("User Email: " + u.getUserEmail());

        TextView idText = (TextView)findViewById(R.id.idText);
        idText.setText("User ID: " + u.getUserID());

        TextView majorText = (TextView) findViewById(R.id.majorText);
        majorText.setText("User Major: " + u.getUserMajor());

        TextView classText = (TextView) findViewById(R.id.classText);
        classText.setText("User Class: " + u.getUserStanding());

        CheckBox lockBox = (CheckBox) findViewById(R.id.locked);
        if (u.getPermission().equals("true")){
            lockBox.setChecked(false);

        } else {
            lockBox.setChecked(true);
        }

//        ListView listView = (ListView) findViewById(R.id.listView);
//        listView.setAdapter(new MyAdapter(this, R.layout.manage_user, R.id.textView6, userHistory));


        lockBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    u.setPermission("false");
                    users.saveUser();
                } else {
                    u.setPermission("true");
                    users.saveUser();
                }
            }
        });


    }
}
