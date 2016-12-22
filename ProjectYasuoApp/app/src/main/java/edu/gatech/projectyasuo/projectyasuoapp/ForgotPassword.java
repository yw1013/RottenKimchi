package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.Random;

import android.app.ProgressDialog;
import android.os.AsyncTask;

import java.io.UnsupportedEncodingException;
import java.util.Properties;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

/**
 * ForgotPassword class that sends email to the user when user forget his/her password
 * once the user types in correct User Name and User Email
 * @author Yea Won Yoon
 */
public class ForgotPassword extends AppCompatActivity implements View.OnClickListener {

    private static final String username = "gtRottenKimchi@gmail.com";
    private static final String password = "fhxmsrlacl";
    private EditText userID;
    private EditText userEMAIL;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        ImageButton cancel = (ImageButton) findViewById(R.id.backbtn);
        ImageButton createAcc = (ImageButton) findViewById(R.id.createAcc);

        userID = (EditText) findViewById(R.id.userID);
        userEMAIL = (EditText) findViewById(R.id.userEMAIL);

        final TextInputLayout usernameWrapper = (TextInputLayout) findViewById(R.id.idWrapper);
        final TextInputLayout emailWrapper = (TextInputLayout) findViewById(R.id.emailWrapper);
        usernameWrapper.setHint("User ID");
        emailWrapper.setHint("Email");

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
            }
        });

        createAcc.setOnClickListener(this);
    }

    private static final String CHAR_LIST =
            "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
    private static final int RANDOM_STRING_LENGTH = 5;

    /**
     * Creates random password
     */
    private String generateRandomString(){
        StringBuilder randStr = new StringBuilder();
        for(int i=0; i<RANDOM_STRING_LENGTH; i++){
            int number = getRandomNumber();
            char ch = CHAR_LIST.charAt(number);
            randStr.append(ch);
        }
        return randStr.toString();
    }

    /**
     * Creates random number for random password
     */
    private int getRandomNumber() {
        Random randomGenerator = new Random();
        int randomInt = randomGenerator.nextInt(CHAR_LIST.length());
        if (randomInt - 1 == -1) {
            return randomInt;
        } else {
            return randomInt - 1;
        }
    }

    /**
     * Access the Gmail SMTP server
     */
    private Session createSessionObject() {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        return Session.getInstance(properties, new javax.mail.Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });
    }

    /**
     * Creates the message (new password)
     */
    private Message createMessage(String email, String subject, String messageBody, Session session) throws MessagingException, UnsupportedEncodingException {
        Message message = new MimeMessage(session);
        message.setFrom(new InternetAddress("gtRottenKimchi@gatech.edu", "RottenKimchiAdmin"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress(email, email));
        message.setSubject(subject);
        message.setText(messageBody);
        return message;
    }

    @Override
    public void onClick(View v) {
        UserList list = UserList.getUserList();
        User getUser = list.getUser(userEMAIL.getText().toString());

        if (list.existingEmail(userEMAIL.getText().toString())
                && userID.getText().toString().matches(getUser.getUserID())) {

            String newPW = generateRandomString();
            getUser.setUserPW(newPW);

            String email = getUser.getUserEmail();
            String subject = "New Password for RottenKimchi";
            String userIDMessage = userID.getText().toString();
            String newPWMessage = userIDMessage + ": " + newPW;
            sendMail(email, subject, newPWMessage);

            list.saveUser();

            startActivity(new Intent(ForgotPassword.this, LoginActivity.class));
            //Toast.makeText(getApplicationContext(), newPW, Toast.LENGTH_LONG).show();

        } else if (!list.existingEmail(userEMAIL.getText().toString())){
            Toast.makeText(getApplicationContext(), "Email Does Not Exist", Toast.LENGTH_SHORT).show();
        } else if (!userID.getText().toString().matches(getUser.getUserID())){
            Toast.makeText(getApplicationContext(), "Check Your User ID", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Sends email directly to the user by using AsyncTask in background
     * @author Yea Won Yoon
     */
    private class SendMailTask extends AsyncTask<Message, Void, Void> {
        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = ProgressDialog.show(ForgotPassword.this, "Please wait", "Sending mail", true, false);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressDialog.dismiss();
        }

        @Override
        protected Void doInBackground(Message... messages) {
            try {
                Transport.send(messages[0]);
            } catch (MessagingException e) {
            }
            return null;
        }
    }

    /**
     * Initiates the sending of an e-mail
     */
    private void sendMail(String email, String subject, String messageBody) {
        Session session = createSessionObject();
        try {
            Message message = createMessage(email, subject, messageBody, session);
            new SendMailTask().execute(message);
        } catch (AddressException | UnsupportedEncodingException e) {
        } catch (MessagingException e) {
        }
    }
}


