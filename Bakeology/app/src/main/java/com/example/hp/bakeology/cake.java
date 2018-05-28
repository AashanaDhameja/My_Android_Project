package com.example.hp.bakeology;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
import java.util.ArrayList;

public class cake extends AppCompatActivity implements AdapterView.OnItemClickListener {
    TextView textView;
    String s1;
    ListView listView;
    //TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cake);
        listView= (ListView) findViewById(R.id.list_view);
        //textView= (TextView) findViewById(R.id.textView14);
        listView.setOnItemClickListener(this);


        Cat_det det= (Cat_det) getIntent().getExtras().getSerializable("Cat_data");
      /*  if(det!=null)
        {

            textView.setText(det.getCat_name());
        }
*/


        new check().execute("http://cakesnbakes.000webhostapp.com/cake.php",det.getCat_name());
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        Intent intent=new Intent(this, sel_cake.class);
        intent.putExtra("selected",(cake_det)adapterView.getItemAtPosition(i));
        startActivity(intent);
    }

    class check extends AsyncTask<String,Void,String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

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

                s1=params[1];

                urlconn.setDoOutput(true);
                Uri.Builder builder=new Uri.Builder()
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
                String s=bufferedReader.readLine();
                bufferedReader.close();
                return  s;



            } catch (IOException e) {
                e.printStackTrace();
            }


            return null;

        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //textView.setText(s);
           try {
                JSONObject jsonObject=new JSONObject(s);

                ArrayList<cake_det> cakelist=new ArrayList<>();

                JSONArray jsonArray=jsonObject.getJSONArray("res");
                for(int i=0;i<jsonArray.length();i++)
                {

                    JSONObject object=jsonArray.getJSONObject(i);
                    cake_det det=new cake_det();
                    det.setBaker(object.getString("baker"));
                    det.setFlavour(object.getString("flavour"));
                    det.setPrice(object.getString("price"));
                    det.setDiscription(object.getString("discription"));
                    det.setCategory(object.getString("category"));
                    det.setImage_path(object.getString("image_path"));
                    det.setImage_name(object.getString("image_name"));
                    cakelist.add(det);
                }

                CakeAdapter cakeAdapter=new CakeAdapter(cake.this,R.layout.catcake,cakelist);
                listView.setAdapter(cakeAdapter);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
