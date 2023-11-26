package com.example.myapplicationn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

public class Board extends View
{
    Context context;
    float x, y, r;
    Paint paint;
    Bitmap bitmap;

    MyCircle circle;
    Picture pic;

    public Board(Context context)
    {
        super(context);
        circle = new MyCircle(900,700,500);
    //    x = 900;
     //   y = 700;
       // r = 500;
        this.context = context;
    //    paint = new Paint();
      //  paint.setColor(Color.YELLOW);
       // paint.setStrokeWidth(12);

        //pic = new Picture();
        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snowman);

    }



    @Override
    protected void onDraw(Canvas canvas) {
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
