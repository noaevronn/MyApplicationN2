package com.example.myapplicationn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class MainActivity extends AppCompatActivity implements IView
{
    Board board;
    LinearLayout linearLayout;
    private String gameID;

    GameManager gm;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        int gameConfig = getIntent().getIntExtra(AppConstants.GAME_CONFIG,0);
        board = new Board(this);
        setContentView(R.layout.activity_main);
        linearLayout = (LinearLayout)findViewById(R.id.game);

        //single phone
        if (gameConfig== AppConstants.ONE_PHONE)
        {
            linearLayout.addView(board);
             gm = new GameManager(board); // CREATE gm OF ONE PHONE
        }
        else //two phones
        //should be passed to gm
        //which is created in board game
        {
            gameID = getIntent().getStringExtra("gameId"); //doc reference
            int player = getIntent().getIntExtra("player", 0); //owner
            showGame(gameID,player);
        }
    }

    private void showGame(String gameID,int player) {
        // 1 get game from firebase

         gm = new GameManager(board,gameID,player,this);

        /*
       FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("Rounds").document(gameID).get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Round r = documentSnapshot.toObject(Round.class);
                GameManager gm = new GameManager(board,r.getMyGameDeck());
                linearLayout.addView(board);
            }
        });


         */


    }


    public void showBoard()
    {
        linearLayout.addView(board);


    }


}