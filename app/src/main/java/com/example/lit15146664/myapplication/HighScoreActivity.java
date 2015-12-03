package com.example.lit15146664.myapplication;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Button;

import java.lang.reflect.Array;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.paperdb.Paper;

public class HighScoreActivity extends AppCompatActivity {

    private ListView listView;

    private Button btnReset;

    private List<HighScoreObject> highscores;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_high_score);

        listView = (ListView)findViewById(R.id.listView);


        Paper.init(this);

        btnReset = (Button) findViewById(R.id.btnReset);

        btnReset.setOnClickListener(new View.OnClickListener() {
            @Override
        public void onClick(View v) {

                MediaPlayer player;
                Paper.book().delete("highscores");
                player = MediaPlayer.create(HighScoreActivity.this, R.raw.reset);
                player.start();
                setContentView(R.layout.activity_high_score);
            }

        });

        highscores = Paper.book().read("highscores", new ArrayList<HighScoreObject>());

        Toast.makeText(this, "number = " + highscores.size(), Toast.LENGTH_SHORT).show();

        HighscoreAdapter adapter = new HighscoreAdapter(highscores);

        listView.setAdapter(adapter);
    }

    private class HighscoreAdapter extends ArrayAdapter<HighScoreObject> {

        public HighscoreAdapter(List<HighScoreObject> items) {

            super(HighScoreActivity.this, 0, items);
        }
        @Override
        public View getView(int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = getLayoutInflater().inflate(
                        R.layout.row_highscore, null);
            }

            //get the highscore object for the row we're looking at
            HighScoreObject highscore = highscores.get(position);
            Date date = new Date(highscore.getTime());

            SimpleDateFormat fmtOut = new SimpleDateFormat("dd-MM-yyyy");

            TextView lblTitle = (TextView)convertView.findViewById(R.id.lblTitle);

            lblTitle.setText(highscore.getScore() + " - " + highscore.getName() + " - " + fmtOut.format(date));

            return convertView;
        }// end get view

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_high_score, menu);
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
