package com.example.lit15146664.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuItem;

import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.TextView;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.parse.GetCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;

import com.parse.ParseException;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    //variables
    private Button btnFalse;
    private Button btnTrue;
    private TextView lblQuestion;
    private ImageView imgPicture;
    private String m_Text = "";

    private List<QuestionObject> questions;

    private QuestionObject currentQuestion;
    private int index;

    private int score;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //connecting the variables to interface items
        btnFalse = (Button) findViewById(R.id.btnFalse);
        btnTrue = (Button) findViewById(R.id.btnTrue);
        lblQuestion = (TextView) findViewById(R.id.lblQuestion);
        imgPicture = (ImageView) findViewById(R.id.imgPicture);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter your name!");

// Set up the input
        final EditText input = new EditText(this);
// Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

// Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Text = input.getText().toString();
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();



        //set question text
        lblQuestion.setText("Is my name Samuel?");

        //set image picture
        imgPicture.setImageResource(R.drawable.cat);

        index = 0;
        score = 0;

        //onclick listeners
        btnFalse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                determineButtonPress(false);


            }
        });

        btnTrue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                determineButtonPress(true);

            }
        });

        generateQuestions();

        setUpQuestion();

        Paper.init(this);
    }

    private void generateQuestions(){
        questions = new ArrayList<>();

        questions.add(new QuestionObject("CtZI2ebO3X", true, R.drawable.ethiopia));
        questions.add(new QuestionObject("VtsC9TZkIy", false, R.drawable.jamaica));
        questions.add(new QuestionObject("TU0vUeS5uO", true, R.drawable.australia));
        questions.add(new QuestionObject("AveyUuD75r", true, R.drawable.uk));
        questions.add(new QuestionObject("NSemlewuqV", true, R.drawable.birthday));
        questions.add(new QuestionObject("07Klqob3d8", false, R.drawable.uk));
        questions.add(new QuestionObject("e4eDdVbZDK", true, R.drawable.uk));
        questions.add(new QuestionObject("TPiaiZx7ci", false, R.drawable.skype));
        questions.add(new QuestionObject("tWzrkoelIt", true, R.drawable.location));
        questions.add(new QuestionObject("p6qnwdkpEj", true, R.drawable.cat));

    }



    private void setUpQuestion() {

        if (index == 10) {
            //All questions completed, end game
            endGame();

        } else {

            currentQuestion = questions.get(index);

            ParseQuery<ParseObject> query = ParseQuery.getQuery("Questions");
            query.getInBackground(currentQuestion.getQuestion(), new GetCallback<ParseObject>() {
                @Override
                public void done(ParseObject object, ParseException e) {
                    if (e == null) {
                        // game score
                        String textQuestion = object.getString("question");
                        lblQuestion.setText(textQuestion);
                        imgPicture.setImageResource(currentQuestion.getPicture());
                    }
                }
            });

            lblQuestion.setText(currentQuestion.getQuestion());
            imgPicture.setImageResource(currentQuestion.getPicture());

            index++;
        }
    }

    private void determineButtonPress(boolean answer){

        boolean expectedAnswer = currentQuestion.isAnswer();
        MediaPlayer player;
        if (answer == expectedAnswer){
            //Correct Answer

            Toast.makeText(MainActivity.this, "Correct!", Toast.LENGTH_SHORT).show();
            score++;
            player = MediaPlayer.create(MainActivity.this,R.raw.correct); // plays correct sound obtained from http://tinyurl.com/glgcak6
            player.start();
        } else {
            //else Wrong Answer

            Toast.makeText(MainActivity.this, "Wrong!", Toast.LENGTH_SHORT).show();
            player = MediaPlayer.create(MainActivity.this,R.raw.wrong); // plays error sound obtained from http://tinyurl.com/nzxjtsc
            player.start();
        }

        setUpQuestion();

    }

    private void endGame(){


        final AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this)
                .setTitle("Congratulations!")
                .setMessage("You scored " + score + "points!")
                .setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        HighScoreObject highScore = new HighScoreObject(score, m_Text, new Date().getTime());

                        List<HighScoreObject> highScores = Paper.book().read("highscores", new ArrayList<HighScoreObject>());

                        highScores.add(highScore);

                        Paper.book().write("highscores", highScores);

                        finish();

                    }

                })

            .create();
        alertDialog.show();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
