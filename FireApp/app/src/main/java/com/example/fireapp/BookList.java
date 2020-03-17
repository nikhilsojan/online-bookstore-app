package com.example.fireapp;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class BookList extends ArrayAdapter<Book> {
    private Activity context;
    private List<Book> booklist;
    public BookList(Activity context, List<Book> booklist) {
        super(context, R.layout.list_layout,booklist);
        this.context=context;
        this.booklist=booklist;
    }

    @NonNull
    @Override
    public View getView(int position,View convertView,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listviewitem=inflater.inflate(R.layout.list_layout,null,true);

        TextView name=(TextView) listviewitem.findViewById(R.id.name);
        TextView author=(TextView) listviewitem.findViewById(R.id.author);
        TextView count=(TextView) listviewitem.findViewById(R.id.count);
        TextView price=listviewitem.findViewById(R.id.price);
        Button button=listviewitem.findViewById(R.id.addcart);
        //TextView count=(TextView) listviewitem.findViewById(R.id.count);
        Book book=booklist.get(position);
        final int bookcount=book.getCount();
        final String bookid=book.getId();
        final String bookname=book.getName();
        final String authorname=book.getAuthor();
        final String genre=book.getGenre();
        final String bookprice=book.getPrice();
        name.setText(book.getName());
        author.setText(book.getAuthor());
        count.setText(String.valueOf(book.getCount()));
        price.setText(book.getPrice());
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth firebaseauth= FirebaseAuth.getInstance();
                FirebaseUser curruser=firebaseauth.getCurrentUser();
                String uid=curruser.getUid();
                final DatabaseReference firebasecart=FirebaseDatabase.getInstance().getReference("carts").child(uid);
                firebasecart.addListenerForSingleValueEvent(new ValueEventListener() {
                    int flag=0;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot booksnapshot:dataSnapshot.getChildren()){
                            Book cartbook=booksnapshot.getValue(Book.class);
                            if(cartbook.getName().equals(bookname)) {
                                flag=1;
                                if(cartbook.getCount()<bookcount)
                                {
                                  Toast.makeText(getContext(), "Added to Cart", Toast.LENGTH_SHORT).show();
                                  Book new_book = new Book(bookid, bookname, authorname, genre, cartbook.getCount() + 1,cartbook.getPrice());
                                    firebasecart.child(bookid).setValue(new_book);

                                }
                                else
                                {
                                    Toast.makeText(getContext(), "Quantity exceeds stock", Toast.LENGTH_SHORT).show();

                                }

                                break;
                            }
                        }
                        if(flag==0){

                            int count = 1;
                            Book new_book = new Book(bookid, bookname, authorname, genre, 1,bookprice);
                            assert bookid != null;
                            firebasecart.child(bookid).setValue(new_book);
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        //count.setText(book.getCount());
        return listviewitem;
    }
}
