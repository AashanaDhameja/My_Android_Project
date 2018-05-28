package com.example.hp.bakeology;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public class SelCat extends AppCompatActivity implements AdapterView.OnItemClickListener{
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sel_cat);
        listView= (ListView) findViewById(R.id.list_view1);
        listView.setOnItemClickListener(SelCat.this);
        // textView=findViewById(R.id.textView5);
        new SelCat.check().execute("https://cakesnbakes.000webhostapp.com/select.php");
    }
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int i, long l) {

        Intent intent=new Intent(this,AddSelCat.class);
        intent.putExtra("Cate_data",(Cat_det)parent.getItemAtPosition(i));
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
            try {
                JSONObject jsonObject=new JSONObject(s);

                ArrayList<Cat_det> catlist=new ArrayList<>();

                JSONArray jsonArray=jsonObject.getJSONArray("res");
                for(int i=0;i<jsonArray.length();i++)
                {

                    JSONObject object=jsonArray.getJSONObject(i);
                    Cat_det det=new Cat_det();
                    det.setCat_name(object.getString("cat_name"));
                    det.setLogo_path(object.getString("logo_path"));
                    catlist.add(det);

                }

                CatAdapter catAdapter=new CatAdapter(SelCat.this,R.layout.cat_list,catlist);
                listView.setAdapter(catAdapter );

            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

}
