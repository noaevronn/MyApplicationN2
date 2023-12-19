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
    int curretCard = 0;

    MyCircle circle1, circle2;
    Picture pic;
    int[] bitmaps;

    ArrayList<Card> deck = new ArrayList<>();


    public Board(Context context)
    {
        super(context);
        circle1 = new MyCircle(AppConstants.X, AppConstants.Y1 ,AppConstants.RADIUS);
        circle2 = new MyCircle(AppConstants.X, AppConstants.Y2 ,AppConstants.RADIUS);
        this.context = context;
        bitmaps = new int[]{R.drawable.airplane, R.drawable.artistpalette, R.drawable.ballet, R.drawable.bathtub, R.drawable.bomb, R.drawable.bowling, R.drawable.bubbles, R.drawable.camera, R.drawable.cheese, R.drawable.coral, R.drawable.corn, R.drawable.cyclone, R.drawable.doughnut, R.drawable.dragonface, R.drawable.firecracker, R.drawable.gloves, R.drawable.hat, R.drawable.infinity, R.drawable.jackolantern, R.drawable.joker, R.drawable.key, R.drawable.kite, R.drawable.locked, R.drawable.luggage, R.drawable.medal, R.drawable.microphone, R.drawable.money, R.drawable.moon, R.drawable.mountain, R.drawable.mushroom, R.drawable.nazaramulet, R.drawable.pistol, R.drawable.rocket, R.drawable.sandal, R.drawable.saxophone, R.drawable.shopping, R.drawable.snowflake, R.drawable.snowman, R.drawable.spider, R.drawable.sunglasses, R.drawable.tent, R.drawable.tiger, R.drawable.truck, R.drawable.tulip, R.drawable.umbrella, R.drawable.volcano, R.drawable.waterwave, R.drawable.wedding, R.drawable.yoyo};
        CreateDeck();

        Toast.makeText(context,"" + CheckDeck(),Toast.LENGTH_LONG).show();
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

        canvas.drawCircle(circle1.getX(), circle1.getY(), circle1.getR(), circle1.getPaint());
        canvas.drawCircle(circle2.getX(), circle2.getY(), circle2.getR(), circle2.getPaint());
// added comment


            int[] arr1 = deck.get(curretCard).getCard();
            for (int i = 0; i <arr1.length ; i++) {
                bitmap = BitmapFactory.decodeResource(getResources(), bitmaps[arr1[i]]);
                canvas.drawBitmap(bitmap, AppConstants.arrX[i], AppConstants.arrY1[i], null);
            }
            if (curretCard == deck.size() - 1)
                curretCard = 0;
            int[] arr2 = deck.get(curretCard + 1).getCard();
            for (int i = 0; i <arr2.length ; i++)
            {
                bitmap = BitmapFactory.decodeResource(getResources(), bitmaps[arr2[i]]);
                canvas.drawBitmap(bitmap, AppConstants.arrX[i], AppConstants.arrY2[i], null);
            }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event)
    {
        if (event.getAction() == MotionEvent.ACTION_DOWN)
        {
            float x = event.getX();
            float y = event.getY();
            int num = 1;
            if (y > AppConstants.Y2 - AppConstants.RADIUS)
                num = 2;
            Log.d("TOUCH", "onTouchEvent: " + x + "," + y);
            // check which image
            int i = findClickedImage(x,y, num);
            Log.d("ON TOUCH ", "onTouchEvent: " + i );
            if (i != -1)
            {
                boolean flag = CheckIfTrue(i, num);
                Log.d("check ", "flag: " +  flag);
            }
        }

        return super.onTouchEvent(event);

    }

    private boolean CheckIfTrue(int indx, int num)
    {
        if (num == 1)
        {
            int[] arr1 = deck.get(curretCard).getCard();
            int[] arr2 = deck.get(curretCard + 1).getCard();
            for (int i = 0; i <arr1 .length ; i++)
            {
                if (arr1[indx] == arr2[i])
                    return true;
            }
        }
        else
        {
            int[] arr1 = deck.get(curretCard + 1).getCard();
            int[] arr2 = deck.get(curretCard).getCard();
            for (int i = 0; i < arr1 .length ; i++)
            {
                if (arr1[indx] == arr2[i])
                    return true;
            }
        }
        return  false;
    }

    private int findClickedImage(float x, float y, int num)
    {
        if (num == 1)
        {
            int[] arr = deck.get(curretCard).getCard();
            for (int i = 0; i < arr.length; i++) {
                bitmap = BitmapFactory.decodeResource(getResources(), bitmaps[arr[i]]);

                if ((x > AppConstants.arrX[i] && x < (AppConstants.arrX[i] + bitmap.getWidth())) && (y > AppConstants.arrY1[i] && y < (AppConstants.arrY1[i] + bitmap.getHeight())))
                    return i;
            }
        }
        else
        {
            int[] arr = deck.get(curretCard + 1).getCard();
            for (int i = 0; i < arr.length; i++) {
                bitmap = BitmapFactory.decodeResource(getResources(), bitmaps[arr[i]]);

                if ((x > AppConstants.arrX[i] && x < (AppConstants.arrX[i] + bitmap.getWidth())) && (y > AppConstants.arrY2[i] && y < (AppConstants.arrY2[i] + bitmap.getHeight())))
                    return i;
            }
        }
        return  -1;
    }
}
