package com.developer.ankit.quizzer;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class WonActivity extends AppCompatActivity {

    private TextView mScoreText;

    private FirebaseAuth mAuth;
    private DatabaseReference mScoreDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_won);

        mScoreText =(TextView) findViewById(R.id.scoretext);

        int score = getIntent().getIntExtra("score" , 0);

        mScoreText.setText(Integer.toString(score));




    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent mainIntent = new Intent(WonActivity.this , MainActivity.class);
        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(mainIntent);
    }
}
