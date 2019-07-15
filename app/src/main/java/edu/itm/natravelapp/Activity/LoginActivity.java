package edu.itm.natravelapp.Activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import edu.itm.natravelapp.FirebaseExtra.FirebaseInit;
import edu.itm.natravelapp.R;
import edu.itm.natravelapp.Model.UsersModel;
import io.paperdb.Paper;

public class LoginActivity extends AppCompatActivity {


    public static final int RC_SIGN_IN = 001;
    private static final String TAG = MainActivity.class.getSimpleName();


    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    List<UsersModel> usersModelList = new ArrayList<>();

    public String phoneNumber;

    private boolean mVerificationInProgress = false;
    private String mVerificationId;
    private PhoneAuthProvider.ForceResendingToken mResendToken;
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;

    private EditText phoneNumberField, smsCodeVerificationField;
    private FloatingActionButton startVerficationButton, verifyPhoneButton;

    private String verificationid;

    int flag = 0;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);


        getData();

        //intialized firebase auth
        mAuth = FirebaseAuth.getInstance();



        Paper.init(getApplicationContext());

        phoneNumberField = findViewById(R.id.email_et);
        smsCodeVerificationField = findViewById(R.id.password_et);
        startVerficationButton = findViewById(R.id.fab);
        verifyPhoneButton = findViewById(R.id.fab1);
        startVerficationButton.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View view) {


                if (!validatePhoneNumberAndCode()) {
                    return;
                }

                flag = 0;

                String getEmailId = phoneNumberField.getText().toString();

// Check if email id is valid or not
                if (!isEmailValid(getEmailId)){
                    phoneNumber = getEmailId;

                    for( UsersModel user : usersModelList){
                        if(user.getMobile().equals(phoneNumber)){
                            flag =1;
                            Paper.book().write("user",user);
                        }
                    }

                }else{

                    for( UsersModel user : usersModelList){
                        if(user.getEmail().equals(phoneNumberField.getText().toString())){
                            flag =1;
                            phoneNumber = user.getMobile();
                            Paper.book().write("user",user);
                        }
                    }

                }

                if(flag==1){
                    startPhoneNumberVerification(phoneNumber);

                }else{
                    Toast.makeText(getApplicationContext(),"Your Number Not registered please contact Admin",Toast.LENGTH_SHORT).show();
                }

            }
        });
        verifyPhoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!validateSMSCode()) {
                    return;
                }

                verifyPhoneNumberWithCode(verificationid, smsCodeVerificationField.getText().toString());
            }
        });

    }
        @Override
        protected void onStart() {
            super.onStart();

            // Check if user is signed in (non-null) and update UI accordingly.
            FirebaseUser currentUser = mAuth.getCurrentUser();
            updateUI(currentUser);

            mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
                @Override
                public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

                    signInWithPhoneAuthCredential(phoneAuthCredential);

                }

                @Override
                public void onVerificationFailed(FirebaseException e) {

                    Toast.makeText(LoginActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();

                }

                @Override
                public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                    super.onCodeSent(s, forceResendingToken);

                    verificationid = s;
                }
            };
        }

        private void startPhoneNumberVerification(String phoneNumber) {

            PhoneAuthProvider.getInstance().verifyPhoneNumber(
                    phoneNumber,        // Phone number to verify
                    30,                 // Timeout duration
                    TimeUnit.SECONDS,   // Unit of timeout
                    this,               // Activity (for callback binding)
                    mCallbacks);        // OnVerificationStateChangedCallbacks

            Toast.makeText(getApplicationContext(),"OTP, if not received request in 30 sec.",Toast.LENGTH_SHORT).show();


        }

        private void verifyPhoneNumberWithCode(String verificationId, String code) {

            PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationId, code);
            signInWithPhoneAuthCredential(credential);
        }


        private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
            mAuth.signInWithCredential(credential)
                    .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                // Sign in success, update UI with the signed-in user's information
                                Log.d(TAG, "signInWithCredential:success");

                                FirebaseUser user = task.getResult().getUser();

                                startActivity(new Intent(getApplicationContext(), HomeActivity.class));

                            } else {
                                // Sign in failed, display a message and update the UI
                                Log.w(TAG, "signInWithCredential:failure", task.getException());
                                if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                    // The verification code entered was invalid

                                    smsCodeVerificationField.setError("Invalid code.");

                                }

                            }
                        }
                    });
        }

        private boolean validatePhoneNumberAndCode() {
            String phoneNumber = phoneNumberField.getText().toString();
            if (TextUtils.isEmpty(phoneNumber)) {
                phoneNumberField.setError("Invalid phone number.");
                return false;
            }


            return true;
        }

        private boolean validateSMSCode(){
            String code = smsCodeVerificationField.getText().toString();
            if (TextUtils.isEmpty(code)) {
                smsCodeVerificationField.setError("Enter verification Code.");
                return false;
            }

            return true;
        }

        public  void getData(){

            FirebaseInit.getDatabase().getReference().child("registeredUsers").addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                    try{

                        UsersModel usersModel = dataSnapshot.getValue(UsersModel.class);

                        usersModelList.add(usersModel);


                    }catch (Exception e){

                    }

                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }





    public void updateUI(FirebaseUser user){

        if(user!=null){
            Intent intent = new Intent(getApplicationContext(),HomeActivity.class);
            startActivity(intent);
            finish();
        }
    }





}
