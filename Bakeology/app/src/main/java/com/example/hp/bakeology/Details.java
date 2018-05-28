package com.example.hp.bakeology;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Details extends AppCompatActivity {

    EditText flavour;
    EditText price;
    EditText discription;
    //TextView text;
    String s1;
    String s3;
    String name;
    String path;
    int s2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        flavour= (EditText) findViewById(R.id.editText9);

        price= (EditText) findViewById(R.id.editText13);

        discription= (EditText) findViewById(R.id.editText14);

      //  text= (TextView) findViewById(R.id.textView8);




    }

     public  void akhil(android.view.View v)
    {
        s1=flavour.getText().toString();
        s2=Integer.parseInt(String.valueOf(price.getText()));
        s3=discription.getText().toString();
        SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
        path=sharedPreferences.getString("img_path","");
        name=sharedPreferences.getString("name","");

        new Details.checkc().execute("http://cakesnbakes.000webhostapp.com/Detail.php",s1,s3,path,name);

    }

    class checkc extends AsyncTask<String,Void,String>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //text.setText("heyy");

        }

        @Override
        protected String doInBackground(String... params) {
            URL url=null;
        String s1=params[1];
        String s3=params[2];
            try {
                url=new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {

                HttpURLConnection urlconn= (HttpURLConnection) url.openConnection();

                urlconn.setDoOutput(true);
                Uri.Builder builder=new Uri.Builder()
                        .appendQueryParameter("flavour",s1)
                        .appendQueryParameter("price", String.valueOf(s2))
                        .appendQueryParameter("discription", s3)
                        .appendQueryParameter("name", name)
                        .appendQueryParameter("path", path);


                OutputStream outputStream=urlconn.getOutputStream();
                BufferedWriter bufferedWriter=new BufferedWriter(new OutputStreamWriter(outputStream,"UTF-8"));
                bufferedWriter.write(builder.build().getEncodedQuery());
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();

                urlconn.connect();


                InputStream inputStream=urlconn.getInputStream();
                BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));

                String str="";

                while (true )
                {
                    String val=bufferedReader.readLine();
                    if(val==null)
                    {
                        break;
                    }
                    else
                    {
                        str=val;
                    }

                }


                bufferedReader.close();
              return str;
            } catch (IOException e) {
                e.printStackTrace();
            }
        return null;
        }

        @Override
        protected void onPostExecute(String s) {
            // super.onPostExecute(s);
            //String msg=;
            //text.setText(s);

                Intent intent=new Intent(Details.this,Category.class);
                startActivity(intent);
              SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor= sharedPreferences.edit();
                editor.putString("dis",s3);
                editor.commit();

             }
    }

}
