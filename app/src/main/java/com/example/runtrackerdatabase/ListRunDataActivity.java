package com.example.runtrackerdatabase;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class ListRunDataActivity extends AppCompatActivity {

    private static final String TAG = "ListRunDataActivity";
    DatabaseHelper mDatabaseHelper;

    private ListView mListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_run_data);

        // Gets reference to listview and DatabaseHelper
        mListView = (ListView) findViewById(R.id.runListView);
        mDatabaseHelper = new DatabaseHelper(this);

        // calls method to fill values in the ListView
        populateListView();
    }

    private void populateListView() {
        Log.d(TAG, "populateListView: Displaying data in the Listview");

        // get the data and append to a list
        Cursor data = mDatabaseHelper.getData();

        // Creates an arraylist to store all the String display from database elements

        // TO-DO
        // Return back to this code and make it an ArrayList of RunEntry Objects and then pull
        // out all the elements, call the object constructor, and add the object to the Arraylist

        ArrayList<String> runData = new ArrayList<>();
        while (data.moveToNext()) {
            // get the value from the database in column 1 (name) and the value in column 2 (dist)
            // then add it to the ArrayList
             runData.add(data.getString(1) + ", " + data.getString(2) + " miles");     // Name of runner
        }

        // Create the list adapter and set the adapter to this ArrayList
        ListAdapter adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, runData);
        mListView.setAdapter(adapter);

        // set an onItemClickListener to the Listview to respond to user interaction
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                // get the index of the entry that was clicked on
                String name = adapterView.getItemAtPosition(i).toString();
                Log.d(TAG, "onItemClick: You clicked on " + name);

                String shortName = "";
                // strip off characters after the ',' that aren't the name
                // once the ArrayList contains RunEntry objects, this won't be needed
                int loc = name.indexOf(",");
                if (loc != -1)
                    shortName = name.substring(0, loc);
                else
                    shortName = name;


                // get the unique id associated with that name
                Cursor data = mDatabaseHelper.getItemID(shortName);
                int itemID = -1;

                while (data.moveToNext()) {
                    itemID = data.getInt(0);
                }

                // if data is returned, Log it and then create an Intent to edit the entry with this name and id
                // If not found, send an error message via a toast

                if (itemID > -1) {
                    Log.d(TAG, "onItemClick: The ID is: " + itemID);
                    Intent intent = new Intent(ListRunDataActivity.this, EditRunDataActivity.class);
                    intent.putExtra(EditRunDataActivity.ID, itemID);
                    intent.putExtra(EditRunDataActivity.NAME, shortName);
                    startActivity(intent);
                }
                else {
                    toastMessage("No ID associated with that name");
                }
            }
        });


    }

    // Takes user back to home screen to enter a new run
    public void onClickAddNewRun(View v) {
        Intent intent = new Intent(ListRunDataActivity.this, MainActivity.class);
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
