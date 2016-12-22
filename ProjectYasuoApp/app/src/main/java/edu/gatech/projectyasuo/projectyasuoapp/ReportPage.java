package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageButton;
import android.net.Uri;
import android.util.Log;
import android.view.Menu;
import android.widget.Button;
import android.widget.Toast;

/**
 * ReportPage class that allows Users to send email to Admin
 * @author Yoon Yea Won
 */
public class ReportPage extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_report_page);
        ImageButton home = (ImageButton) findViewById(R.id.homeButton);
        home.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                startActivity(new Intent(ReportPage.this, MainPage.class));
            }
        });
        Button startBtn = (Button) findViewById(R.id.sendEmail);
        startBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                startActivity(new Intent(ReportPage.this, ReportPage.class));
                sendEmail();
            }
        });
    }

    /**
     * Send email to our admin email address
     */
    private void sendEmail() {
        Log.i("Send Email", "");
        String[] to = {"gtRottenKimchi@gmail.com"};
        Intent emailIntent = new Intent(Intent.ACTION_SEND);

        emailIntent.setData(Uri.parse("mailto:"));
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, to);
        emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Subject");
        emailIntent.putExtra(Intent.EXTRA_TEXT, "Message");
        emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        try {
            startActivity(Intent.createChooser(emailIntent, "Send mail"));
            finish();
            Log.i("Finished Sending Email", "");
        }
        catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(ReportPage.this, "There Is No Email Client Installed.", Toast.LENGTH_SHORT).show();
        }
        //startActivity(new Intent(ReportPage.this, ReportPage.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
        return true;
    }
}
