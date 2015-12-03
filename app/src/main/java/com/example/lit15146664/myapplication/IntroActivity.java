package com.example.lit15146664.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.parse.Parse;
import com.parse.ParseObject;

public class IntroActivity extends AppCompatActivity {

    private Button btnPlayButton;
    private Button btnHighScore;
    private Button btnAboutButton;
    private TextView lblWelcome;
    private ImageView imgWelcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intro);

        // Enable Local Datastore.
        Parse.enableLocalDatastore(this);

        Parse.initialize(this, "I8l8aBGIovPSybNIUDQxeh8odv3PGN7lfKXKJzBr", "vfG3yQqFoRXPkuzgUE9B2doOvlxCzfnryOMfZQuq");

        ParseObject Questions = new ParseObject("Questions");
        Questions.put("foo", "Is this the Ugandan flag?");
        Questions.saveInBackground();


        btnPlayButton = (Button)findViewById(R.id.btnPlayButton);
        btnHighScore = (Button)findViewById(R.id.btnHighScore);
        btnAboutButton = (Button)findViewById(R.id.btnAboutButton);

        btnPlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroActivity.this, MainActivity.class);
                startActivity(i);
            }
        });

        btnHighScore.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Intent i = new Intent(IntroActivity.this, HighScoreActivity.class);
            startActivity(i);

            }
        });
        btnAboutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(IntroActivity.this, ProfileActivity.class);
                startActivity(i);
            }
        });


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_intro, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
