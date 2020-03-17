package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.nio.charset.MalformedInputException;

public class MainActivity extends AppCompatActivity {
    EditText emailId, password,state,city,number,pincode,name,housename;
    Button btnsignup;
    TextView btnsignin;
    FirebaseAuth firebaseauth;
    DatabaseReference databaseusers;
    TextView btnadmin;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        databaseusers= FirebaseDatabase.getInstance().getReference("users");
        firebaseauth=FirebaseAuth.getInstance();
        emailId=findViewById(R.id.email);
        name=findViewById(R.id.name);
        number=findViewById(R.id.number);
        password=findViewById(R.id.password);
        btnsignup=findViewById(R.id.signup);
        btnsignup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email=emailId.getText().toString();
                final String pwd=password.getText().toString();
                final String aname=name.getText().toString();
                final String anumber=number.getText().toString();
                if(email.isEmpty()){
                    emailId.setError("please provide EmailId");
                    emailId.requestFocus();
                }
                if(pwd.isEmpty()){
                    password.setError("please enter password");
                    password.requestFocus();
                }
                else{
                    firebaseauth.createUserWithEmailAndPassword(email,pwd).addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(!task.isSuccessful()){
                                Toast.makeText(MainActivity.this,"Signup unsuccessful",Toast.LENGTH_SHORT).show();
                            }
                            else{
                                String id=firebaseauth.getCurrentUser().getUid();
                                User user=new User(id,email,pwd,aname,anumber);
                                databaseusers.child(id).setValue(user);
                                startActivity(new Intent(MainActivity.this,HomeActivity.class));
                            }
                        }
                    });
                }
            }
        });
        btnsignin=findViewById(R.id.signin);
        btnsignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this,LoginActivity.class));
            }
        });
        btnadmin=findViewById(R.id.admin);
        btnadmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               startActivity(new Intent(MainActivity.this,AddbooksActivity.class));
            }
        });
    }

}
