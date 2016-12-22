package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import android.widget.ArrayAdapter;
import android.widget.Spinner;

/**
 * Registration class that allows Users to Register and save Users data
 * into our database.
 *
 * @author Jongwoo Jang
 */
public class Registration extends AppCompatActivity implements View.OnClickListener {
    private EditText userID;
    private EditText userPW;
    private EditText userEMAIL;
    private EditText userNickname;
    private EditText userAge;
    private Spinner userMajorSpinner;
    private Spinner userStandingSpinner;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userMajorSpinner = (Spinner) findViewById(R.id.userMajor_spinner);
        userStandingSpinner = (Spinner) findViewById(R.id.userStanding_spinner);

        ArrayAdapter<CharSequence> majorAdapter = ArrayAdapter.createFromResource(this, R.array.major_arrays, android.R.layout.simple_spinner_item);
        ArrayAdapter<CharSequence> standingAdapter = ArrayAdapter.createFromResource(this, R.array.standing_arrays, android.R.layout.simple_spinner_item);

        majorAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        standingAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        userMajorSpinner.setAdapter(majorAdapter);
        userStandingSpinner.setAdapter(standingAdapter);

        userID = (EditText) findViewById(R.id.userID);
        userPW = (EditText) findViewById(R.id.userPW);
        userEMAIL = (EditText) findViewById(R.id.userEMAIL);
        userNickname = (EditText) findViewById(R.id.userNickname);
        userAge = (EditText) findViewById(R.id.userAge);

        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.idWrapper);
        final TextInputLayout pwWrapper = (TextInputLayout) findViewById(R.id.pwWrapper);
        final TextInputLayout emailWrapper = (TextInputLayout) findViewById(R.id.emailWrapper);
        final TextInputLayout nicknameWrapper = (TextInputLayout) findViewById(R.id.nicknameWrapper);
        final TextInputLayout ageWrapper = (TextInputLayout) findViewById(R.id.ageWrapper);

        usernameWrapper.setHint("User ID");
        pwWrapper.setHint("Password");
        emailWrapper.setHint("Email");
        nicknameWrapper.setHint("Nickname");
        ageWrapper.setHint("Age");

        ImageButton backbtn = (ImageButton) findViewById(R.id.backbtn);
        ImageButton createAcc = (ImageButton) findViewById(R.id.createAcc);

        TextView appname = (TextView) findViewById(R.id.appname);
        Typeface billabong = Typeface.createFromAsset(getAssets(), "billabong.ttf");
        appname.setTypeface(billabong);
        TextView tvSignin = (TextView) findViewById(R.id.tvSignin);

        createAcc.setOnClickListener(this);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Registration.this, WelcomePage.class));
            }
        });

        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(Registration.this, LoginActivity.class));
            }
        });
    }

    /**
     * check info if its null
     * @return true if its not null else false
     */
    public boolean checkNull() {
        return (!userID.getText().toString().equals("")) &&
                (!userPW.getText().toString().equals("")) &&
                (!userEMAIL.getText().toString().equals("")) &&
                (!userNickname.getText().toString().equals("")) &&
                (!userMajorSpinner.getSelectedItem().toString().equals("Select your major")) &&
                (!userStandingSpinner.getSelectedItem().toString().equals("Select your class standing")) &&
                (!userAge.getText().toString().equals(""));
    }

    /**
     * check info if its valid
     * @return true if valid else false
     */
    public boolean checkInfo() {
        return userID.getText().toString().length() > 4 && userEMAIL.getText().toString().endsWith("@gatech.edu") &&
                !UserList.getUserList().existingEmail(userEMAIL.getText().toString()) && userPW.getText().toString().length() > 4 &&
                !userMajorSpinner.getSelectedItem().toString().equals("Select your major") & !userStandingSpinner.getSelectedItem().toString().equals("Select your class standing") &&
                userAge.getText().toString().matches("\\d+") && userAge.getText().toString().length() < 3;
    }
    @Override
    public void onClick(View v) {
        User user;
        String userMajor = String.valueOf(userMajorSpinner.getSelectedItem());
        String userStanding = String.valueOf(userStandingSpinner.getSelectedItem());
        UserList list = UserList.getUserList();
        if (checkNull()) {
            //check if userName has minimum 5 characters
            //*****Check if userID only contains letters and numbers
            if (userID.getText().toString().length() < 5) {
                Toast.makeText(getApplicationContext(),
                        "User ID should be minimum 5 characters", Toast.LENGTH_SHORT).show();
            }
            //check if userPW has minimum 5 characters
            if (userPW.getText().toString().length() < 5) {
                Toast.makeText(getApplicationContext(),
                        "Password should be minimum 5 characters", Toast.LENGTH_SHORT).show();
            }
            //check if the email is GT email
            if (!userEMAIL.getText().toString().endsWith("@gatech.edu")) {
                Toast.makeText(getApplicationContext(),
                        "Email must be Georgia Tech email", Toast.LENGTH_SHORT).show();
            }
            //check if email is already used
            if (list.existingEmail(userEMAIL.getText().toString())) {
                Toast.makeText(getApplicationContext(),
                        "There is already an account with this Email", Toast.LENGTH_SHORT).show();
            }
            //check if the age is integer and a valid age
            if (!userAge.getText().toString().matches("\\d+") ||
                    userAge.getText().toString().length() > 2) {
                Toast.makeText(getApplicationContext(),
                        "Not a valid age", Toast.LENGTH_SHORT).show();
            }

            //*****userGender

            if (checkInfo()) {
                Toast.makeText(getApplicationContext(), "Creating...", Toast.LENGTH_SHORT).show();

                user = new User(userID.getText().toString(), userPW.getText().toString(), userEMAIL.getText().toString(),
                        userNickname.getText().toString(), userAge.getText().toString(), userMajor, userStanding, "true", "0");
                //user.setPermission("true");
                list.addUser(user);

                finish();
                startActivity(new Intent(Registration.this, LoginActivity.class));
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "One or more fields are empty", Toast.LENGTH_SHORT).show();
        }
    }
}