package com.example.myapplicationn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;

public class
Board extends View
{
    Context context;
    Bitmap bitmap;
    MyCircle circle1, circle2;
    int[] bitmaps;

    Card userCard = null;
    Card deckCard = null;
    private GameManager gameManager;


    public Board(Context context)
    {
        super(context);

        initBoard(context);

        circle1 = new MyCircle(AppConstants.X, AppConstants.Y1 ,AppConstants.RADIUS);
        circle2 = new MyCircle(AppConstants.X, AppConstants.Y2 ,AppConstants.RADIUS);
        this.context = context;
        bitmaps = new int[]{R.drawable.airplane, R.drawable.artistpalette, R.drawable.ballet, R.drawable.bathtub, R.drawable.batman,R.drawable.bomb, R.drawable.bowling, R.drawable.bubbles, R.drawable.camera, R.drawable.cheese, R.drawable.coral, R.drawable.corn, R.drawable.cyclone, R.drawable.doughnut, R.drawable.dragonface, R.drawable.firecracker,R.drawable.fleurdelis,R.drawable.footprint, R.drawable.gloves, R.drawable.grinch, R.drawable.hat, R.drawable.infinity, R.drawable.jackolantern, R.drawable.jerry, R.drawable.joker, R.drawable.key, R.drawable.kite, R.drawable.locked, R.drawable.luggage, R.drawable.medal, R.drawable.microphone, R.drawable.money, R.drawable.moon, R.drawable.mountain, R.drawable.mushroom, R.drawable.nazaramulet, R.drawable.pistol, R.drawable.rocket, R.drawable.sandal, R.drawable.saxophone, R.drawable.shopping, R.drawable.smurf, R.drawable.snowflake, R.drawable.snowman, R.drawable.spider, R.drawable.sunglasses, R.drawable.tent, R.drawable.theflash, R.drawable.tiger, R.drawable.tower, R.drawable.truck, R.drawable.tulip, R.drawable.umbrella, R.drawable.volcano, R.drawable.waterwave, R.drawable.wedding, R.drawable.yoyo};
    }

    private void initBoard(Context context) {
        DisplayMetrics dm =getResources().getDisplayMetrics();
        AppConstants.X = dm.widthPixels/2;
        AppConstants.Y1 = dm.heightPixels/4;
        AppConstants.Y2 = dm.heightPixels/2 + dm.heightPixels/4;
        AppConstants.RADIUS = dm.heightPixels/6;
        AppConstants.arrX =new float[] {AppConstants.X, (float) (AppConstants.X - 0.7*AppConstants.RADIUS), (float) ((AppConstants.X - AppConstants.RADIUS) + (0.1 * AppConstants.RADIUS)), (float) (AppConstants.X - 0.5*AppConstants.RADIUS), AppConstants.X, (float) (AppConstants.X + 0.5 * AppConstants.RADIUS), (float) ((AppConstants.X + AppConstants.RADIUS) - (0.3 * AppConstants.RADIUS)), (float) (AppConstants.X + 0.5*AppConstants.RADIUS)};
        AppConstants.arrY1 = new float[] {(float) ((AppConstants.Y1 - AppConstants.RADIUS) + (0.1*AppConstants.RADIUS)), (float) (AppConstants.Y1-0.6*AppConstants.RADIUS), AppConstants.Y1, (float) (AppConstants.Y1+ 0.6*AppConstants.RADIUS), (float) ((AppConstants.Y1 + AppConstants.RADIUS) - (0.3*AppConstants.RADIUS)), (float) (AppConstants.Y1+ 0.5*AppConstants.RADIUS), AppConstants.Y1, (float) (AppConstants.Y1 - 0.7*AppConstants.RADIUS)};
        AppConstants.arrY2 = new float[] {(float) ((AppConstants.Y2 - AppConstants.RADIUS) + (0.1*AppConstants.RADIUS)), (float) (AppConstants.Y2-0.6*AppConstants.RADIUS), AppConstants.Y2, (float) (AppConstants.Y2+ 0.6*AppConstants.RADIUS), (float) ((AppConstants.Y2 + AppConstants.RADIUS) - (0.3*AppConstants.RADIUS)), (float) (AppConstants.Y2+ 0.5*AppConstants.RADIUS), AppConstants.Y2, (float) (AppConstants.Y2 - 0.7*AppConstants.RADIUS)};

    }

    public void makeTurn(Card c1,Card c2)
    {
        this.userCard = c1;
        this.deckCard =c2;

        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas)
        {
        super.onDraw(canvas);


        //


        if(userCard == null || deckCard ==null)
            return;

        canvas.drawCircle(circle1.getX(), circle1.getY(), circle1.getR(), circle1.getPaint());
        canvas.drawCircle(circle2.getX(), circle2.getY(), circle2.getR(), circle2.getPaint());

            int[] arr1 = userCard.getCard();
            for (int i = 0; i <arr1.length ; i++) {
                bitmap = BitmapFactory.decodeResource(getResources(), bitmaps[arr1[i]-1]);
                canvas.drawBitmap(bitmap, AppConstants.arrX[i], AppConstants.arrY1[i], null);
            }
            int[] arr2 = deckCard.getCard();
            for (int i = 0; i <arr2.length ; i++)
            {
                bitmap = BitmapFactory.decodeResource(getResources(), bitmaps[arr2[i]-1]);
                canvas.drawBitmap(bitmap, AppConstants.arrX[i], AppConstants.arrY2[i], null);
            }

            Paint p = new Paint();
            p.setColor(Color.WHITE);

            p.setTextSize(80);
            canvas.drawText("My Card's number: "+AppConstants.cardCounter,100,100,p);//



            //START TIME...
            AppConstants.startTime = System.currentTimeMillis();

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

            // check which image
            int i = findClickedImage(x,y, num);
            Log.d("ON TOUCH ", "onTouchEvent: " + i );
            if (i != -1)
            {
                boolean res = CheckIfTrue(i, num);
                gameManager.userResult(res);
            }
        }
        return super.onTouchEvent(event);

    }

    private boolean CheckIfTrue(int indx, int num)
    {
        if (num == 1)
        {
            int[] arr1 = userCard.getCard();
            int[] arr2 = deckCard.getCard();
            for (int i = 0; i <arr1 .length ; i++)
            {
                if (arr1[indx] == arr2[i])
                    return true;
            }
        }
        else
        {
            int[] arr1 = userCard.getCard();
            int[] arr2 = deckCard.getCard();
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
            int[] arr = userCard.getCard();
            for (int i = 0; i < arr.length; i++) {
                bitmap = BitmapFactory.decodeResource(getResources(), bitmaps[arr[i]-1]);

                if ((x > AppConstants.arrX[i] && x < (AppConstants.arrX[i] + bitmap.getWidth())) && (y > AppConstants.arrY1[i] && y < (AppConstants.arrY1[i] + bitmap.getHeight())))
                    return i;
            }
        }
        else
        {
            int[] arr = deckCard.getCard();
            for (int i = 0; i < arr.length; i++) {
                bitmap = BitmapFactory.decodeResource(getResources(), bitmaps[arr[i]-1]);

                if ((x > AppConstants.arrX[i] && x < (AppConstants.arrX[i] + bitmap.getWidth())) && (y > AppConstants.arrY2[i] && y < (AppConstants.arrY2[i] + bitmap.getHeight())))
                    return i;
            }
        }
        return  -1;
    }

    public void setGameManager(GameManager gameManager) {
        this.gameManager = gameManager;
    }
}
