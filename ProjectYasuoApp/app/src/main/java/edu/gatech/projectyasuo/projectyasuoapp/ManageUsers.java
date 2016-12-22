package edu.gatech.projectyasuo.projectyasuoapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import java.util.List;
/**
 * Implementation of Manage Users
 * @author Gyung Yoon Yoo
 */

public class ManageUsers extends AppCompatActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_users);

        ImageButton button = (ImageButton) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                startActivity(new Intent(ManageUsers.this, MainPage.class));
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);

        listView.setOnItemClickListener(this);

        List<User> mgr = UserList.getUserList().getItemArray(UserList.getUserList().regiUser);

        listView.setAdapter(new MyAdapter(this, R.layout.manage_user, R.id.textView6, mgr));

    }

    /**
     * Method implemented to get the clicked item's position
     * and get user detail of corresponding item.
     *
     */

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Intent i = new Intent(ManageUsers.this, userDetail.class);
        i.putExtra("position", position);
        startActivity(i);
    }
    /**
     * Private inner class of adapter to use it in listView design
     */
    private class MyAdapter extends ArrayAdapter<User>{

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View view = super.getView(position, convertView, parent);
            //Here we provide the mapping from our object data to the list layout elements
            User item = getItem(position);
            assert item != null;
            // Get the textView
            TextView tv = (TextView) view.findViewById(R.id.textView6);
            tv.setText(item.getUserEmail());
            assert tv != null;
            // Set Image to the left of the Text View
            //set the checkbox value

            return view;

        }
        // Constructor of ArrayAdapter
        public MyAdapter(Context context, int resource, int textViewResourceId, List<User> objects) {
            super(context, R.layout.manage_user, textViewResourceId, objects);
        }

        @Override
        public boolean isEnabled(int position)
        {
            return true;
        }
    }
}