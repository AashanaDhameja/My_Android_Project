package com.example.hp.bakeology;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.view.ViewPropertyAnimatorCompatSet;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.firebase.client.core.view.View;

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

public class Book extends AppCompatActivity {
    String s;
    TextView t1,t2,t3;
    cake_det det;
    String msg;
    String bname;
    String bmail;
    String bid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book);
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        s = sharedPreferences.getString("book", "");
        String[] parts=s.split("  ");
        bid=parts[0];
        bmail=parts[1];
        bname=parts[2];
        t1= (TextView) findViewById(R.id.textView16);
        t2= (TextView) findViewById(R.id.textView24);
        t3= (TextView) findViewById(R.id.textView26);
        t1.setText(bid);
        t2.setText(bname);
        t3.setText(bmail);

    }
  /*  public void domail(View v)
    {
      //  det=(cake_det) getIntent().getExtras().getSerializable("booked");
        if(det!=null) {
            //Glide.with(this).load(det.getImage_path()).into(img);
            String cake = det.getImage_name();
            String flavour = det.getFlavour();
            String cat = det.getCategory();
            msg="Cake name: "+cake+"Flavour: "+flavour+"Category:"+cat;

        }
       // new Book.checkc().execute("http://cakesnbakes.000webhostapp.com/Detail.php",msg,bmail);

    }*/
    public  void domail(android.view.View v)
    {
        det=(cake_det) getIntent().getExtras().getSerializable("booked");
        if(det!=null) {
            //Glide.with(this).load(det.getImage_path()).into(img);
            String cake = det.getImage_name();
            String flavour = det.getFlavour();
            String cat = det.getCategory();
            msg="Cake name: "+cake+"Flavour: "+flavour+"Category:"+cat;

        }
        // new Book.checkc().execute("http://cakesnbakes.000webhostapp.com/Detail.php",msg,bmail);

    }
    public  void logout(android.view.View v)
    {
        Intent intent = new Intent(getApplicationContext(), Login.class);
        startActivity(intent);
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
            String message=params[1];
            String mail=params[2];
            try {
                url=new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {

                HttpURLConnection urlconn= (HttpURLConnection) url.openConnection();

                urlconn.setDoOutput(true);
                Uri.Builder builder=new Uri.Builder()
                        .appendQueryParameter("mail",mail)
                        .appendQueryParameter("message",message);


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
            Toast.makeText(Book.this,"heyy"+s,Toast.LENGTH_SHORT).show();
            //Intent intent=new Intent(Details.this,Category.class);
            //startActivity(intent);


        }
    }
}
