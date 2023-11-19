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

    public Board(Context context)
    {
        super(context);
        x = 900;
        y = 700;
        r = 500;
        this.context = context;
        paint = new Paint();
        paint.setColor(Color.YELLOW);
        paint.setStrokeWidth(12);

        bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.snowman);
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawCircle(x, y, r, paint);

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
