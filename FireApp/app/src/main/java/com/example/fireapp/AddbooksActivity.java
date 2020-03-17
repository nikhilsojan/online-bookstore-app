package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class AddbooksActivity extends AppCompatActivity {
    Spinner genrelist;
    EditText book,author,price,stock;
    Button add;
    DatabaseReference databasebooks;
    FirebaseAuth firebaseauth;
    int flag;
    String bookname;
    String authorname;
    String genre;
    String bookprice;
    String bookstock;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addbooks);
        firebaseauth=FirebaseAuth.getInstance();
        databasebooks= FirebaseDatabase.getInstance().getReference("books");
        genrelist=findViewById(R.id.genre);
        price=findViewById(R.id.price);
        book=findViewById(R.id.bookname);
        author=findViewById(R.id.authorname);
        add=findViewById(R.id.addbook);
        stock = findViewById(R.id.stock);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                bookname = book.getText().toString();
                authorname = author.getText().toString();
                bookprice=price.getText().toString();
                genre = genrelist.getSelectedItem().toString();
                bookstock = stock.getText().toString();
                if (bookname.isEmpty()) {
                    Toast.makeText(AddbooksActivity.this, "Enter bookname", Toast.LENGTH_SHORT).show();
                }
                if (authorname.isEmpty()) {
                    Toast.makeText(AddbooksActivity.this, "Enter authorname", Toast.LENGTH_SHORT).show();
                }
                if (genre.isEmpty()) {
                    Toast.makeText(AddbooksActivity.this, "Enter authorname", Toast.LENGTH_SHORT).show();
                }
              if (bookstock.isEmpty()) {
                Toast.makeText(AddbooksActivity.this, "Enter stock", Toast.LENGTH_SHORT).show();
              } else {
                    flag=0;
                    databasebooks.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for(DataSnapshot booksnapshot:dataSnapshot.getChildren()){
                                Book book=booksnapshot.getValue(Book.class);
                                if(book.getName().equals(bookname)) {
                                    flag=1;
                                  int stockvalue =Integer.parseInt(bookstock);
                                  Book new_book = new Book(book.getId(), bookname, authorname, genre, book.getCount() + stockvalue,bookprice);
                                    databasebooks.child(book.getId()).setValue(new_book);
                                    Toast.makeText(AddbooksActivity.this, "count updated", Toast.LENGTH_SHORT);
                                    break;
                                }
                            }
                            if(flag==0){
                                String id = databasebooks.push().getKey();
                                int stockvalue = Integer.parseInt(bookstock) ;

                                Book book = new Book(id, bookname, authorname, genre, stockvalue,bookprice);
                                bookname="";
                                assert id != null;
                                databasebooks.child(id).setValue(book).addOnCompleteListener(AddbooksActivity.this, new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if (!task.isSuccessful()) {
                                            Toast.makeText(AddbooksActivity.this, "adding failed", Toast.LENGTH_SHORT).show();
                                        } else {
                                            Toast.makeText(AddbooksActivity.this, "book added", Toast.LENGTH_SHORT).show();
                                        }

                                    }
                                });
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                //startActivity(new Intent(AddbooksActivity.this,AddbooksActivity.class));
            }
            }
        });
    }
}
