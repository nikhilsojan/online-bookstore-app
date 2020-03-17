package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.StringTokenizer;

public class CheckoutActivity extends AppCompatActivity {
    EditText dname,demail,dphone,daddress;
    DatabaseReference databaseorders;
    DatabaseReference databasecarts;
    DatabaseReference databasebooks;
    FirebaseAuth firebaseauth;
    String order_name,order_email,order_address,order_phone;
    Button checkout;
    Book order_book;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        firebaseauth=FirebaseAuth.getInstance();
        String uid=firebaseauth.getCurrentUser().getUid();
        databaseorders= FirebaseDatabase.getInstance().getReference("orders").child(uid);
        databasecarts= FirebaseDatabase.getInstance().getReference("carts").child(uid);
        databasebooks=FirebaseDatabase.getInstance().getReference("books");
        dname=findViewById(R.id.fullname);
        demail=findViewById(R.id.email);
        dphone=findViewById(R.id.phone);
        daddress=findViewById(R.id.address);

        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        final String date=df.format(dateobj);
        checkout=findViewById(R.id.checkout);

        checkout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                order_name=dname.getText().toString();
                order_email=demail.getText().toString();
                order_phone=dphone.getText().toString();
                order_address=daddress.getText().toString();
             if(order_name.isEmpty())
             {
                 Toast.makeText(CheckoutActivity.this, "name can't be empty"+order_email, Toast.LENGTH_SHORT).show();
                 return;
             }
             if(order_phone.isEmpty())
             {
                 Toast.makeText(CheckoutActivity.this, "phone can't be empty", Toast.LENGTH_SHORT).show();
                 return;
             }
             if(order_email.isEmpty())
             {
                 Toast.makeText(CheckoutActivity.this, "Email can't be empty", Toast.LENGTH_SHORT).show();
                 return;
             }
             if(order_address.isEmpty())
             {
                 Toast.makeText(CheckoutActivity.this, "address can't be empty", Toast.LENGTH_SHORT).show();
                 return;
             }
             else if(!order_name.isEmpty()&&!order_phone.isEmpty()&&!order_email.isEmpty()&&!order_address.isEmpty())
             {
                 databasecarts.addListenerForSingleValueEvent(new ValueEventListener() {
                     @Override
                     public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                         for(DataSnapshot cartbooksnapshot:dataSnapshot.getChildren())
                         {
                             final Book cartbook=cartbooksnapshot.getValue(Book.class);
                             order_book=cartbook;
                             databasebooks.addListenerForSingleValueEvent(new ValueEventListener() {
                                 @Override
                                 public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                     for(DataSnapshot booksnapshot:dataSnapshot.getChildren())
                                     {
                                         Book book=booksnapshot.getValue(Book.class);
                                         if(book.getId().equals(cartbook.getId()))
                                         {
                                             Book new_book=new Book(book.getId(),book.getName(),book.getAuthor(),book.getGenre(),book.getCount()-cartbook.getCount(),book.getPrice());
                                             if(new_book.getCount()==0)
                                                 databasebooks.child(book.getId()).removeValue();
                                             else
                                                databasebooks.child(book.getId()).setValue(new_book);
                                             databasecarts.child(cartbook.getId()).removeValue();
                                             break;
                                         }
                                     }
                                 }

                                 @Override
                                 public void onCancelled(@NonNull DatabaseError databaseError) {

                                 }
                             });
                             String id=databaseorders.push().getKey();
                             Order order=new Order(id,order_name,order_email,order_phone,order_address,date,order_book) ;
                             databaseorders.child(id).setValue(order);
                         }



                     }

                     @Override
                     public void onCancelled(@NonNull DatabaseError databaseError) {

                     }
                 });
                 startActivity(new Intent(CheckoutActivity.this,OrderActivity.class));
             }

            }

        });
    }
}
