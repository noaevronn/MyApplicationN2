package com.example.myapplicationn;

import android.app.Activity;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class FBwork
{

    private GameManager gameManager;


    FirebaseFirestore fb = FirebaseFirestore.getInstance();


    public void ReadDataFromFB(View view) {

        fb.collection("Rounds").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if (task.isSuccessful()) {
                    ArrayList<Round> rounds = new ArrayList<>();

                    //task.getResult() -> collection of documents
                    for (QueryDocumentSnapshot snapshot : task.getResult()) //עבור כל מסמך מקבלים את התוכן שלו ועוברים אחד אחד ומכניסים אותו לתוך מערך
                    {
                        rounds.add(snapshot.toObject(Round.class)); // ממיר את התוכן לראונד
                    }

                    //rounds -> מערך שמכיל את כל הראונדים
                }
            }
        });

    }
        public void getRound(String ref)
        {
            fb.collection("Rounds").document(ref).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Round r = documentSnapshot.toObject(Round.class);

                    // let game manager know the round.
                    gameManager.roundFromFirebase(r.getMyGameDeck());

                //    GameManager gm = new GameManager(board,r.getMyGameDeck());
                //    linearLayout.addView(board);
                }
            });

        }


        public void handleGame(String gameID)
        {

            fb.collection("Rounds").document(gameID).addSnapshotListener( new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                    if (value != null && value.exists()) {

                        // value holds Round object
                        Round round = value.toObject(Round.class);


                    }

                }
            });

        }


        public void setGameStatus(String ref,int status)
        {

            fb.collection("Rounds").document(ref).update("status",status);
        }

    public void setGameManager(GameManager gameManager) {

        this.gameManager = gameManager;
    }


    //לא לשכוח לסיים את ההאזנה בסוף המשחק!!!!!

}
