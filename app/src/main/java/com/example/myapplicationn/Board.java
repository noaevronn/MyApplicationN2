package com.example.myapplicationn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class Board extends View
{
    Context context;
    float x, y, r;
    Paint paint;
    Bitmap bitmap;

    MyCircle circle;
    Picture pic;
    int[] bitmaps;

    ArrayList<Card> deck = new ArrayList<>();


    public Board(Context context)
    {
        super(context);
        circle = new MyCircle(AppConstants.X, AppConstants.Y ,AppConstants.RADIUS);
        this.context = context;

        //pic = new Picture();


        bitmaps = new int[]{R.drawable.airplane, R.drawable.artistpalette, R.drawable.ballet, R.drawable.bathtub, R.drawable.bomb, R.drawable.bowling, R.drawable.bubbles, R.drawable.camera, R.drawable.cheese, R.drawable.coral, R.drawable.corn, R.drawable.cyclone, R.drawable.doughnut, R.drawable.dragonface, R.drawable.firecracker, R.drawable.gloves, R.drawable.hat, R.drawable.infinity, R.drawable.jackolantern, R.drawable.joker, R.drawable.key, R.drawable.kite, R.drawable.locked, R.drawable.luggage, R.drawable.medal, R.drawable.microphone, R.drawable.money, R.drawable.moon, R.drawable.mountain, R.drawable.mushroom, R.drawable.nazaramulet, R.drawable.pistol, R.drawable.rocket, R.drawable.sandal, R.drawable.saxophone, R.drawable.shopping, R.drawable.snowflake, R.drawable.snowman, R.drawable.spider, R.drawable.sunglasses, R.drawable.tent, R.drawable.tiger, R.drawable.truck, R.drawable.tulip, R.drawable.umbrella, R.drawable.volcano, R.drawable.waterwave, R.drawable.wedding, R.drawable.yoyo};

      //  deck.add(new Card());

        CreateDeck();

        showCard(0);
        Toast.makeText(context,"" + CheckDeck(),Toast.LENGTH_LONG).show();

    }

    private void showCard(int index) {
        int[] arr = deck.get(index).getCard();

        for (int i = 0; i <arr.length ; i++)
        {
            bitmap = BitmapFactory.decodeResource(getResources(), bitmaps[arr[i]]);
            bitmap.setWidth((int) AppConstants.arrX[i]);
            bitmap.setHeight((int) AppConstants.arrY[i]);
        }
    }

    public void CreateDeck()
    {
        int n = 7;
        int numOfSymbols = n + 1;
        Card card = new Card();

        for (int i = 1; i <= numOfSymbols; i++)  //build the first card
        {
            card.addImage(i);
        }
        deck.add(card);

        for (int j = 1; j <= n; j++)  //build the next n number of cards
        {
            card = new Card();
            card.addImage(1);

            for (int k = 1; k <= n; k++) {
                card.addImage(n * j + (k + 1));
            }
            deck.add(card);
        }

        for (int i = 1; i <= n; i++) //build the next n² number of cards
        {
            for (int j = 1; j <= n; j++)
            {
                card = new Card();
                card.addImage(i + 1);

                for (int k = 1; k <= n; k++) {
                    card.addImage(n + 2 + n * (k - 1) + (((i - 1) * (k - 1) + j - 1) % n));
                }
                deck.add(card);
            }
        }


    }

    public boolean CheckDeck()
    {
        for (int i = 0; i < deck.size(); i++)
        {
            int[] arr1  = deck.get(i).getCard();
            int index = i;
            int counter = 0;


            for (int j = 0; j < deck.size(); j++)
            {
                if (index != j)
                {
                    int[] arr2  = deck.get(j).getCard();
                    int count = checkArr(arr1, arr2);
                    if (count != 1) {
                        Log.d("Card Check", "CheckDeck: " + i +"," +j);
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public int checkArr(int[] arr1, int[] arr2)
    {
        int count = 0;
        for (int i = 0; i < arr1.length; i++)
        {
            for (int k = 0; k < arr2.length; k++)
            {
                if (arr1[i] == arr2[k])
                    count ++;
            }
        }

        return count;
    }

    @Override
    protected void onDraw(Canvas canvas)
        {
        super.onDraw(canvas);

        canvas.drawCircle(circle.getX(), circle.getY(), circle.getR(), circle.getPaint());
// added comment
        canvas.drawBitmap(bitmap,x, y, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            float x = event.getX();
            float y = event.getY();
            Log.d("TOUCH", "onTouchEvent: " + x + "," + y);
        }

        return super.onTouchEvent(event);

    }
}
