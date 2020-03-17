package com.example.fireapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

public class Cartlist extends ArrayAdapter<Book> {
    private Activity context;
    private List<Book> booklist;
    public Cartlist(Activity context, List<Book> booklist) {
        super(context, R.layout.cartlist_layout,booklist);
        this.context=context;
        this.booklist=booklist;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View listviewitem=inflater.inflate(R.layout.cartlist_layout,null,true);

        TextView name=(TextView) listviewitem.findViewById(R.id.name);
        TextView author=(TextView) listviewitem.findViewById(R.id.author);
        TextView count=(TextView) listviewitem.findViewById(R.id.count);
        TextView price=listviewitem.findViewById(R.id.price);
        Button button=listviewitem.findViewById(R.id.delcart);
        //TextView count=(TextView) listviewitem.findViewById(R.id.count);

        Book book=booklist.get(position);
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
                final DatabaseReference firebasecart= FirebaseDatabase.getInstance().getReference("carts").child(uid);
                firebasecart.addListenerForSingleValueEvent(new ValueEventListener() {
                    int flag=0;
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        for(DataSnapshot booksnapshot:dataSnapshot.getChildren()){
                            Book cartbook=booksnapshot.getValue(Book.class);
                            if(cartbook.getName().equals(bookname)) {
                                flag=1;
                                if(cartbook.getCount()>1)
                                {
                                    Book new_book = new Book(bookid, bookname, authorname, genre, cartbook.getCount() - 1,bookprice);
                                    firebasecart.child(bookid).setValue(new_book);
                                }
                                else
                                    firebasecart.child(bookid).removeValue();
                                break;
                            }
                        }
                        if(flag==0){
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        return listviewitem;
    }
}
