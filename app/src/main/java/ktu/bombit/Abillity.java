package ktu.bombit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Abillity {
    private Bitmap image;
    private int x;
    private int y;
    private Rect collider;

    public Abillity(Bitmap image, int x, int y){
        this.image = image;
        this.x = x;
        this.y = y;
    }

    public void update(){
    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(image, x,y, null);
    }

    public Rect collider(){
        collider = new Rect(x, y, x + image.getWidth(), y+image.getHeight());
        return collider;
    }
}
