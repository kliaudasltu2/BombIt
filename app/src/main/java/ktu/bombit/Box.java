package ktu.bombit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;

import java.util.Random;

public class Box {


    private Bitmap image;
    private int x;
    private int y;
    private Random rand = new Random();
    private Rect collider;
    private int end;

    public Box(Bitmap image){
        this.image = image;
        this.x = rand.nextInt(Constants.SCREEN_WIDTH- image.getWidth());
        this.y = 0;
        end = rand.nextInt(Constants.SCREEN_HEIGHT- image.getHeight());
    }

    public void update(){
        if(this.y < end){
            this.y += 7;
        }

    }

    public void draw(Canvas canvas){

        canvas.drawBitmap(image, x,y, null);
    }

    public Rect collider(){
        collider = new Rect(x, y, x + image.getWidth(), y+image.getHeight());
        return collider;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }
}
