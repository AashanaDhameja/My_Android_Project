package com.example.hp.bakeology;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class Category extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category);
    }
    public  void sel_cat(View view)
    {

        Intent intent=new Intent(Category.this,AddCat.class);
        startActivity(intent);

    }
    public  void add_cat(View view)
    {

        Intent intent=new Intent(Category.this,SelCat.class);
        startActivity(intent);


    }

}
