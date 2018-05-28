package com.example.hp.bakeology;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

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

public class sel_cake extends AppCompatActivity {
    ImageView img;
    TextView imgname;
    TextView bakern;
    TextView flavour;
    TextView disc;
    TextView cat;
    Button b;
    TextView price;
    String custn;
    cake_det det;
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_cake);
        img= (ImageView) findViewById(R.id.imageView4);
        imgname= (TextView) findViewById(R.id.textView14);
        b= (Button) findViewById(R.id.button16);
        bakern= (TextView) findViewById(R.id.textView18);
        flavour= (TextView) findViewById(R.id.textView19);
        disc= (TextView) findViewById(R.id.textView20);
        cat= (TextView) findViewById(R.id.textView21);
        price= (TextView) findViewById(R.id.textView22);
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
        custn = sharedPreferences.getString("customer", "");
        //flavour.setText(s1);
        det= (cake_det) getIntent().getExtras().getSerializable("selected");
        if(det!=null)
        {
           Glide.with(this).load(det.getImage_path()).into(img);
            imgname.setText(det.getImage_name());
            bakern.setText(det.getBaker());
            flavour.setText(det.getFlavour());
            disc.setText(det.getDiscription());
            cat.setText(det.getCategory());
            price.setText(det.getPrice());
            //
            // .detail="The selected cake is :"+imgname+". Your baker name is :"+bakern+". The flavour of your cake is "
        }

   }

    public void dojob(View v)
    {

        new sel_cake.checkc().execute("http://cakesnbakes.000webhostapp.com/order.php",custn);

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
            custn=params[1];
            //dis=sharedPreferences.getString("dis", "");

//            String s1=params[1];
            try {
                url=new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {

                HttpURLConnection urlconn= (HttpURLConnection) url.openConnection();

                urlconn.setDoOutput(true);
                Uri.Builder builder=new Uri.Builder()
                        .appendQueryParameter("cust",custn)
                        .appendQueryParameter("baker",det.getBaker())
                        .appendQueryParameter("imgname",det.getImage_name());

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
            Toast.makeText(sel_cake.this,s,Toast.LENGTH_LONG).show();
            //bakern.setText(s);
            SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor= sharedPreferences.edit();
            editor.putString("book",s);
            editor.commit();
            Intent intent=new Intent(sel_cake.this,Book.class);
            intent.putExtra("booked",det);
            startActivity(intent);


        }
    }

}



