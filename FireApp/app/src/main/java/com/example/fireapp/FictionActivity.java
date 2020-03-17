package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FictionActivity extends AppCompatActivity {
    TextView btncart;
    Button btnlogout;
    FirebaseAuth firebaseauth;
    ListView listviewbooks;
    DatabaseReference databasebooks;
    List<Book> books;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fiction);
        firebaseauth=FirebaseAuth.getInstance();
        databasebooks= FirebaseDatabase.getInstance().getReference("books");
        listviewbooks=findViewById(R.id.listviewbooks);
        books=new ArrayList<Book>();
        btnlogout=findViewById(R.id.logout);
        btncart=findViewById(R.id.cart);
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(FictionActivity.this,CartActivity.class));
            }
        });
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(FictionActivity.this,"You have logged out",Toast.LENGTH_SHORT);
                firebaseauth.signOut();
                startActivity(new Intent(FictionActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        databasebooks.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                books.clear();
                for(DataSnapshot booksnapshot:dataSnapshot.getChildren())
                {
                    Book book=booksnapshot.getValue(Book.class);
                    if(book.getGenre().equals("Fiction"))
                    books.add(book);
                }
                BookList adapter=new BookList(FictionActivity.this,books);
                listviewbooks.setAdapter(adapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
