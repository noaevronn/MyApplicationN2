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
    private boolean listening = false;


    FirebaseFirestore fb = FirebaseFirestore.getInstance();

/*
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

 */
        public void getRound(String ref)
        {
            fb.collection("Rounds").document(ref).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                @Override
                public void onSuccess(DocumentSnapshot documentSnapshot) {
                    Round r = documentSnapshot.toObject(Round.class);
                    // let game manager know the round.
                    gameManager.roundFromFirebase(r.getMyGameDeck(),r);
                }
            });
        }



        public void handleGame(String gameID)
        {
            if(!listening) {
                listening = true;


                fb.collection("Rounds").document(gameID).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                    @Override
                    public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
                        if (value != null && value.exists()) {

                            // value holds Round object
                            Round round = value.toObject(Round.class);


                            if (round.getStatus() == AppConstants.CREATED)
                            {


                                return;
                            }

                            if (round.getStatus() == AppConstants.JOINED) {
                                gameManager.notifyViewGameStarted();
                                if(AppConstants.currentPlayer==AppConstants.HOST)
                                // timer for 3 seconds
                                 //   round.setStatus(AppConstants.STARTED);
                                    setGameStatus(gameID,AppConstants.STARTED);

                                return;
                            }
                            //מאזין אם אחד השחקנים לחץ על אחד הקלפים
                            if (round.getStatusP1() != 0) {
                                if (round.getStatusP1() == 1) {
                                    //תציג הודעה שהשחקן שהוא HOST ניצח בסיבוב הזה
                                } else {
                                    //תציג הודעה שהשחקן שהוא OTHER ניצח בסיבוב הזה
                                }
                            } else if (round.getStatusP2() != 0) {
                                if (round.getStatusP2() == 1) {
                                    //תציג הודעה שהשחקן שהוא OTHER ניצח בסיבוב הזה
                                } else {
                                    //תציג הודעה שהשחקן שהוא OTHER ניצח בסיבוב הזה
                                }
                            }
                            if (round.getStatus() == AppConstants.STARTED) //כדי שהשחקנים יתחילו עם אותם קלפים ולא יחליפו אותם כל פעם כשיש שינוי
                                gameManager.ChangeCards();
                        }
                    }
                });
            }
        }


        public void setGameStatus(String ref,int status)
        {
            fb.collection("Rounds").document(ref).update("status",status);
        }

    public void setGameManager(GameManager gameManager) {

        this.gameManager = gameManager;
    }

    public void setRound(Round currentRound, String gameId) {

        fb.collection("Rounds").document(gameId).set(currentRound);
    }

    public void updateRound(Round currentRound, String gameId, int player)
    {
        if (player == AppConstants.HOST)
        {
            fb.collection("Rounds").document(gameId).update("time1",currentRound.time1);
            fb.collection("Rounds").document(gameId).update("statusP1",currentRound.statusP1);
        }
        else
        {
            fb.collection("Rounds").document(gameId).update("time2",currentRound.time2);
            fb.collection("Rounds").document(gameId).update("statusP2",currentRound.statusP2);
        }
    }


    //לא לשכוח לסיים את ההאזנה בסוף המשחק!!!!!

}
