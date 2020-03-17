package com.example.fireapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;
import java.util.zip.Inflater;

public class OrderList extends ArrayAdapter<Order> {
    private Activity context;
    private List<Order> orderlist;
    public OrderList(Activity context, List<Order> orderlist) {
        super(context, R.layout.orderlist_layout,orderlist);
        this.context=context;
        this.orderlist=orderlist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater= context.getLayoutInflater();
        View listviewitem=inflater.inflate(R.layout.orderlist_layout,null,true);
        TextView date=listviewitem.findViewById(R.id.date);
        TextView bookname=listviewitem.findViewById(R.id.bookname);
        TextView price=listviewitem.findViewById(R.id.price);
        TextView address=listviewitem.findViewById(R.id.address);
        Order order=orderlist.get(position);
        Book book=order.getBook();
        date.setText(order.getDate());
        bookname.setText(book.getName());
        price.setText(book.getPrice());
        address.setText(order.getAddress());
        return listviewitem;
    }
}
