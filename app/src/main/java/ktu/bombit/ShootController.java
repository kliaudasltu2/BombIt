package ktu.bombit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;


public class ShootController {

    private RectF button;


    public ShootController(){

        button = new RectF( Constants.SCREEN_WIDTH - 400, Constants.SCREEN_HEIGHT -300,Constants.SCREEN_WIDTH, Constants.SCREEN_HEIGHT);
    }

    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAlpha(50);
        canvas.drawOval(button, paint);
        paint.setAlpha(255);
        paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setTextSize(100);
        canvas.drawText("BOOM" ,Constants.SCREEN_WIDTH  - 350, Constants.SCREEN_HEIGHT -125, paint);
    }

    public boolean Collider(int x, int y){
        if(x > button.left && x < button.right
           && y > button.top && y < button.bottom)
            return true;
        else
            return false;
    }



}
