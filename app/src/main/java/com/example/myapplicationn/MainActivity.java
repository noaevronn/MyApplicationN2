package com.example.myapplicationn;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {

    LinearLayout linearLayout;
    Board board;
    GameManager gm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        createGameInFB();

        //setContentView(board);
    }

    private void createGameInFB() {
        board = new Board(this);
        gm = new GameManager(board);


        Round r= new Round();
        r.setStatus(AppConstants.CREATED);
        r.setTime1(0.0f);
        r.setTime2(0.0f);
        r.setMyGameDeck(gm.getIndxs());



        showGame();

    }

    private void showGame() {
        linearLayout = (LinearLayout)findViewById(R.id.game);


        linearLayout.addView(board);

    }
}