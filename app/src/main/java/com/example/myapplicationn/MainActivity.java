package com.example.myapplicationn;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.LabeledIntent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.SystemClock;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.units.qual.C;

public class MainActivity extends AppCompatActivity implements IView
{
    Board board;
    LinearLayout linearLayout;

    TextView counter;
    private String gameID;
    DocumentReference ref;

    PopupWindow popUp;

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


    @Override
    protected void onResume() {
        super.onResume();

        new Thread(new Runnable() {
            @Override
            public void run() {
                SystemClock.sleep(300);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        View popupView = LayoutInflater.from(MainActivity.this).inflate(R.layout.pop_up_layout, null);

                        counter = popupView.findViewById(R.id.textView3);
                        // Create the popup window
                        popUp = new PopupWindow(
                                popupView,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                ViewGroup.LayoutParams.MATCH_PARENT,
                                true // Set this to true to allow outside touches to dismiss the popup window
                        );

                        counter.setText("" + 5);



                        popUp.showAtLocation(popupView, Gravity.CENTER, 0, 0);

                    }
                });


            }
        }).start();
    }



    private void dismissPopup()
    {
        popUp.dismiss();


    }

    private void showGame(String gameID, int player) {
        // 1 get game from firebase
         gm = new GameManager(board,gameID,player,this);
    }


    public void showBoard()
    {
         linearLayout.addView(board);
    }

    @Override
    public void showCounter() {

        CountDownTimer ctd = new CountDownTimer(5000,1000) {
            @Override
            public void onTick(long millisUntilFinished)
            {
                Toast.makeText(MainActivity.this,"counter... ",Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onFinish() {
                dismissPopup();
            }
        };
        ctd.start();
    }

    private void getRoomData()
    {
        ref.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task)
            {
                if (task .isSuccessful())
                {
                    Round round = (task.getResult().toObject(Round.class));
                    if (round.getStatus() == AppConstants.CREATED)
                    {
                        round.setStatus(AppConstants.JOINED);
                        //after the first player will do the first action the status will be changed

                    }
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Not Succeeded", Toast.LENGTH_LONG).show();
                    MainActivity.this.finish();
                }
            }
        });
    }


}