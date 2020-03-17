package com.example.fireapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AdminLogin extends AppCompatActivity {

  EditText uname, password;
  Button btnsignin;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_login);
    uname = findViewById(R.id.uname);
    password = findViewById(R.id.password);
    btnsignin = findViewById(R.id.signin);

    btnsignin.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        String username = uname.getText().toString();
        String pwd = password.getText().toString();
        if (username.isEmpty()) {
          uname.setError("please provide EmailId");
          uname.requestFocus();
        }
        if (pwd.isEmpty()) {
          password.setError("please enter password");
          password.requestFocus();
        } else {

          if(username.equals("admin")&&pwd.equals("admin")){
            Toast.makeText(AdminLogin.this, "Admin Panel", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(AdminLogin.this, AdminPanel.class));

          }
          else
          {
            Toast.makeText(AdminLogin.this, "Login failed", Toast.LENGTH_SHORT).show();

          }


        }
      }
    });


  }
}
