package com.example.hp.bakeology;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
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

public class AddSelCat extends AppCompatActivity {
    Cat_det det;
    TextView text;
    String dis;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_sel_cat);
        det= (Cat_det) getIntent().getExtras().getSerializable("Cate_data");
        text= (TextView) findViewById(R.id.textView5);
    }
    public void  dowork(View v)
    {
        String s=det.getCat_name();
        text.setText(s);
        SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
       dis = sharedPreferences.getString("dis", "");
       new AddSelCat.checkc().execute("http://cakesnbakes.000webhostapp.com/selcategory.php",s,dis);

    }
    class checkc extends AsyncTask<String,Void,String>
    {


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            text.setText("heyy");

        }

        @Override
        protected String doInBackground(String... params) {
            URL url=null;
            String s1=params[1];
            String s2=params[2];

            try {
                url=new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {

                HttpURLConnection urlconn= (HttpURLConnection) url.openConnection();

                urlconn.setDoOutput(true);
                Uri.Builder builder=new Uri.Builder()
                        .appendQueryParameter("dis",s2)
                        .appendQueryParameter("category",s1);


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
            text.setText(s);

            Intent intent=new Intent(AddSelCat.this,Done.class);
            startActivity(intent);

        }
    }

}
