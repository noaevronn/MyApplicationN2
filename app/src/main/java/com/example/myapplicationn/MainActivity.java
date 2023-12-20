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




        linearLayout = (LinearLayout)findViewById(R.id.game);
        board = new Board(this);


        linearLayout.addView(board);

        gm = new GameManager(board);
        //setContentView(board);
    }
}