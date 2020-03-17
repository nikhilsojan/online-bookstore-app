package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {
    Button btnlogout,btnbuy;
    FirebaseAuth firebaseauth;
    ListView listviewbooks;
    DatabaseReference databasecarts;
    List<Book> books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        firebaseauth=FirebaseAuth.getInstance();
        final String uid=firebaseauth.getCurrentUser().getUid();
        Toast.makeText(CartActivity.this,uid,Toast.LENGTH_SHORT);
        databasecarts= FirebaseDatabase.getInstance().getReference("carts").child(uid);
        //DatabaseReference cartitem= databasecarts.child(email);
        listviewbooks=findViewById(R.id.listviewbooks);
        books=new ArrayList<Book>();
        btnlogout=findViewById(R.id.logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(CartActivity.this,"logged out",Toast.LENGTH_SHORT);
                firebaseauth.signOut();
                startActivity(new Intent(CartActivity.this,MainActivity.class));
            }
        });
        btnbuy=findViewById(R.id.buy);
        btnbuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CartActivity.this,CheckoutActivity.class));
            }
        });
    }
    @Override
    protected void onStart() {
        super.onStart();
        databasecarts.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                books.clear();
                if(dataSnapshot.exists()) {
                    for (DataSnapshot booksnapshot : dataSnapshot.getChildren()) {
                        Book book = booksnapshot.getValue(Book.class);
                        books.add(book);
                    }
                }
                Cartlist adapter=new Cartlist(CartActivity.this,books);
                listviewbooks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
