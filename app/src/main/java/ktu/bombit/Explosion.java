package ktu.bombit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Explosion extends GameObject {

    private int rowIndex = 0;
    private int collIndex = -1;
    private boolean finish = false;
    private GameView gameView;
    private int xSize = 0;
    private int ySize = 0;

    private Rect colliderX;
    private Rect colliderY;

    public Explosion(Bitmap image, int x, int y, int xSize, int ySize){
        super(image, 5, 5, x,y);

        this.xSize = xSize;
        this.ySize = ySize;
    }

    public void update(){
        this.collIndex++;

       /* if(this.collIndex==0 && this.rowIndex==0){
           // GamePlayScene.PlaySoundExplosion();
        }*/
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
            for(int i = 1; i <= xSize; i++){
                canvas.drawBitmap(bitmap, this.x+(this.width-50)*i, this.y,null);
                canvas.drawBitmap(bitmap, this.x-(this.width-50)*i, this.y,null);
            }
            for(int i = 1; i <= ySize; i++){
                canvas.drawBitmap(bitmap, this.x, this.y + (this.height-50)*i,null);
                canvas.drawBitmap(bitmap, this.x, this.y - (this.height-50)*i,null);
            }
        }
    }

    public boolean isFinish() {
        return finish;
    }

    public boolean collider(Rect rect) {


        colliderX = new Rect((this.getX()-(this.width-50)*xSize)+20,this.getY()+20,(this.getX()+this.width + (this.width-50)*xSize)-20,this.getY()+this.height-20);
        colliderY = new Rect(this.getX()+20, this.getY() - ySize*(this.height-50)+20, this.getX() + this.width-20, this.getY() + ySize*(this.height-50)+this.height-20);
        if(colliderX.intersect(rect) || colliderY.intersect(rect))
            return  true;
        else
            return false;
    }

    public void  increaseSizeX(){
        xSize++;
    }

    public void increaseSizeY(){
        ySize++;
    }


}
