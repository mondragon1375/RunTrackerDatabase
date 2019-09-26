package com.example.runtrackerdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * This is an example on how to use an SQLite database to save locally on your Android device
 * Each Activity uses a constant called "TAG" which is the name of the activity for debugging purposes
 * I referred to the two following two YouTube videos by CodingByMitch when making this app.
 *
 * https://youtu.be/aQAIMY-HzL8
 * https://www.youtube.com/watch?v=nY2bYJyGty8
 *
 * I then added on to it to create a Run tracker app to allow people to track their runs.
 *
 * Ultimately I want to have the data pull and refresh and display as RunEntry objects and
 * have a drop down menu for sorting by fastest pace and by longest run
 */

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";

    DatabaseHelper mDatabaseHelper;
    private Button btnAdd, btnView;
    private EditText nameEditText, distanceEditText, timeEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create references to all view elements in xml file

        btnAdd = (Button) findViewById(R.id.addRunButton);
        btnView = (Button) findViewById(R.id.viewRunButton);
        nameEditText = (EditText) findViewById(R.id.name);
        distanceEditText = (EditText) findViewById(R.id.distance);
        timeEditText = (EditText) findViewById(R.id.time);

        // reference to DatabaseHelper object that will be used
        mDatabaseHelper = new DatabaseHelper(this);
    }

    public void onClickAddButton(View v) {

        // Gets data the user entered
        String name = nameEditText.getText().toString();
        String distance = distanceEditText.getText().toString();
        String time = timeEditText.getText().toString();

        Log.d(TAG, "onClickAddButton: " + name + ", " + distance + ", " + time );

        // Make sure that none of the fields are empty
        if (name.length()!= 0 && distance.length() != 0 && time.length() != 0) {

            addData(name, distance, time);

            // clears the edit text fields in case more data entry follows
            nameEditText.setText("");
            distanceEditText.setText("");
            timeEditText.setText("");
        }
        else
            toastMessage("Please fill in all text fields");
    }


    public void addData(String name, String dist, String time) {
        boolean insertData = mDatabaseHelper.addData(name, dist, time);

        if(insertData) {
            toastMessage("Data inserted successfully");
        }
        else
            toastMessage("Something went wrong");
    }

    // This method will load the page with the ListView that shows all the values in the database
    public void onClickViewButton(View v) {
        Intent intent = new Intent(MainActivity.this, ListRunDataActivity.class);
        startActivity(intent);
    }


    /**
     * Customizable toast message
     * @param message
     */

    private void toastMessage(String message){
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}
