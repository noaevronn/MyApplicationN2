package com.example.myapplicationn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class JoinGame extends AppCompatActivity {

    Board board;
    GameManager gm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join_game);
    }

    public void onClickCreateGameInFb(View view)
    {
        board = new Board(this);
        gm = new GameManager(board);

        Round r = new Round();
        r.setStatus(AppConstants.CREATED);
        r.setTime1(0.0f);
        r.setTime2(0.0f);
        r.setMyGameDeck(gm.getIndxs());
        AddRoundToFb(r);

    }

    public  void setClickToChat(String message){
        String url = "https://api.whatsapp.com/send";
        try {
            PackageManager pm = this.getPackageManager();
            pm.getPackageInfo("com.whatsapp", PackageManager.GET_ACTIVITIES);
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(url));
            i.putExtra(Intent.EXTRA_TEXT, message);

            startActivity(i);
        } catch (PackageManager.NameNotFoundException e) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        }
    }

    private void AddRoundToFb(Round round)
    {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("Rounds").add(round).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        TextView tv = findViewById(R.id.codeTextV);
                        ImageView imageView = findViewById(R.id.shareImageView);
                        tv.setText("Your game code is: " + documentReference.getId() + "  .Share it with your friend!");
                        tv.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);

                        imageView.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                // image view has been clicked
                        //        setClickToChat("invited to join my game... Code for the game is:\n" + documentReference.getId());
                                Log.d("ONSUCCESS", " id " +documentReference.getId());
                                Intent i = new Intent(JoinGame.this,MainActivity.class);
                                i.putExtra("GameID",documentReference.getId());
                                i.putExtra("action","CREATED");
                                startActivity(i);

                            }
                        });

                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e)
                    {
                        Log.d("ONFAILRE", "onFailure: " + e.getMessage());

                    }
                });
    }


    public void onClickPractice(View view)
    {
        Intent i = new Intent(JoinGame.this, MainActivity.class);
        startActivity(i);
    }

    public void onClickJoinGame(View view)
    {

    }
}