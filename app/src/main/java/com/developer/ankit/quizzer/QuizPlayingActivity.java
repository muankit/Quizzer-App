package com.developer.ankit.quizzer;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class QuizPlayingActivity extends AppCompatActivity implements View.OnClickListener {


    private LinearLayout mRootLayout;

    private Button[] btn = new Button[4];
    private Button btn_unfocus , mResetOptionBtn;
    private int[] btn_id = {R.id.answerBtn1, R.id.answerBtn2, R.id.answerBtn3, R.id.answerBtn4};

    private Button mNextQuestionBtn , mQuizQuitBtn;

    private int answerSelected = 0 ;

    private TextView mQuestionText , mQuetsionNumberToolbarText;
    private FirebaseAuth mAuth;
    private DatabaseReference mEnggChemData , mEnggPhyData , mEnggMathData;
    private int qid = 1;
    private int score = 0;

    private String answer;

    private ProgressDialog mProgressDialog;

    private Boolean chemQuiz , phyQuiz , mathQuiz;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz_playing);

        mProgressDialog = new ProgressDialog(QuizPlayingActivity.this , R.style.AppTheme);
        mProgressDialog.setTitle("Quiz Starting");
        mProgressDialog.setMessage("This quiz contains only 10 questions ");
        mProgressDialog.show();

        chemQuiz = getIntent().getBooleanExtra("chemQuiz" , false);
        phyQuiz = getIntent().getBooleanExtra("phyQuiz" , false);
        mathQuiz = getIntent().getBooleanExtra("mathQuiz" , false);

        mRootLayout = (LinearLayout) findViewById(R.id.rootQueLayout);
        Log.d("qid", Integer.toString(qid));
        mQuestionText = (TextView) findViewById(R.id.questionText);
        mAuth = FirebaseAuth.getInstance();

        mNextQuestionBtn =(Button) findViewById(R.id.nextBtn);

        mQuetsionNumberToolbarText = (TextView) findViewById(R.id.questionNumberText);
        mResetOptionBtn = (Button) findViewById(R.id.resetOptionBtn);

        /*mQuizQuitBtn = (Button) findViewById(R.id.quitQuizBtn);

        mQuizQuitBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final AlertDialog.Builder quizExitAlert = new AlertDialog.Builder(QuizPlayingActivity.this);

                quizExitAlert.setTitle("Exit Quiz");
                quizExitAlert.setMessage("Answered question will not be saved .");
                quizExitAlert.setCancelable(false);

                quizExitAlert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent mainIntent = new Intent(QuizPlayingActivity.this , MainActivity.class);
                        mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(mainIntent);
                    }
                });

                quizExitAlert.setNegativeButton("Resume ", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                    }
                });

                quizExitAlert.show();
            }
        });*/

        mResetOptionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatePage();
            }
        });
        mNextQuestionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                nextBtnOnCLick();
            }
        });


        mEnggChemData = FirebaseDatabase.getInstance().getReference("Quiz/EnggChem/");
        mEnggPhyData = FirebaseDatabase.getInstance().getReference("Quiz/EnggPhy/");
        mEnggMathData = FirebaseDatabase.getInstance().getReference("Quiz/EnggMath/");

        for(int i = 0; i < btn.length; i++){
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setBackground(getResources().getDrawable(R.drawable.option_background));
            btn[i].setOnClickListener(this);
        }

        btn_unfocus = btn[0];

        updateQuestion();

    }

    @Override
    public void onBackPressed() {

        final AlertDialog.Builder quizExitAlert = new AlertDialog.Builder(this);

        quizExitAlert.setTitle("Exit Quiz");
        quizExitAlert.setMessage("Answered question will not be saved .");
        quizExitAlert.setCancelable(false);

        quizExitAlert.setPositiveButton("Exit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent mainIntent = new Intent(QuizPlayingActivity.this , MainActivity.class);
                mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(mainIntent);
            }
        });

        quizExitAlert.setNegativeButton("Resume ", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        quizExitAlert.show();
    }

    private void nextBtnOnCLick() {

        qid++;
        if(qid <= 10) {

            updateScore();
            updatePage();
            updateQuestion();

            Animation RightSwipe = AnimationUtils.loadAnimation(QuizPlayingActivity.this, R.anim.slide_to_left);
            mRootLayout.startAnimation(RightSwipe);

        } else   {

            if(score < 4){

                Intent looseIntent = new Intent(QuizPlayingActivity.this , LooseActivity.class);
                looseIntent.putExtra("score" , score);
                looseIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(looseIntent);

            }else{

                Intent wonIntent = new Intent(QuizPlayingActivity.this , WonActivity.class);
                wonIntent.putExtra("score" , score);
                wonIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(wonIntent);
            }
        }

    }

    private void updatePage() {
        answerSelected =0;

        for(int i = 0; i < btn.length; i++){
            btn[i] = (Button) findViewById(btn_id[i]);
            btn[i].setBackground(getResources().getDrawable(R.drawable.option_background));
            btn[i].setOnClickListener(this);
        }
    }

    private void updateScore() {

        if(Integer.toString(answerSelected).equals(answer)){
            score++;
        }else if(score == 0){
                score = 0;
        }
    }

    private void updateQuestion() {

        if(chemQuiz){

            mEnggChemData.child(Integer.toString(qid)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String question = dataSnapshot.child("question").getValue().toString();
                    String firstOpt = dataSnapshot.child("option1").getValue().toString();
                    String secondOpt = dataSnapshot.child("option2").getValue().toString();
                    String thirdOpt = dataSnapshot.child("option3").getValue().toString();
                    String fourthOpt = dataSnapshot.child("option4").getValue().toString();
                    answer = dataSnapshot.child("answer").getValue().toString();

                    mQuestionText.setText(question);

                    String [] btnOpt = {firstOpt , secondOpt, thirdOpt , fourthOpt };

                    for(int i=0; i<btnOpt.length; i++){
                        btn[i].setText(btnOpt[i]);
                    }

                    mQuetsionNumberToolbarText.setText(Integer.toString(qid));

                    mProgressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

        else if(phyQuiz){
            mEnggPhyData.child(Integer.toString(qid)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String question = dataSnapshot.child("question").getValue().toString();
                    String firstOpt = dataSnapshot.child("option1").getValue().toString();
                    String secondOpt = dataSnapshot.child("option2").getValue().toString();
                    String thirdOpt = dataSnapshot.child("option3").getValue().toString();
                    String fourthOpt = dataSnapshot.child("option4").getValue().toString();
                    answer = dataSnapshot.child("answer").getValue().toString();

                    mQuestionText.setText(question);

                    String [] btnOpt = {firstOpt , secondOpt, thirdOpt , fourthOpt };

                    for(int i=0; i<btnOpt.length; i++){
                        btn[i].setText(btnOpt[i]);
                    }

                    mQuetsionNumberToolbarText.setText(Integer.toString(qid));

                    mProgressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        } else if(mathQuiz){

            mEnggMathData.child(Integer.toString(qid)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String question = dataSnapshot.child("question").getValue().toString();
                    String firstOpt = dataSnapshot.child("option1").getValue().toString();
                    String secondOpt = dataSnapshot.child("option2").getValue().toString();
                    String thirdOpt = dataSnapshot.child("option3").getValue().toString();
                    String fourthOpt = dataSnapshot.child("option4").getValue().toString();
                    answer = dataSnapshot.child("answer").getValue().toString();

                    mQuestionText.setText(question);

                    String [] btnOpt = {firstOpt , secondOpt, thirdOpt , fourthOpt };

                    for(int i=0; i<btnOpt.length; i++){
                        btn[i].setText(btnOpt[i]);
                    }

                    mQuetsionNumberToolbarText.setText(Integer.toString(qid));

                    mProgressDialog.dismiss();

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });

        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.answerBtn1 :
                answerSelected = 1;
                setFocus(btn_unfocus, btn[0]);
                break;

            case R.id.answerBtn2 :
                answerSelected = 2;
                setFocus(btn_unfocus, btn[1]);
                break;

            case R.id.answerBtn3 :
                answerSelected = 3;
                setFocus(btn_unfocus, btn[2]);
                break;

            case R.id.answerBtn4 :
                answerSelected = 4;
                setFocus(btn_unfocus, btn[3]);
                break;
        }
        
    }

    private void setFocus(Button btn_unfocus, Button btn_focus) {

        btn_unfocus.setBackground(getResources().getDrawable(R.drawable.option_background));
        btn_focus.setBackground(getResources().getDrawable(R.drawable.selected_answer_background));
        this.btn_unfocus = btn_focus;

        Log.d("answer", Integer.toString(answerSelected));

    }

}

