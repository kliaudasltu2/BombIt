package ktu.bombit;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;


public class Bomb extends GameObject{

    private int rowIndex = 0;
    private int collIndex = -1;
    private boolean finish = false;


    private Bitmap image;

    public Bomb(Bitmap image, int x, int y){
        super(image, 8, 2, x,y);
    }


    public void update(){
        this.collIndex++;

        if(this.collIndex >= this.colCount){
            this.collIndex = 0;
            this.rowIndex++;

            if(this.rowIndex >= this.rowCount){
                this.finish=true;
            }
        }
    }

    public void draw(Canvas canvas)  {
        if(!finish)  {
            Bitmap bitmap= this.createSubImageAt(rowIndex,collIndex);
            canvas.drawBitmap(bitmap, this.x, this.y,null);
        }
    }

    public boolean isFinish() {
        return finish;
    }

}
