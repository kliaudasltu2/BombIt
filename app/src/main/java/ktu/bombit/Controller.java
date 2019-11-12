package ktu.bombit;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.MotionEvent;


public class Controller {
    private RectF oval;
    private RectF navigator;


    public Controller(int x, int y){
        oval = new RectF(x-125,y-125,x+125,y+125);
        navigator = new RectF( oval.left +110, oval.top+110, oval.right -110, oval.bottom-110);
    }

    public void update(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        double distance = Math.sqrt(((x - oval.centerX()) * (x - oval.centerX())) + ((y - oval.centerY()) * (y - oval.centerY())));

        if (distance < 125) {
            navigator.left = x - 15;
            navigator.right = x + 15;
            navigator.top = y - 15;
            navigator.bottom = y + 15;
        } else {
            double temp = (distance / 125);
            navigator.left = oval.centerX() + (int) (((x - oval.centerX()) / temp)) - 15;
            navigator.right = oval.centerX() + (int) (((x - oval.centerX()) / temp)) + 15;
            navigator.top = oval.centerY() + (int) (((y - oval.centerY()) / temp)) - 15;
            navigator.bottom = oval.centerY() + (int) (((y - oval.centerY()) / temp)) + 15;
        }


    }



    public void draw(Canvas canvas){
        Paint paint = new Paint();
        paint.setColor(Color.BLACK);
        paint.setAlpha(50);
        canvas.drawOval(oval, paint);
        paint.setColor(Color.BLACK);
        canvas.drawOval(navigator, paint);
    }


    public RectF getObstacle(){
        return oval;
    }


}
