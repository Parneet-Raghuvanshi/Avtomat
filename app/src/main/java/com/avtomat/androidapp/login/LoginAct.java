package com.avtomat.androidapp.login;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

import com.avtomat.androidapp.Dashboard;
import com.avtomat.androidapp.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.TimeUnit;

public class LoginAct extends AppCompatActivity {

    TextInputLayout phone_inputLayout,otp_inputlayout;
    MaterialButton continue_btn,login_btn;
    String phone_nu;
    private String mVerificationId;
    PhoneAuthProvider.ForceResendingToken token;
    private FirebaseAuth mAuth;
    boolean newuser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_login);

        phone_inputLayout = findViewById(R.id.phone_input);
        otp_inputlayout = findViewById(R.id.otp_input);
        continue_btn = findViewById(R.id.continue_btn);
        login_btn = findViewById(R.id.login_btn);
        mAuth = FirebaseAuth.getInstance();

        continue_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                phone_nu = phone_inputLayout.getEditText().getText().toString();
                boolean valid = validateNumber();
                if (valid){
                    continue_btn.setVisibility(View.GONE);
                    phone_inputLayout.setVisibility(View.GONE);
                    login_btn.setVisibility(View.VISIBLE);
                    otp_inputlayout.setVisibility(View.VISIBLE);
                    DatabaseReference dbref = FirebaseDatabase.getInstance().getReference().child("Users");
                    dbref.keepSynced(true);
                    dbref.orderByChild("number").equalTo(phone_nu).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.getValue() != null){
                                //login
                                String finnumber = "+91"+phone_nu;
                                newuser = false;
                                sendVerificationCode(finnumber);
                            }
                            else {
                                //register
                                String finnumber = "+91"+phone_nu;
                                newuser = true;
                                sendVerificationCode(finnumber);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });
                }
            }
        });

        login_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String code = otp_inputlayout.getEditText().getText().toString().trim();
                if (code.length() != 6) {
                    otp_inputlayout.setError("Enter valid code");
                    otp_inputlayout.requestFocus();
                }
                else
                    verifyVerificationCode(code);
            }
        });
    }

    private void sendVerificationCode(String mobile) {
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(mobile)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                otp_inputlayout.getEditText().setText(code);

                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
            Toast.makeText(LoginAct.this, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
            token=forceResendingToken;
        }
    };

    private void verifyVerificationCode(String code) {
        //creating the credential
        try {
            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);
            //mAuth.signOut();
            //signing the user
            signInWithPhoneAuthCredential(credential);

        }catch (Exception e){ //ss
            Toast toast = Toast.makeText(getApplicationContext(), "Verification Code is wrong, try again", Toast.LENGTH_SHORT);
            toast.setGravity( Gravity.CENTER,0,0);
            toast.show();
        }
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential).addOnCompleteListener(LoginAct.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    //verification successful we will start the dashboard.
                    if (newuser){
                        saveData();
                    }
                    else {
                        Intent intent = new Intent(LoginAct.this, Dashboard.class);
                        startActivity(intent);
                        finish();
                    }
                    Toast.makeText(LoginAct.this,"Success",Toast.LENGTH_SHORT).show();

                } else {
                    //verification unsuccessful.. display an error message
                    String message = "Somthing is wrong, we will fix it soon...";

                    if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                        message = "Invalid code entered...";
                    }
                    Toast.makeText(LoginAct.this,message, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private void saveData() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(FirebaseAuth.getInstance().getCurrentUser().getUid());
        databaseReference.keepSynced(true);
        databaseReference.child("number").setValue(phone_nu);
        databaseReference.child("userid").setValue(FirebaseAuth.getInstance().getCurrentUser().getUid());

        Intent intent = new Intent(LoginAct.this, Dashboard.class);
        startActivity(intent);
        finish();
    }

    private boolean validateNumber() {
        if (phone_nu.isEmpty()){
            phone_inputLayout.setError(getString(R.string.emptynumbertext));
            return false;
        }
        else if (phone_nu.length() != 10){
            phone_inputLayout.setError(getString(R.string.numberlengthtext));
            return false;
        }
        else return true;
    }
}