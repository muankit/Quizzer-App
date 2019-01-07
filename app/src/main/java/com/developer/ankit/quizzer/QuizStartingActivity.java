package com.developer.ankit.quizzer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class QuizStartingActivity extends AppCompatActivity {

    private TextView subjectNameTV;

    private Button mSkipQuizBtn , mQuizContinueBtn;

    // toolbar instance
    private android.support.v7.widget.Toolbar mToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_starting);

        //Custom Toolbar
        mToolbar = (android.support.v7.widget.Toolbar) findViewById(R.id.startQuizToolbar);
        setSupportActionBar(mToolbar);
        mToolbar.setElevation(0F);

        subjectNameTV = (TextView) findViewById(R.id.subjectName);
        mSkipQuizBtn = (Button) findViewById(R.id.skipQuizBtn);
        mQuizContinueBtn = (Button) findViewById(R.id.continueQuizBtn);

        Intent intent = getIntent();

        final boolean enggChemIntent = intent.getExtras().getBoolean("enggChemIntent" , false);
        final boolean enggPhyIntent = intent.getExtras().getBoolean("enggPhyIntent" , false);
        final boolean enggMathIntent = intent.getExtras().getBoolean("enggMathIntent" , false);

        if(enggChemIntent){

            String enggChem = intent.getStringExtra("enggChem");
            subjectNameTV.setText(enggChem);
        }else if (enggPhyIntent){

            String enggPhy = intent.getStringExtra("enggPhy");
            subjectNameTV.setText(enggPhy);
        }else if (enggMathIntent){

            String enggMath = intent.getStringExtra("enggMath");
            subjectNameTV.setText(enggMath);
        }

        mSkipQuizBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent skipIntent = new Intent(QuizStartingActivity.this , MainActivity.class);
                skipIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(skipIntent);
            }
        });

        mQuizContinueBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent continueIntent = new Intent(QuizStartingActivity.this , QuizPlayingActivity.class);
                continueIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                continueIntent.putExtra("chemQuiz" , enggChemIntent);
                continueIntent.putExtra("phyQuiz" , enggPhyIntent);
                continueIntent.putExtra("mathQuiz" , enggMathIntent);
                startActivity(continueIntent);
            }
        });

    }


}
