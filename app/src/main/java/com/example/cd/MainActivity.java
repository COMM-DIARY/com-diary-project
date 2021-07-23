package com.example.cd;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.cd.data.MyDbHandler;
import com.example.cd.databinding.ActivityMainBinding;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    private ActivityMainBinding binding;
    private PhoneAuthProvider.ForceResendingToken forceResendingToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    private String mVerificationId;
    private static final String TAG = "MAIN_TAG";
    private FirebaseAuth firebaseAuth;
    private ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyDbHandler db = new MyDbHandler(MainActivity.this);

        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.phoneL1.setVisibility(View.VISIBLE);
        binding.codeL1.setVisibility(View.GONE);

        firebaseAuth = FirebaseAuth.getInstance();

        pd = new ProgressDialog(this);
        pd.setTitle("Please wait...");
        pd.setCanceledOnTouchOutside(false);

        mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
                signInWithPhoneAuthCredential(phoneAuthCredential);
            }

            @Override
            public void onVerificationFailed(@NonNull FirebaseException e) {
                pd.dismiss();
                Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @SuppressLint("SetTextI18n")
            @Override
            public void onCodeSent(@NonNull String verificationId, @NonNull PhoneAuthProvider.ForceResendingToken token) {
                super.onCodeSent(verificationId, forceResendingToken);

                Log.d(TAG,"onCodeSent: "+verificationId);

                mVerificationId = verificationId;
                forceResendingToken = token;
                pd.dismiss();

                binding.phoneL1.setVisibility(View.GONE);
                binding.codeL1.setVisibility(View.VISIBLE);

                Toast.makeText(MainActivity.this, "verification code sent", Toast.LENGTH_SHORT).show();

                binding.codeSendDescription.setText("Please type the verification code we send \nto "+binding.phoneEt.getText().toString().trim());
            }
        };
        binding.phoneContinueBtn.setOnClickListener(v -> {
            String phone = binding.phoneEt.getText().toString().trim();
            if(TextUtils.isEmpty(phone)){
                Toast.makeText(MainActivity.this, "Please enter the phone number", Toast.LENGTH_SHORT).show();
            }
            else{
                startPhoneNumberVerification(phone);
            }
        });
        binding.resendCodeTv.setOnClickListener(v -> {
            String phone = binding.phoneEt.getText().toString().trim();
            if(TextUtils.isEmpty(phone)){
                Toast.makeText(MainActivity.this, "Please enter the phone number", Toast.LENGTH_SHORT).show();
            }
            else{
                resendVerificationCode(phone,forceResendingToken);
            }
        });
        binding.codeSubmitBtn.setOnClickListener(v -> {
            String code = binding.codeEt.getText().toString().trim();
            if(TextUtils.isEmpty(code)){
                Toast.makeText(MainActivity.this, "Please enter verification code", Toast.LENGTH_SHORT).show();
            }
            else{
                verifyPhoneNumberWithCode(mVerificationId, code);
            }
        });


    }
    private void startPhoneNumberVerification(String phoneNumber) {
        pd.setMessage("Verifying Phone Number");
        pd.show();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phoneNumber)
                        .setTimeout(60L, TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void resendVerificationCode(String phone, PhoneAuthProvider.ForceResendingToken token){
        pd.setMessage("Resending Code");
        pd.show();

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(firebaseAuth)
                        .setPhoneNumber(phone)
                        .setTimeout(60L,TimeUnit.SECONDS)
                        .setActivity(this)
                        .setCallbacks(mCallbacks)
                        .setForceResendingToken(token)
                        .build();

        PhoneAuthProvider.verifyPhoneNumber(options);
    }
    private void verifyPhoneNumberWithCode(String mVerificationId, String code){
        pd.setMessage("Verifying Code");
        pd.show();

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);
        signInWithPhoneAuthCredential(credential);
        Intent intent=new Intent(this,SplashScreen.class);
        startActivity(intent);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        pd.setMessage("Logging in...");

        firebaseAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> {
                    pd.dismiss();
                    String phone = Objects.requireNonNull(firebaseAuth.getCurrentUser()).getPhoneNumber();
                    Toast.makeText(MainActivity.this, "Logged In as "+phone, Toast.LENGTH_SHORT).show();
                })
                .addOnFailureListener(e -> {
                    pd.dismiss();
                    Toast.makeText(MainActivity.this, ""+e.getMessage(), Toast.LENGTH_SHORT).show();
                });

    }}