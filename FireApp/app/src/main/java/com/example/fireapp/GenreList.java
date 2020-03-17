package com.example.fireapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.List;

public class GenreList extends ArrayAdapter<String> {
    private Activity context;
    private List<String> genrelist;
    public GenreList(Activity context, List<String> genrelist) {
        super(context, R.layout.list_layout,genrelist);
        this.context=context;
        this.genrelist=genrelist;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listviewitem=inflater.inflate(R.layout.genrelist_layout,null,true);

        TextView genrename=(TextView) listviewitem.findViewById(R.id.genreid);
        String genre=genrelist.get(position);
        genrename.setText(genre);

        return listviewitem;
    }
}
