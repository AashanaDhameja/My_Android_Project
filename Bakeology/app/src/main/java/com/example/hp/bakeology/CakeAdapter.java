package com.example.hp.bakeology;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by hp on 28-01-2018.
 */

public class CakeAdapter extends ArrayAdapter {

    private List<cake_det> cakelist;
    private int resource;
    private Context context;
    

    public CakeAdapter(@NonNull Context context, @LayoutRes int resource, List<cake_det> cakeDet) {
        super(context, resource, cakeDet);
        this.context=context;
        this.resource=resource;
        this.cakelist=cakeDet;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        
        cake_det det=cakelist.get(position);

      View view= LayoutInflater.from(context).inflate(resource,parent,false);
        TextView cname= (TextView) view.findViewById(R.id.textView12);
        TextView bname= (TextView) view.findViewById(R.id.textView13);
       ImageView img= (ImageView) view.findViewById(R.id.imageView5);

        cname.setText(det.getImage_name());
        bname.setText(det.getPrice());
       Glide.with(context).load(det.getImage_path()).into(img);
        Toast.makeText(context,det.getImage_path(),Toast.LENGTH_LONG).show();
        return view;

    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return cakelist.get(position);
    }
}
