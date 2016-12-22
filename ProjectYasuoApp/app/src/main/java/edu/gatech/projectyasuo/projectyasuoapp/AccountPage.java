package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageButton;

/**
 * Show the logged in user id and email
 * @Author Jongwoo Jang
 */
public class AccountPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_page);
        ImageButton home = (ImageButton) findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(AccountPage.this, MainPage.class));
            }
        });

        TextView showID = (TextView) findViewById(R.id.userID);
        TextView showEmail = (TextView) findViewById(R.id.userEmail);
        ImageButton editProfile = (ImageButton) findViewById(R.id.editProfile);
        User loggedUser = User.getLoggedInUser();

        showID.setText(loggedUser.getUserID());
        showEmail.setText(loggedUser.getUserEmail());

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(AccountPage.this, EditProfile.class));
            }
        });
    }

}
