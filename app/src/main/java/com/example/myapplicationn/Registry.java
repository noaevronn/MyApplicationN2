package com.example.myapplicationn;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;

public class Registry extends AppCompatActivity
  {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

      @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);


    }

      public void LogInClick(View view)
      {
          EditText etMail = findViewById(R.id.etEmailAddress);
          EditText etPass = findViewById(R.id.etPassword);
          String mail = etMail.getText().toString();
          String pass = etPass.getText().toString();;

          if (mail.equals("") || pass.equals(""))
          {
              return;
          }

          Intent intent = new Intent(this, MainActivity.class);
          startActivity(intent);
      }
  }