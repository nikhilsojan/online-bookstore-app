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

public class UpdatebooksActivity extends AppCompatActivity {
  TextView btnlogout;
  FirebaseAuth firebaseauth;
  ListView listviewbooks;
  DatabaseReference databasebooks;
  List<Book> books;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_updatebooks);
    databasebooks= FirebaseDatabase.getInstance().getReference("books");
    listviewbooks=findViewById(R.id.listviewbooks);
    books=new ArrayList<Book>();
    btnlogout=findViewById(R.id.logout);
    btnlogout.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        Toast.makeText(UpdatebooksActivity.this,"logged out",Toast.LENGTH_SHORT);
        startActivity(new Intent(UpdatebooksActivity.this,MainActivity.class));
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
        if(dataSnapshot.exists()) {
          for (DataSnapshot booksnapshot : dataSnapshot.getChildren()) {
            Book book = booksnapshot.getValue(Book.class);
            books.add(book);
          }
        }
        UpdateList adapter=new UpdateList(UpdatebooksActivity.this,books);
        listviewbooks.setAdapter(adapter);
      }

      @Override
      public void onCancelled(@NonNull DatabaseError databaseError) {

      }
    });
  }
}
