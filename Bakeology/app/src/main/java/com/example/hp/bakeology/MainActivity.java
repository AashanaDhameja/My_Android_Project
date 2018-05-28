package com.example.hp.bakeology;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.util.TimeUnit;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.Firebase;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;

public class MainActivity extends AppCompatActivity {
FirebaseAuth auth;
    EditText e1,e2;
PhoneAuthProvider.OnVerificationStateChangedCallbacks mcallback;
    String verify;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        e1= (EditText) findViewById(R.id.editText);
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor= sharedPreferences.edit();
        editor.putString("phoneno", String.valueOf(e1));
        editor.commit();
        e2= (EditText) findViewById(R.id.editText2);
        auth=FirebaseAuth.getInstance();
        mcallback= new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            @Override
            public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            }

            @Override
            public void onVerificationFailed(FirebaseException e) {

            }

            @Override
            public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
                super.onCodeSent(s, forceResendingToken);
                verify=s;
                Toast.makeText(getApplicationContext(),"code sent",Toast.LENGTH_LONG).show();
            }
        };

    }
    public  void send_sms(View v)
    {
        String number=e1.getText().toString();

        PhoneAuthProvider.getInstance().verifyPhoneNumber(number,60, java.util.concurrent.TimeUnit.SECONDS,this,mcallback);
    }
    public void signin(PhoneAuthCredential phoneAuthCredential)
    {

        auth.signInWithCredential(phoneAuthCredential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful())
                {

                    Toast.makeText(getApplicationContext(),"User Sign in successful",Toast.LENGTH_LONG).show();
                    Intent intent=new Intent(getApplicationContext(),start.class);
                    startActivity(intent);

                }
                else {

                    Toast.makeText(getApplicationContext(),"Invalid number",Toast.LENGTH_LONG).show();
                }

            }
        });


    }
    public void setVerify(View view)
    {
     String inputc=e2.getText().toString();
        if(verify.equals(""))
        {

        }
        else {
            verifyphone(verify,inputc);
            Toast.makeText(getApplicationContext(),"sent for verification",Toast.LENGTH_LONG).show();
        }
    }

    public void verifyphone(String verify,String inputc)
    {
        PhoneAuthCredential credential=PhoneAuthProvider.getCredential(verify,inputc);
        signin(credential);

    }
}
