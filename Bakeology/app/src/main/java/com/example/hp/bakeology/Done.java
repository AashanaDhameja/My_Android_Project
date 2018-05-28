package com.example.hp.bakeology;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.firebase.client.core.view.View;

public class Done extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_done);


    }
    public void  dosomework(android.view.View v)
    {
        Intent intent=new Intent(Done.this,Login.class);
        startActivity(intent);


    }
}
