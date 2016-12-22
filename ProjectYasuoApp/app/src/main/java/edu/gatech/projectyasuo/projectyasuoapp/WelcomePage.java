package edu.gatech.projectyasuo.projectyasuoapp;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;

import java.io.File;

/**
 * Welcome Page of the Application,
 * it initializes the path to our database text file, and
 * load it into our HashMap on create.
 * @author Jongwoo Jang
 */
public class WelcomePage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_page);

        ImageButton toLogin = (ImageButton) findViewById(R.id.loginbtn);
        ImageButton toReg = (ImageButton) findViewById(R.id.regbtn);

        File path = getFilesDir();
        if(path.exists()) {
            UserList.initPath(path.getAbsolutePath() + "/dbUser");
            MovieList.initPath(path.getAbsolutePath() + "/dbMovie");
            RatingList.initPath(path.getAbsolutePath() + "/dbRating");
        }
        UserList.getUserList().loadUser();
        MovieList.getMovieList().loadMovie();
        RatingList.getRatingList().loadRates();
        MovieList.getMovieList().setAvgCriticsRate();


        // INITIALIZE ADMINS HERE SINCE WE DO NOT HAVE ANY BETTER DATABASE IMPLEMENTATION
        UserList.getUserList().unAdmin();
        UserList.getUserList().initAdmin("test10@gatech.edu");
        UserList.getUserList().initAdmin("jjang74@gatech.edu");
        ////////////////////////////////////////////////////////////////////////////////

        toLogin.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(WelcomePage.this, LoginActivity.class));
            }
        });

        toReg.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                finish();
                startActivity(new Intent(WelcomePage.this, Registration.class));
            }
        });

    }

}
