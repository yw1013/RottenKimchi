package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

/**
 * Implementation of Changing Password
 * @author Yea Won Yoon
 */
public class ChangePassword extends AppCompatActivity implements View.OnClickListener {
    private EditText oldPW, newPW, checkPW;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        ImageButton changePW = (ImageButton) findViewById(R.id.changePW);
        ImageButton backbtn = (ImageButton) findViewById(R.id.backbtn);

        oldPW = (EditText) findViewById(R.id.oldPW);
        newPW = (EditText) findViewById(R.id.newPW);
        checkPW = (EditText) findViewById(R.id.checkPW);

        changePW.setOnClickListener(this);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
                startActivity(new Intent(ChangePassword.this, EditProfile.class));
            }
        });

    }

    @Override
    public void onClick(View v) {
        User loggedUser = User.getLoggedInUser();
        UserList list = UserList.getUserList();
        if (!newPW.getText().toString().equals("") && !checkPW.getText().toString().equals("")) {
            if (newPW.getText().toString().length() < 5) {
                Toast.makeText(getApplicationContext(),
                        "Password Should Be Minimum 5 Characters", Toast.LENGTH_SHORT).show();
            }
            if (newPW.getText().toString().length() > 4 &&
                    oldPW.getText().toString().equals(loggedUser.getUserPW()) &&
                    newPW.getText().toString().equals(checkPW.getText().toString())) {
                loggedUser.setUserPW(newPW.getText().toString());
                list.saveUser();

                finish();
                Toast.makeText(getApplicationContext(), "Successfully Changed Password", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(ChangePassword.this, EditProfile.class));
            } else if (!oldPW.getText().toString().equals(loggedUser.getUserPW())) {
                Toast.makeText(getApplicationContext(), "Password Incorrect", Toast.LENGTH_SHORT).show();
            } else if (!newPW.getText().toString().equals(checkPW.getText().toString())) {
                Toast.makeText(getApplicationContext(), "Your Password Differ", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(),
                    "Please Enter Your New Password", Toast.LENGTH_SHORT).show();
        }
    }
}
