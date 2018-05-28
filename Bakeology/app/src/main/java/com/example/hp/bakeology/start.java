package com.example.hp.bakeology;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

public class start extends AppCompatActivity {

    Button login;
    Button signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //show();
        login= (Button) findViewById(R.id.button7);
        signup= (Button) findViewById(R.id.button8);


    }
    public  void login(View v)
    {
        Intent intent=new Intent(getApplicationContext(),Login.class);
        startActivity(intent);
    }
    public  void signup(View v)
    {
        Intent intent=new Intent(getApplicationContext(),signup.class);
        startActivity(intent);

    }
    public  void show()
    {
        Toast toast=new Toast(this);
        toast.setDuration(Toast.LENGTH_LONG);
        LayoutInflater inflator=getLayoutInflater();
        View appear=inflator.inflate(R.layout.toast,  (ViewGroup)findViewById(R.id.root));
        toast.setView(appear);
        toast.show();
    }
}
