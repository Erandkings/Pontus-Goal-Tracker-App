package com.project.pontusgoaltracker;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.concurrent.TimeUnit;

public class PhoneVerification extends AppCompatActivity {
    EditText otp;
    Button otpBtn;
    String mobile_no;
    String mCode;
    FirebaseAuth mAuth;
    PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallBacks;
    ProgressBar progressBar;
    PhoneAuthCredential credential;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_phone_verification);
        otp = findViewById(R.id.otp);
        otpBtn = findViewById(R.id.sign_in_otp);
        progressBar = findViewById(R.id.progressBar4);
        mAuth = FirebaseAuth.getInstance();

        mobile_no = getIntent().getStringExtra("phone");
        sendVerification(mobile_no);

        otpBtn.setOnClickListener(new View.OnClickListener() {

            String code = otp.getText().toString().trim();

            @Override
            public void onClick(View v) {
                if (code.isEmpty()) {

                    otp.setError("Enter Code Please");
                    otp.requestFocus();
                    return;
                }
                verifyCode(code);
            }
        });

    }

    private void verifyCode(String code) {
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mCode, code);
        signInOtp(credential);


    }

    private void sendVerification(String mobile_no) {
        progressBar.setVisibility(View.VISIBLE);
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile_no,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD, mCallback

        );
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallback = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            mCode = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            //  progressBar.setVisibility(View.GONE);
            //  Toast.makeText(PhoneVerification.this, "User has been successfully registered", Toast.LENGTH_SHORT).show();
            //   credential = phoneAuthCredential;
            if (code != null) {
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(PhoneVerification.this, e.getMessage(), Toast.LENGTH_SHORT).show();

        }
    };

    private void signInOtp(PhoneAuthCredential credential) {

        mAuth.signInWithCredential(credential).addOnCompleteListener(PhoneVerification.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    progressBar.setVisibility(View.GONE);
                    Intent intent = new Intent(PhoneVerification.this, GoalListActivity.class);
                    startActivity(intent);
                } else {
                    progressBar.setVisibility(View.GONE);
                    Toast.makeText(PhoneVerification.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });

    }


}
