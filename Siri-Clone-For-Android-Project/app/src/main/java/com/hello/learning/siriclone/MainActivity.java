package com.hello.learning.siriclone;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import ai.api.AIConfiguration;
import ai.api.AIListener;
import ai.api.AIService;
import ai.api.GsonFactory;
import ai.api.model.AIError;
import ai.api.model.AIResponse;
import ai.api.model.Result;



public class MainActivity extends Activity implements AIListener {

    TextView userWordsTV;       // TextView for holding the user's speech
    TextView computerWordsTV;   // TextView for holding the computer's reply

    private AIService aiService;    // Speech Listener object

    // Config object - speech listening
    final AIConfiguration config = new AIConfiguration("0fe4c65a0ef94336b7529efa4f2882a5 ",
            "5bd76f3b-b0e2-438f-a093-0c2e681a92dd ", AIConfiguration.SupportedLanguages.English,
            AIConfiguration.RecognitionEngine.Google);




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize the two TextViews
        userWordsTV = (TextView) findViewById(R.id.userWords);
        computerWordsTV = (TextView) findViewById(R.id.computerWords);

        // Hide the ActionBar - we won't be needing it as our UI is very simple
        ActionBar actionBar = getActionBar();
        actionBar.hide();

        // Initialize the speech listeners
        aiService = AIService.getService(this, config);
        aiService.setListener(this);
    }



    // The onClick listener for the blue microphone button
    public void onBlueMicClick(View view) {

        // Reset both text boxes for a new query
        computerWordsTV.setText("");
        userWordsTV.setText("");

    // Listen for the inputs
    // Figure out what to do with them

        // Listen for the inputs
        aiService.startListening();

        // Next function that is called is onResult
        // That is a callback function which validates the inputs and turns it into text.







    }


    // Handles the response
    // Specifically, it turns the speech to text and returns the text, making sure there are no errors
    // It does NOT determine what the user wanted to do (call, text, etc)
    @Override
    public void onResult(AIResponse response) {

        // If all is well, do this!
        if (!(response.isError())) {

            // This holds all the goods
            Result result = response.getResult();
            String userSpeechStr = result.getResolvedQuery();

            Log.i("User speech: ", result.getResolvedQuery());

            userWordsTV.setText(userSpeechStr);

            // Now, start interpreting the speech and executing commands based on it.

            if (userSpeechStr.contains("call") || userSpeechStr.contains("dial")) {

                // The dialer/call function now starts
                call(userSpeechStr);

            }

            else if (userSpeechStr.contains("text") || userSpeechStr.contains("send")) {

                // send functin
                text();

            }



        }


    }

    public void call(String userSpeechStr) {


        // Add logic for extracting number here... would be a cool feature
        // maybe ask the user????

        // Need to incorporate the computer and the user in a conversation

        Intent callIntent = new Intent(Intent.ACTION_DIAL);
        callIntent.setData(Uri.parse("tel:" + ""));             // This preloads the dialer with numbers

        // If there is an activity that can process the request
        if (callIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(callIntent);
        }
        else
            computerWordsTV.setText("Sorry, I can't find an app to do this.");

    }

    public void text(){

        // Send a (Text/email?)

        Intent textIntent = new Intent(Intent.ACTION_SEND); // doesnt specify who to send to

        // This holds the text... need to parse user inputs
        textIntent.putExtra(Intent.EXTRA_TEXT, "This is my text to send.");
        textIntent.setType("text/plain");



        // if there is an activity that can process the request
        if (textIntent.resolveActivity(getPackageManager()) != null) {

//            startActivity(Intent.createChooser(sendIntent, getResources().getText(R.string.send_to)));

            // Shows picker/chooser
            startActivity(Intent.createChooser(textIntent, "What app do you want to use?"));


        }

        else
            computerWordsTV.setText("Sorry, I can't find an app to do that");

    }



    // Does the error handling here
    @Override
    public void onError(final AIError error) {

        // Actual error message
        String errorMessage = error.getMessage();

        // This is what it displays if the device isn't connected to the Internet
        String noInternet = "Speech recognition engine error: Server sends error status.";
        // This is what it displays if it can't understand your speech
        String cantUnderstand = "Speech recognition engine error: No recognition result matched.";
        // THis is the error message if u don't say anything
        String noInput = "Speech recognition engine error: No speech input.";


        // I want to display a custom error message instead of the pregenerated messages.
        // That is why I have this if else statement

        if (errorMessage.equals(noInternet)) {

            userWordsTV.setText("...");
            computerWordsTV.setText("Sorry, I need to be connected to the Internet to work properly.");

        }

        else if (errorMessage.equals(cantUnderstand)) {

            userWordsTV.setText("...");
            computerWordsTV.setText("I can't understand you. Try again, please.");

        }

        else if (errorMessage.equals(noInput)) {

            userWordsTV.setText("...");
            computerWordsTV.setText("Did you say something? Try again, please.");

        }

    }




    // Empty methods to implement AIListener
    @Override
    public void onListeningStarted() {}

    @Override
    public void onListeningFinished() {}

    @Override
    public void onAudioLevel(final float level) {}


}
