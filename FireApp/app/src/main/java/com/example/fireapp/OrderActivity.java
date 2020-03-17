package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class OrderActivity extends AppCompatActivity {
    ListView listvieworders;
    Button btnlogout;
    TextView btnhome;
    DatabaseReference databaseorders,databasebooks;
    FirebaseAuth firebaseauth;
    ArrayList<Order> orders;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order);
        firebaseauth=FirebaseAuth.getInstance();
        listvieworders=findViewById(R.id.listvieworders);
        String uid= Objects.requireNonNull(firebaseauth.getCurrentUser()).getUid();
        databasebooks=FirebaseDatabase.getInstance().getReference("books");
        databaseorders= FirebaseDatabase.getInstance().getReference("orders").child(uid);
        btnhome=findViewById(R.id.home);
        btnhome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OrderActivity.this,HomeActivity.class));
            }
        });
        btnlogout=findViewById(R.id.logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseauth.signOut();
                startActivity(new Intent(OrderActivity.this,MainActivity.class));
            }
        });
        orders=new ArrayList<Order>();

    }

@Override
protected void onStart() {
    super.onStart();
    databaseorders.addValueEventListener(new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
            orders.clear();
            for(DataSnapshot ordersnapshot:dataSnapshot.getChildren())
            {
                Order order=ordersnapshot.getValue(Order.class);
                orders.add(order);
            }
            OrderList adapter=new OrderList(OrderActivity.this,orders);
            listvieworders.setAdapter(adapter);
        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }
    });

    }

}
