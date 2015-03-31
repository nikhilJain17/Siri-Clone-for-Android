package com.hello.learning.siriclone;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;


public class MainActivity extends Activity {

    TextView userWordsTV;       // TextView for holding the user's speech
    TextView computerWordsTV;   // TextView for holding the computer's reply

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the two TextViews
        userWordsTV = (TextView) findViewById(R.id.userWords);
        computerWordsTV = (TextView) findViewById(R.id.computerWords);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    // The onClick listener for the blue microphone button
    public void onBlueMicClick(View view) {


    // Listen for the inputs
    // Figure out what to do with them
           // eg. Call Joe, Text 333-3333, etc...




    }
}
