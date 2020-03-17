package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
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

public class HomeActivity extends AppCompatActivity {
    Button btnlogout;
    TextView btncart,btnorders;
    FirebaseAuth firebaseauth;
    ListView listviewgenres;
    DatabaseReference databasebooks;
    List<String> genres;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseauth=FirebaseAuth.getInstance();
        databasebooks= FirebaseDatabase.getInstance().getReference("books");
        listviewgenres=findViewById(R.id.listviewgenres);
        genres=new ArrayList<String>();
        listviewgenres.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String g=genres.get(position);
                if(g=="Fiction")
                    startActivity(new Intent(HomeActivity.this,FictionActivity.class));
                else if(g=="Non-Fiction")
                    startActivity(new Intent(HomeActivity.this,NonfictionActivity.class));
                if(g=="Educational")
                    startActivity(new Intent(HomeActivity.this,EducationalActivity.class));
            }
        });
        btncart=findViewById(R.id.cart);
        btncart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this,CartActivity.class));
            }
        });
        btnorders=findViewById(R.id.orders);
        btnorders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(HomeActivity.this,OrderActivity.class));
            }
        });
        btnlogout=findViewById(R.id.logout);
        btnlogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(HomeActivity.this,"You have logged out",Toast.LENGTH_SHORT);
                firebaseauth.signOut();
                startActivity(new Intent(HomeActivity.this,MainActivity.class));
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        genres.clear();
        genres.add("Fiction");
        genres.add("Non-Fiction");
        genres.add("Educational");
        GenreList adapter=new GenreList(HomeActivity.this,genres);
        listviewgenres.setAdapter(adapter);
    }
}
