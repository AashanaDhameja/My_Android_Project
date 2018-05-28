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
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

/**
 * Created by hp on 21-01-2018.
 */

public class CatAdapter extends ArrayAdapter{

    private List<Cat_det> detlist;
    private int resource;
    private Context context;



    public CatAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Cat_det> dets ) {
        super(context, resource, dets);
        this.context=context;
        this.resource=resource;
        this.detlist =dets;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Cat_det det=detlist.get(position);
        View view= LayoutInflater.from(context).inflate(R.layout.cat_list,parent,false);
        TextView catn= (TextView) view.findViewById(R.id.textView11);
        ImageView img= (ImageView) view.findViewById(R.id.imageView2);
        catn.setText(det.getCat_name());
        Glide.with(context).load(det.getLogo_path()).into(img);
      return  view;
    }

    @Nullable
    @Override
    public Object getItem(int position)
    {

        return detlist.get(position);
    }
}
