package com.example.myapplicationn;

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



    public void ReadDataFromFB(View view) {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();

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
/*
        public void listenForChanges()
        {
            //1. collection
            //2. document ref
            //3. listen
            //display data - איך מציגים את המידע כשמקבלים אותו

            FirebaseFirestore fb = FirebaseFirestore.getInstance();
            String docRef = "";
            fb.collection("Rounds").document(docRef).addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
                @Override
                public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {

                }
            });
        }


 */
    }
        public void getRound(String ref)
        {
            FirebaseFirestore fb = FirebaseFirestore.getInstance();
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

    public void setGameManager(GameManager gameManager) {

        this.gameManager = gameManager;
    }


    //לא לשכוח לסיים את ההאזנה בסוף המשחק!!!!!

}
