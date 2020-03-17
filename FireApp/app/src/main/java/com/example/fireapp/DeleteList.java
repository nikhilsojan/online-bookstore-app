package com.example.fireapp;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

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

public class DeleteList extends ArrayAdapter<Book> {
    Activity context;
    List<Book> booklist;
    public DeleteList(Activity context, List<Book> booklist) {
        super(context, R.layout.deletelist_layout,booklist);
        this.context=context;
        this.booklist=booklist;
    }
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        final View listviewitem=inflater.inflate(R.layout.deletelist_layout,null,true);

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
                final DatabaseReference firebasebooks = FirebaseDatabase.getInstance().getReference("books");
                firebasebooks.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        EditText qty=listviewitem.findViewById(R.id.qty);
                        if(qty.getText().toString().equals(""))
                        {
                            Toast.makeText(getContext(),"quantity can't be empty",Toast.LENGTH_SHORT).show();
                            return;
                        }
                        final int qc=Integer.parseInt(qty.getText().toString());

                        int flag=0;
                        for(DataSnapshot booksnapshot:dataSnapshot.getChildren()){
                            Book cartbook=booksnapshot.getValue(Book.class);
                            if(cartbook.getId().equals(bookid)) {
                                flag=1;
                                if(cartbook.getCount()-qc>0)
                                {
                                    Book new_book = new Book(bookid, bookname, authorname, genre, cartbook.getCount() - qc,bookprice);
                                    firebasebooks.child(bookid).setValue(new_book);
                                }
                                else
                                    firebasebooks.child(bookid).removeValue();
                                break;
                            }
                        }
                        if(flag==0){
                        }
                        //return;
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
