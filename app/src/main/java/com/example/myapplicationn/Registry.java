package com.example.myapplicationn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Registry extends AppCompatActivity
  {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();


      @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registry);

        if (mAuth.getCurrentUser() != null) //אומר שכבר נרשמנו לאפליקציה וצריך לעבור לדף הבא ישר
        {
            String mail = mAuth.getCurrentUser().getEmail();
            String userId = mAuth.getCurrentUser().getUid();
            Toast.makeText(this, userId, Toast.LENGTH_LONG).show();
            Intent intent = new Intent(Registry.this, JoinGame.class);
            startActivity(intent);
        }
    }

      public void LogInClick(View view)
      {
          EditText etMail = findViewById(R.id.etEmailAddress);
          EditText etPass = findViewById(R.id.etPassword);
          EditText etName = findViewById(R.id.eTName);

          if(TextUtils.isEmpty(etMail.getText()) || TextUtils.isEmpty(etName.getText()) || TextUtils.isEmpty(etPass.getText()))
          {
              Toast.makeText(this, "One of your fields is empty!!", Toast.LENGTH_LONG).show();
              return;
          }

          String mail = etMail.getText().toString();
          String pass = etPass.getText().toString();
          // read username
          String name = etName.getText().toString();

          mAuth.createUserWithEmailAndPassword(mail, pass)
                  .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                      @Override
                      public void onComplete(@NonNull Task<AuthResult> task)
                      {
                          if (task.isSuccessful() == true) // הרישום הצליח
                          {
                              Toast.makeText(Registry.this, "Registry Success", Toast.LENGTH_LONG).show();


                              // create User object - with default values and use the username entered
                              User user = new User(name);
                              // add the user object to FB collection users
                              FirebaseFirestore fb = FirebaseFirestore.getInstance();
                              fb.collection("User").document(mAuth.getCurrentUser().getUid()).set(user)
                                      .addOnSuccessListener(new OnSuccessListener<Void>() {
                                          @Override
                                          public void onSuccess(Void unused) {
                                              Intent intent = new Intent(Registry.this, JoinGame.class);
                                              startActivity(intent);
                                          }
                                      });
                          }
                          else
                          {
                              String failureReason = task.getException().toString();
                              Toast.makeText(Registry.this,"register failed " + failureReason,Toast.LENGTH_SHORT).show();
                          }
                      }
                  });
      }
  }