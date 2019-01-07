package com.developer.ankit.quizzer;


import android.content.Intent;
import android.os.CountDownTimer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class VerificationActivity extends AppCompatActivity {

    String phoneNumber;
    String mName;
    private String mVerficationID;
    String code;

    private EditText mSmsCode;
    private Button mVerifyBtn;

    private FirebaseAuth mAuth;
    private boolean delete = false;

    private TextView mWrongNumber;
    private TextView mPhoneNumberTextView;
    private TextView mCountdownTimerTextView;

    private long seconds , minutes , millisRemaining;
    CountDownTimer countDownTimer = null;

    private DatabaseReference mUserDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);

        mAuth = FirebaseAuth.getInstance();
        mUserDatabase = FirebaseDatabase.getInstance().getReference().child("Users");

        mSmsCode = (EditText) findViewById(R.id.textSmsCode);
        mSmsCode.requestFocus();

        code = mSmsCode.getText().toString().trim();
        Log.d("smscode", code);

        mVerifyBtn = (Button) findViewById(R.id.verifyBtn);

        mWrongNumber = (TextView) findViewById(R.id.wrongNumberTextView);
        mPhoneNumberTextView = (TextView) findViewById(R.id.phoneNumberTextView);
        mCountdownTimerTextView = (TextView)  findViewById(R.id.countDownTimer);

        // getting intent extras here
        phoneNumber = getIntent().getStringExtra("phoneNumber");
        mName = getIntent().getStringExtra("name");

        mPhoneNumberTextView.setText("+91 " + phoneNumber);

        String phoneNumberWithCountryCode = "+91" + phoneNumber;
        sendVerificationCode(phoneNumberWithCountryCode);


        mWrongNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent wrongNumberIntent = new Intent(VerificationActivity.this , LoginActivity.class);
                wrongNumberIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(wrongNumberIntent);
            }
        });

        mVerifyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (code.isEmpty() || code.length() == 6) {
                    mSmsCode.setError("Invalid OTP");
                    mSmsCode.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

        countDownTimer = new CountDownTimer(120000 , 1000){

            @Override
            public void onTick(long millisUntilFinished) {

                millisRemaining = millisUntilFinished;

                seconds = (long) (millisUntilFinished / 1000);
                minutes = seconds / 60;
                seconds = seconds % 60;
                mCountdownTimerTextView.setText(String.format("%02d", minutes)
                        + ":" + String.format("%02d", seconds));

            }

            @Override
            public void onFinish() {
                mCountdownTimerTextView.setText("Resend SMS");
            }
        }.start();

    }

    private void verifyCode(String code) {

        PhoneAuthCredential credentials = PhoneAuthProvider.getCredential(mVerficationID, code);
        signInWithCredentials(credentials);
    }

    private void signInWithCredentials(PhoneAuthCredential credentials) {

        mAuth.signInWithCredential(credentials)
                .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull final Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            Map<String , Object> user = new HashMap<>();
                            user.put("name" , mName);
                            user.put("phone" , phoneNumber);

                            mUserDatabase.child(mAuth.getCurrentUser().getUid()).setValue(user)
                                                .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                    @Override
                                                    public void onComplete(@NonNull Task<Void> task) {
                                                        if(task.isSuccessful()){

                                                            Toast.makeText(VerificationActivity.this, "Registered Succedd", Toast.LENGTH_SHORT).show();
                                                            Intent mainIntent = new Intent(getApplicationContext() , MainActivity.class);
                                                            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                                            startActivity(mainIntent);

                                                        } else   {
                                                            Toast.makeText(VerificationActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                                                        }
                                                    }
                                                });




                        }
                    }
                });
    }


    private void sendVerificationCode(String number) {

        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                number,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks
        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks
            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            mVerficationID = s;
        }

        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            String sendedCode = phoneAuthCredential.getSmsCode();

            if (sendedCode != null) {
                verifyCode(sendedCode);

                Log.d("sended code", sendedCode);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(VerificationActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    };

    @Override
    protected void onStart() {
        super.onStart();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {

        outState.putLong("millisLeft" , millisRemaining);
        super.onSaveInstanceState(outState);

    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        millisRemaining = savedInstanceState.getLong("millisleft");

        System.out.println(millisRemaining/1000);

        countDownTimer.onTick(millisRemaining);
    }
}
