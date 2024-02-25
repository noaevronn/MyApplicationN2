package com.example.myapplicationn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class JoinGame extends AppCompatActivity {

    Board board;
    GameManager gm;
    private String gameId = "";

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

    public void ShareWithFriends(View view) //נפתח מלחיצה על התמונה של השיתוף
    {


        //אינטנט מרומז
        Intent intent = new Intent(Intent.ACTION_SEND); //אומר שרוצים לשתף מידע
        intent.setType("text/plain"); //כדי שיהיה אפשר לשתף טקסט
        intent.putExtra(Intent.EXTRA_TEXT, "Hello! this is your code for the game:" + gameId + "    .Join and play with me:) ");
        startActivityForResult(Intent.createChooser(intent, "Share using"), 1);

    }


    protected void onActivityResult(int requestCode, int resultCode, @NonNull Intent data)
    {
        //אחרי שליחת הקוד מעביר למסך הבא של המשחק עצמו בחדר שנשלח לחבר
        super.onActivityResult(requestCode, resultCode, data);

        Intent i = new Intent(this, MainActivity.class);
        i.putExtra("gameId", gameId);
        i.putExtra("player", AppConstants.HOST);
        i.putExtra(AppConstants.GAME_CONFIG, AppConstants.TWO_PHONES);
        startActivity(i);
    }

    /*
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
    */

    private void AddRoundToFb(Round round)
    {
        FirebaseFirestore fb = FirebaseFirestore.getInstance();
        fb.collection("Rounds").add(round).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference)
                    {
                        TextView tv = findViewById(R.id.codeTextV);
                        ImageView imageView = findViewById(R.id.shareImageView);
                        gameId = "AEj4y1SybgjwvAjmLpCL";// documentReference.getId();
                        tv.setText("Your game code is: " + gameId + "  .Share it with your friend!");
                        tv.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        EditText editText = findViewById(R.id.editTextCode);
                        editText.setVisibility(View.INVISIBLE);
                        TextView textView = findViewById(R.id.textViewCode);
                        textView.setVisibility(View.INVISIBLE);
                        Button b = findViewById(R.id.buttonJoin);
                        b.setVisibility(View.INVISIBLE);
/*
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
*/

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
        i.putExtra(AppConstants.GAME_CONFIG, AppConstants.ONE_PHONE);
        startActivity(i);
    }

    public void onClickJoinGame(View view)
    {
        //מציג את האדיט טקסט והכפתור של ההצטרפות לחדר
        EditText editText = findViewById(R.id.editTextCode);
        editText.setVisibility(View.VISIBLE);
        TextView textView = findViewById(R.id.textViewCode);
        textView.setVisibility(View.VISIBLE);
        Button b = findViewById(R.id.buttonJoin);
        b.setVisibility(View.VISIBLE);
        TextView tv = findViewById(R.id.codeTextV);
        tv.setVisibility(View.INVISIBLE);
        ImageView imageView = findViewById(R.id.shareImageView);
        imageView.setVisibility(View.INVISIBLE);

    }

    public void onClickToJoinRound(View view)
    {
        //לאחר הלחיצה על הכפתור של ההצטרפות לחדר
        EditText editText = findViewById(R.id.editTextCode);
        gameId = "AEj4y1SybgjwvAjmLpCL";//editText.getText().toString(); //שומר את הקוד שהמשתמש הכניס
        Intent i = new Intent(this, MainActivity.class);
        //putExtra - מוסיף מידע למסך אליו האינטנט עובר
        i.putExtra("gameId", gameId);
        i.putExtra("player", AppConstants.OTHER);
        i.putExtra(AppConstants.GAME_CONFIG, AppConstants.TWO_PHONES);
        startActivity(i);


    }
}