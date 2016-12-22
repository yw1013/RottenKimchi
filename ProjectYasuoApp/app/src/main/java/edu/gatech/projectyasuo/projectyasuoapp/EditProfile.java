package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Implementation of Profile Editing
 * @author Jongwoo Jang
 */
public class EditProfile extends AppCompatActivity implements View.OnClickListener {
    private Spinner userMajorSpinner;
    private Spinner userStandingSpinner;
    private EditText userID, userPW, userNickname, userAge;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        userMajorSpinner = (Spinner) findViewById(R.id.userMajor_spinner);
        userStandingSpinner = (Spinner) findViewById(R.id.userStanding_spinner);

        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(this, R.array.major_arrays, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> standingAdapter = ArrayAdapter.createFromResource(this, R.array.standing_arrays, android.R.layout.simple_spinner_item);

        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        standingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        userMajorSpinner.setAdapter(majorAdapter);
        userStandingSpinner.setAdapter(standingAdapter);

        userMajorSpinner.setSelection(majorAdapter.getPosition(User.getLoggedInUser().getUserMajor()));
        userStandingSpinner.setSelection(standingAdapter.getPosition(User.getLoggedInUser().getUserStanding()));

        userID = (EditText) findViewById(R.id.userID);
        userPW = (EditText) findViewById(R.id.userPW);
        userNickname = (EditText) findViewById(R.id.userNickname);
        userAge = (EditText) findViewById(R.id.userAge);

        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.idWrapper);
        final TextInputLayout nicknameWrapper = (TextInputLayout) findViewById(R.id.nicknameWrapper);
        final TextInputLayout ageWrapper = (TextInputLayout) findViewById(R.id.ageWrapper);

        usernameWrapper.setHint("User ID");
        nicknameWrapper.setHint("Nickname");
        ageWrapper.setHint("Age");

        userID.setText(User.getLoggedInUser().getUserID());
        userNickname.setText(User.getLoggedInUser().getUserNickname());
        userAge.setText(User.getLoggedInUser().getUserAge());

        ImageButton backbtn = (ImageButton) findViewById(R.id.backbtn);
        ImageButton createAcc = (ImageButton) findViewById(R.id.createAcc);
        TextView changePW = (TextView) findViewById(R.id.changePW);

        TextView appname = (TextView) findViewById(R.id.appname);
        Typeface billabong= Typeface.createFromAsset(getAssets(), "billabong.ttf");
        appname.setTypeface(billabong);
        createAcc.setOnClickListener(this);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(EditProfile.this, MainPage.class));
            }
        });

        changePW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(EditProfile.this, ChangePassword.class));
            }
        });
    }

    @Override
    public void onClick(View v) {
        User loggedUser = User.getLoggedInUser();
        UserList list = UserList.getUserList();
        String userMajor = String.valueOf(userMajorSpinner.getSelectedItem());
        String userStanding = String.valueOf(userStandingSpinner.getSelectedItem());

        if (userID.getText().toString().length() < 5) {
            Toast.makeText(getApplicationContext(),
                    "User ID Should Be Minimum 5 Characters", Toast.LENGTH_SHORT).show();
        }

        if (!userAge.getText().toString().matches("\\d+") ||
                userAge.getText().toString().length() > 2) {
            Toast.makeText(getApplicationContext(),
                    "Not a Valid Age", Toast.LENGTH_SHORT).show();
        }

        if (userID.getText().toString().length() > 4 &&
                userAge.getText().toString().matches("\\d+") && userAge.getText().toString().length() < 3 &&
                userPW.getText().toString().equals(loggedUser.getUserPW())) {
            loggedUser.setUserID(userID.getText().toString());
            loggedUser.setUserNickname(userNickname.getText().toString());
            loggedUser.setUserAge(userAge.getText().toString());
            loggedUser.setUserMajor(userMajor);
            loggedUser.setUserStanding(userStanding);
            list.saveUser();

            finish();
            startActivity(new Intent(EditProfile.this, AccountPage.class));
        } else {
            Toast.makeText(getApplicationContext(), "User Password Required to Edit Profile", Toast.LENGTH_SHORT).show();
        }
    }
}
