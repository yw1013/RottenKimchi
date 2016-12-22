package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Implementation of Login Activity
 * @author Jongwoo Jang
 */
public class LoginActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText userEmail;
    private EditText password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        userEmail = (EditText) findViewById(R.id.userEmail);
        password = (EditText) findViewById(R.id.pw);

        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.usernameWrapper);
        final TextInputLayout pwWrapper = (TextInputLayout) findViewById(R.id.pwWrapper);
        usernameWrapper.setHint("Email");
        pwWrapper.setHint("Password");

        ImageButton loginbtn = (ImageButton) findViewById(R.id.loginbtn);
        ImageButton backbtn = (ImageButton) findViewById(R.id.backbtn);

        TextView appname = (TextView) findViewById(R.id.appname);
        Typeface billabong = Typeface.createFromAsset(getAssets(), "billabong.ttf");
        appname.setTypeface(billabong);
        TextView register = (TextView) findViewById(R.id.register);
        TextView forgotPW = (TextView) findViewById(R.id.forgotPW);

        loginbtn.setOnClickListener(this);

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this, WelcomePage.class));
            }
        });

        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this, Registration.class));
            }
        });

        forgotPW.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(LoginActivity.this, ForgotPassword.class));
            }
        });
    }


    @Override
    public void onClick(View v) {
        UserList user = UserList.getUserList();
        if (user.existingEmail(userEmail.getText().toString())) {
            User pullUser = user.getUser(userEmail.getText().toString());
            if (password.getText().toString().equals(pullUser.getUserPW())) {
                if (pullUser.getPermission().equals("true")) {
                    Toast.makeText(getApplicationContext(), "Login Successful!", Toast.LENGTH_SHORT).show();
                    User.initloggedInUser(pullUser);
                    startActivity(new Intent(LoginActivity.this, MainPage.class));
                } else {
                    Toast.makeText(getApplicationContext(), "Login Failed. Your account has been disabled.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(getApplicationContext(), "Login Failed. Please check your password.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(getApplicationContext(), "Login Failed. Please check your Email.", Toast.LENGTH_SHORT).show();
        }
    }
}
