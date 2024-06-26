package com.example.myapplicationn;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import org.w3c.dom.Document;

import java.util.ArrayList;

public class LeaderBoard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_leader_board);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });


        // get leader board - get from firebase users according to number of wins
        FirebaseFirestore fb = FirebaseFirestore.getInstance(); // for everyone
        FirebaseAuth mAuth = FirebaseAuth.getInstance();


        ArrayList<String> arr = new ArrayList<>();

        //להביא את היוזרים לפי דירוג ורק את ה10 הראשונים
        fb.collection("User").orderBy("wins", Query.Direction.DESCENDING).limit(10).get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        for(int i=0;i< queryDocumentSnapshots.getDocuments().size();i++)
                        {
                            User u = queryDocumentSnapshots.getDocuments().get(i).toObject(User.class); //מומר ליוזר

                            String toShow = "" + (i+1) + ".\t" + u.getName() + " : " + u.getWins(); //הפיכת שם השחקן למחרוזת
                            arr.add(toShow);

                        }

                        ListView lv = findViewById(R.id.listView);
                        //מתאם בין השמות לכמות הניצחונות של כל שחקן
                        ArrayAdapter<String> adapter = new ArrayAdapter<>(LeaderBoard.this, android.R.layout.simple_list_item_1,arr); //מתאם

                        lv.setAdapter(adapter);
                    }
                });

        // get this player's number of wins  and display in text view

        fb.collection("User").document(mAuth.getCurrentUser().getUid()).get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        User u = documentSnapshot.toObject(User.class);
                        TextView tv = findViewById(R.id.textViewUser);

                        tv.setText("Your number of wins is: " + u.getWins());
                    }
                });
    }

    public void BackToGame(View view)
    {
        Intent i = new Intent(this, JoinGame.class);
        startActivity(i);
    }
}