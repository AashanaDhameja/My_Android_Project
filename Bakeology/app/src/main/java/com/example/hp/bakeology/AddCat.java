package com.example.hp.bakeology;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
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
import java.net.URLConnection;

public class AddCat extends AppCompatActivity {

    TextView text;
    EditText edit;
    ImageView img;
    String path;
    String dis;
    //View b=findViewById(R.id.imageView3);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cat);
      //
        img = (ImageView) findViewById(R.id.imageView3);
        edit= (EditText) findViewById(R.id.editText5);
        text= (TextView) findViewById(R.id.textView10);
    }
    public  void category(View v)
    {
        new Download().execute();
    }

    public void add_name(View  v)
    {
       String cat_name= edit.getText().toString();

        new AddCat.checkc().execute("http://cakesnbakes.000webhostapp.com/add_cat.php",cat_name);

        Intent intent=new Intent(AddCat.this,Done.class);
        startActivity(intent);


    }

    private  class Download extends AsyncTask<Void,Void,Bitmap>
    {

        @Override
        protected Bitmap doInBackground(Void... params) {
           SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            String url = sharedPreferences.getString("img_path", "");

           // String url="https://cakesnbakes.000webhostapp.com/images/DSCN0966.JPG";

            try {

                URLConnection urlConnection= new URL(url).openConnection();
                //urlConnection.setConnectTimeout(1000*30);
                //urlConnection.setReadTimeout(1000*30);

                return BitmapFactory.decodeStream((InputStream) urlConnection.getContent());




            } catch (Exception e)
            {
                e.printStackTrace();
                return  null;
            }

        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            if(bitmap!=null)
            {
                img.setImageBitmap(bitmap);
            }
        }
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
            SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
            path = sharedPreferences.getString("img_path", "");
            dis=sharedPreferences.getString("dis", "");

            String s1=params[1];
            try {
                url=new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {

                HttpURLConnection urlconn= (HttpURLConnection) url.openConnection();

                urlconn.setDoOutput(true);
                Uri.Builder builder=new Uri.Builder()
                        .appendQueryParameter("cat_name",s1)
                        .appendQueryParameter("path",path)
                        .appendQueryParameter("dis",dis);

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

           // Intent intent=new Intent(.this,Category.class);
           // startActivity(intent);
//                SharedPreferences sharedPreferences=getSharedPreferences("data", Context.MODE_PRIVATE);
            //               SharedPreferences.Editor editor= sharedPreferences.edit();
            //              editor.putString("name",s);
            //              editor.commit();

        }
    }

}
