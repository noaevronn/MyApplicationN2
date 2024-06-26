package com.example.myapplicationn;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.Dialog;
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

    ActivityResultLauncher<Intent> mActivityResultLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
          //      if (result.getResultCode() == Activity.RESULT_OK) {
                    // Handle the returned data here
                    Intent i = new Intent(this, MainActivity.class);
                    i.putExtra("gameId", gameId);
                    i.putExtra("player", AppConstants.HOST);
                    i.putExtra(AppConstants.GAME_CONFIG, AppConstants.TWO_PHONES);
                    startActivity(i);

      //          }
            }
    );
    public void ShareWithFriends(View view) //נפתח מלחיצה על התמונה של השיתוף
    {
        //אינטנט מרומז
        Intent intent = new Intent(Intent.ACTION_SEND); //אומר שרוצים לשתף מידע
        intent.setType("text/plain"); //כדי שיהיה אפשר לשתף טקסט
        intent.putExtra(Intent.EXTRA_TEXT, "Hello! this is your code for the game:" + gameId + "    .Join and play with me:) ");
        mActivityResultLauncher.launch(intent);
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
                        gameId = documentReference.getId();//"AEj4y1SybgjwvAjmLpCL";// documentReference.getId();
                        tv.setText("Your game code is: " + gameId + "  .Share it with your friend!");
                        tv.setVisibility(View.VISIBLE);
                        imageView.setVisibility(View.VISIBLE);
                        EditText editText = findViewById(R.id.editTextCode);
                        editText.setVisibility(View.INVISIBLE);
                        TextView textView = findViewById(R.id.textViewCode);
                        textView.setVisibility(View.INVISIBLE);
                        Button b = findViewById(R.id.buttonJoin);
                        b.setVisibility(View.INVISIBLE);

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
        gameId = editText.getText().toString();//"AEj4y1SybgjwvAjmLpCL";//editText.getText().toString(); //שומר את הקוד שהמשתמש הכניס
        Intent i = new Intent(this, MainActivity.class);
        //putExtra - מוסיף מידע למסך אליו האינטנט עובר
        i.putExtra("gameId", gameId);
        i.putExtra("player", AppConstants.OTHER);
        i.putExtra(AppConstants.GAME_CONFIG, AppConstants.TWO_PHONES);
        startActivity(i);


    }

    public void GamesRules(View view)
    {
        Dialog dialog = new Dialog(this);
        dialog.setContentView(R.layout.custom_dialog_box_instructions);
        dialog.show();
    }

    public void GoToLeaderBoard(View view)
    {
        Intent i = new Intent(this, LeaderBoard.class);
        startActivity(i);
    }
}