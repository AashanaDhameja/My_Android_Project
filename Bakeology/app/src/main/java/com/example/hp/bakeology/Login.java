package com.example.hp.bakeology;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.ShareActionProvider;
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

public class Login extends AppCompatActivity {
    EditText un,pass;
    //TextView text;
    int flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        un= (EditText) findViewById(R.id.editText7);
        pass= (EditText) findViewById(R.id.editText8);
        //text= (TextView) findViewById(R.id.textView4);

        if(!isConnected(Login.this)) buildDialog(Login.this).show();
        else {
            //Toast.makeText(signup.this,"Welcome", Toast.LENGTH_SHORT).show();

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



    public void baker(View v)
    {
        flag=1;
        new check().execute("http://cakesnbakes.000webhostapp.com/loginb.php");
        //text.setText("over and out");
       // Intent intent=new Intent(getApplicationContext(),accountb.class);
        //startActivity(intent);


    }
    public void customer(View v)
    {
        flag=2;
        new check().execute("http://cakesnbakes.000webhostapp.com/testLoginc.php");
       // Intent intent=new Intent(getApplicationContext(),accountb.class);
       // startActivity(intent);

    }

    class check extends AsyncTask<String,Void,String>
    {

        String s1=un.getText().toString();
        String s2=pass.getText().toString();
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            //text.setText("heyy");

        }

        @Override
        protected String doInBackground(String... params) {
            URL url=null;
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
                        .appendQueryParameter("password",s2);

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
            if(s.equals("0"))
            {
                Toast.makeText(Login.this, "Enter  all Details", Toast.LENGTH_SHORT).show();
            }
            else if(s.equals("1"))
            {
                Toast.makeText(Login.this, "Invalid Details", Toast.LENGTH_SHORT).show();
            }
            else {
                Toast.makeText(Login.this, "Valid Details", Toast.LENGTH_SHORT).show();

                if (flag == 1) {
                    //text.setText(s);
                    Intent intent = new Intent(Login.this, accountb.class);
                    startActivity(intent);
                    SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("name", s);
                    //text.setText(s);
                    editor.commit();
                }
                if (flag == 2) {
                    Toast.makeText(Login.this, "Valid Details", Toast.LENGTH_SHORT).show();

                    //text.setText(s);
                    SharedPreferences sharedPreferences = getSharedPreferences("data", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("customer", s);
                    editor.commit();
                    Intent intent = new Intent(Login.this, accountc.class);
                    startActivity(intent);

                }
            }
            //((Activity)getApplicationContext()).finish();
//myfunc();
        }
    }

}
