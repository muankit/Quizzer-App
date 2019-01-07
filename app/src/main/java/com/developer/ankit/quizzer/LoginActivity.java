package com.developer.ankit.quizzer;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import org.w3c.dom.Text;

import java.util.concurrent.TimeUnit;

public class LoginActivity extends AppCompatActivity {

    private EditText mPhonetext;
    private EditText mNameText;
    private Button mLoginBtn;

    private TextView nameHeading;
    private TextView phoneHeading;

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mPhonetext = (EditText) findViewById(R.id.loginPhoneText);
        mNameText = (EditText) findViewById(R.id.loginNameText);

        nameHeading =(TextView) findViewById(R.id.nameText);
        phoneHeading = (TextView) findViewById(R.id.phoneText);

        // Intializing font here
        Typeface myUbuntuRegularFont = Typeface.createFromAsset(getAssets() , "Fonts/Ubuntu-Regular.ttf");
        nameHeading.setTypeface(myUbuntuRegularFont);
        phoneHeading.setTypeface(myUbuntuRegularFont);

        mAuth = FirebaseAuth.getInstance();
        mLoginBtn = (Button) findViewById(R.id.loginBtn);

        mLoginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!TextUtils.isEmpty(mPhonetext.getText().toString())
                        && (mPhonetext.length() == 10) && !TextUtils.isEmpty(mNameText.getText().toString())) {

                    showAlertDialog();

                } else {
                    if(TextUtils.isEmpty(mNameText.getText().toString())){
                        mNameText.setError("Enter a valid name");
                        mNameText.requestFocus();
                    }
                        mPhonetext.setError("Enter Valid Phone Number");
                        mPhonetext.requestFocus();
                }

            }
        });

    }

    private void showAlertDialog() {
        final AlertDialog.Builder verifyDialog = new AlertDialog.Builder(LoginActivity.this);

        LayoutInflater newInflator = LayoutInflater.from(LoginActivity.this);
        View confirmDialogView = newInflator.inflate(R.layout.confirm_dialog_layout, null);

        verifyDialog.setView(confirmDialogView);

        //Setting Phone number to dialog here
        TextView phoneText = confirmDialogView.findViewById(R.id.phoneNumber);
        phoneText.setText("+91  " + mPhonetext.getText().toString());


        //Clicking OK button
        verifyDialog.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent verifyIntent = new Intent(LoginActivity.this, VerificationActivity.class);
                verifyIntent.putExtra("phoneNumber", mPhonetext.getText().toString());
                verifyIntent.putExtra("name" , mNameText.getText().toString());
                startActivity(verifyIntent);
                finish();
            }
        });

        //Clicking Edit Button
        verifyDialog.setNegativeButton("Edit", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        verifyDialog.show();

    }

    @Override
    protected void onStart() {
        super.onStart();

        if(mAuth.getCurrentUser() != null){
            Intent mainIntent = new Intent(LoginActivity.this , MainActivity.class);
            mainIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(mainIntent);
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString("name" , mPhonetext.getText().toString());
        outState.putString("phone" , mPhonetext.getText().toString());
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        mPhonetext.setText(savedInstanceState.getString("name" , ""));
        mNameText.setText(savedInstanceState.getString("phone" , ""));

    }
}

