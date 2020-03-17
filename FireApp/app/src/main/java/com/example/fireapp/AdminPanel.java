package com.example.fireapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class AdminPanel extends AppCompatActivity {

  Button addBook,delBook,update;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_admin_panel);
    addBook = findViewById(R.id.addBook);
    delBook = findViewById(R.id.delBook);
    update = findViewById(R.id.update);

    addBook.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(AdminPanel.this, AddbooksActivity.class));

      }
    });
    delBook.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(AdminPanel.this, DeletebooksActivity.class));
      }
    });
    update.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        startActivity(new Intent(AdminPanel.this, UpdatebooksActivity.class));
      }
    });
  }
}
