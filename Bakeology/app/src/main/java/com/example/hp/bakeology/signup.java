package com.example.hp.bakeology;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class signup extends AppCompatActivity {
    //TextView text;
    EditText un, email, pass;
    Button b1, b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
      //  text = (TextView) findViewById(R.id.textView3);
        un = (EditText) findViewById(R.id.editText10);
        email = (EditText) findViewById(R.id.editText11);
        pass = (EditText) findViewById(R.id.editText12);

        if(!isConnected(signup.this)) buildDialog(signup.this).show();
        else {
            Toast.makeText(signup.this,"Welcome", Toast.LENGTH_SHORT).show();

        }
    }


    public boolean isConnected(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netinfo = cm.getActiveNetworkInfo();

        if (netinfo != null && netinfo.isConnectedOrConnecting()) {
            android.net.NetworkInfo wifi = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
            android.net.NetworkInfo mobile = cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);

            if((mobile != null && mobile.isConnectedOrConnecting()) || (wifi != null && wifi.isConnectedOrConnecting())) return true;
        else return false;
        } else
        return false;
    }

    public AlertDialog.Builder buildDialog(Context c) {

        AlertDialog.Builder builder = new AlertDialog.Builder(c);
        builder.setTitle("No Internet Connection");
        builder.setMessage("You need to have Mobile Data or wifi to access this. Press ok to Exit");

        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {

                finish();
            }
        });

        return builder;
    }






    public void baker(View v) {
        new check().execute("http://cakesnbakes.000webhostapp.com/log_baker.php","baker");
    }

    public void customer(View v)
    {
        new check().execute("http://cakesnbakes.000webhostapp.com/log_customer.php","customer");
    }
    class check extends AsyncTask<String,Void,String>
    {

        String s1=un.getText().toString();
        String s2=email.getText().toString();
        String s3=pass.getText().toString();

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //text.setText("heyy");

        }

        @Override
        protected String doInBackground(String... params) {
            URL url=null;
            String s4=params[1];
            try {
                url=new URL(params[0]);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            }
            try {

                HttpURLConnection urlconn= (HttpURLConnection) url.openConnection();

                urlconn.setDoOutput(true);
                Uri.Builder builder=new Uri.Builder()
                        .appendQueryParameter("username",s1)
                        .appendQueryParameter("email",s2)
                        .appendQueryParameter("password",s3)
                        .appendQueryParameter("who",s4);

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
                        str=str+val;
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
            super.onPostExecute(s);
          //  text.setText(s);

            if(s.equals("1")) {
                Toast.makeText(signup.this,"Registered",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), Login.class);
                startActivity(intent);
            }
            else if(s.equals("0")) {
                Toast.makeText(signup.this, "Enter all details", Toast.LENGTH_SHORT).show();
            }
            else {

                    Toast.makeText(signup.this, "Something went wrong"+s, Toast.LENGTH_SHORT).show();

            }

        }
    }

}
