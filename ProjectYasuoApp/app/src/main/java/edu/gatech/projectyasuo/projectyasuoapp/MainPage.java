package edu.gatech.projectyasuo.projectyasuoapp;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

/**
 * MainPage that User is directed once logged in successfully
 * @author Jongwoo Jang
 */
public class MainPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        final User loggedUser = User.getLoggedInUser();

        super.onCreate(savedInstanceState);

        if (loggedUser.checkAdmin()) {
            setContentView(R.layout.activity_main_page_admin);



            TextView title = (TextView) findViewById(R.id.titleText);
            ImageButton movie = (ImageButton) findViewById(R.id.movieButton);
            ImageButton account = (ImageButton) findViewById(R.id.accountButton);
            ImageButton manage = (ImageButton)findViewById(R.id.manageBtn);
            ImageButton logout = (ImageButton) findViewById(R.id.logoutButton);

            Typeface billabong= Typeface.createFromAsset(getAssets(), "billabong.ttf");
            title.setTypeface(billabong);

            movie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainPage.this, MoviePage.class));
                }
            });

            account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainPage.this, AccountPage.class));
                }
            });


            manage.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainPage.this, ManageUsers.class));
                }
            });

            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSimplePopUp();
                }
            });

        } else {
            setContentView(R.layout.activity_main_page);


            TextView title = (TextView) findViewById(R.id.titleText);
            ImageButton movie = (ImageButton) findViewById(R.id.movieButton);
            ImageButton account = (ImageButton) findViewById(R.id.accountButton);
            ImageButton report = (ImageButton) findViewById(R.id.reportButton);
            ImageButton logout = (ImageButton) findViewById(R.id.logoutButton);

            Typeface billabong= Typeface.createFromAsset(getAssets(), "billabong.ttf");
            title.setTypeface(billabong);

            movie.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainPage.this, MoviePage.class));
                }
            });

            account.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainPage.this, AccountPage.class));
                }
            });

            report.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    startActivity(new Intent(MainPage.this, ReportPage.class));
                }
            });


            logout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    showSimplePopUp();
                }
            });

        }
    }

    /**
     * User Interface for logging out feature
     */
    private void showSimplePopUp() {

        AlertDialog.Builder helpBuilder = new AlertDialog.Builder(this);
        helpBuilder.setTitle("Confirmation");
        helpBuilder.setMessage("Click OK to log out.");
        helpBuilder.setPositiveButton("Cancel",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
        helpBuilder.setNegativeButton("Ok",
                new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int which) {
                        // Do nothing but close the dialog
                        finish();
                        startActivity(new Intent(MainPage.this, WelcomePage.class));
                    }
                });


                AlertDialog helpDialog = helpBuilder.create();
        helpDialog.show();
    }
}
