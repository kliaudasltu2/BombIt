package ktu.bombit;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class Spider extends GameObject {


    private static final int ROW_TOP_TO_BOTTOM = 2;
    private static final int ROW_RIGHT_TO_LEFT = 1;
    private static final int ROW_LEFT_TO_RIGHT = 3;
    private static final int ROW_BOTTOM_TO_TOP = 0;
    private static final int ROW_DEAD = 4;

    private int rowUsing = ROW_LEFT_TO_RIGHT;
    private int colUsing = -1;

    private Bitmap[] leftToRights;
    private Bitmap[] rightToLefts;
    private Bitmap[] topToBottoms;
    private Bitmap[] bottomToTops;
    private Bitmap[] dead;

    public static final float Velocity = 0.30f;

    private int movingVectorX = 10;
    private int movingVectorY = 5;
    private int maxHp = 200;
    private int hitPoints = maxHp;
    private boolean isDead = false;



    private Rect hitPointsRect = new Rect(5,6,7,8);
    private Rect hitPointsLeft = new Rect(5,6,7,8);

    private Rect collider;

    private long lastDrawNanoTime = -1;

    public Spider(Bitmap image, int x, int y) {
        super(image, 5, 10, x, y);


        this.topToBottoms = new Bitmap[colCount]; // 3
        this.rightToLefts = new Bitmap[colCount]; // 3
        this.leftToRights = new Bitmap[colCount]; // 3
        this.bottomToTops = new Bitmap[colCount]; // 3
        this.dead = new Bitmap[colCount];

        for(int col = 0; col < this.colCount; col++){
            this.topToBottoms[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
            this.rightToLefts[col]  = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
            this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
            this.bottomToTops[col]  = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);
            this.dead[col] = this.createSubImageAt(ROW_DEAD, col);
        }
        hitPointsRect.set(x,this.y-30,x+this.width,y-10);
    }

    public Bitmap[] getMoveBitmaps(){
        switch (rowUsing){
            case ROW_BOTTOM_TO_TOP:
                return  this.bottomToTops;
            case ROW_LEFT_TO_RIGHT:
                return this.leftToRights;
            case ROW_RIGHT_TO_LEFT:
                return this.rightToLefts;
            case ROW_TOP_TO_BOTTOM:
                return this.topToBottoms;
            case ROW_DEAD:
                return this.dead;
            default:
                return null;
        }
    }

    public Bitmap getCurrentMoveBitmap(){
        Bitmap[] bitmaps = this.getMoveBitmaps();
        return bitmaps[this.colUsing];
    }

    public void update() {
        double percentage= (100/(double)maxHp * hitPoints)/100;
        hitPointsRect.set(this.getX(),this.getY()-30,this.getX()+(int)(this.width*percentage),this.getY()-20);
        hitPointsLeft.set(this.getX()+(int)(this.width*percentage), this.getY()-30, this.getX() + this.width, this.getY()-20);
        this.colUsing++;
        if (colUsing >= this.colCount) {
            this.colUsing = 0;
        }
        if(moving()) {

            long now = System.nanoTime();

            // Never once did draw.
            if (lastDrawNanoTime == -1) {
                lastDrawNanoTime = now;
            }
            // Change nanoseconds to milliseconds (1 nanosecond = 1000000 milliseconds).
            int deltaTime = (int) ((now - lastDrawNanoTime) / 1000000);

            // Distance moves
            float distance = Velocity * deltaTime;

            double movingVectorLength = Math.sqrt(movingVectorX * movingVectorX + movingVectorY * movingVectorY);

            // Calculate the new position of the game character.
            this.x = x + (int) (distance * movingVectorX / movingVectorLength);
            this.y = y + (int) (distance * movingVectorY / movingVectorLength);

            // When the game's character touches the edge of the screen, then change direction

            if (this.x < 0) {
                this.x = 0;
                this.movingVectorX = -this.movingVectorX;
            } else if (this.x > Constants.SCREEN_WIDTH - width) {
                this.x = Constants.SCREEN_WIDTH - width;
                this.movingVectorX = -this.movingVectorX;
            }

            if (this.y < 0) {
                this.y = 0;
                this.movingVectorY = -this.movingVectorY;
            } else if (this.y > Constants.SCREEN_HEIGHT - height) {
                this.y = Constants.SCREEN_HEIGHT - height;
                this.movingVectorY = -this.movingVectorY;
            }

            // rowUsing
            if (movingVectorX > 0) {
                if (movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                    this.rowUsing = ROW_TOP_TO_BOTTOM;
                } else if (movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                    this.rowUsing = ROW_BOTTOM_TO_TOP;
                } else {
                    this.rowUsing = ROW_LEFT_TO_RIGHT;
                }
            } else {
                if (movingVectorY > 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                    this.rowUsing = ROW_TOP_TO_BOTTOM;
                } else if (movingVectorY < 0 && Math.abs(movingVectorX) < Math.abs(movingVectorY)) {
                    this.rowUsing = ROW_BOTTOM_TO_TOP;
                } else {
                    this.rowUsing = ROW_RIGHT_TO_LEFT;
                }
            }
        }
        else{

            this.rowUsing = ROW_DEAD;
            if(this.colUsing > 3){
                this.colUsing = 0;
            }
            if(this.colUsing == 3){
                this.isDead = true;
            }
        }


    }

    public void draw(Canvas canvas){
        Bitmap bitmap = this.getCurrentMoveBitmap();
        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        canvas.drawBitmap(bitmap,x,y, null);
        canvas.drawRect(hitPointsRect, paint);
        paint.setColor(Color.RED);
        canvas.drawRect(hitPointsLeft, paint);


        this.lastDrawNanoTime= System.nanoTime();
    }

    public void setMovingVector(int movingVectorX, int movingVectorY){
        this.movingVectorX = movingVectorX;
        this.movingVectorY = movingVectorY;
    }

    public Rect collider() {
        collider = new Rect(this.getX()+20,this.getY()+20,this.getX() + this.width -20,this.getY()+this.height-20);
        return collider;
    }

    public void setHP(int hp){
        if(this.hitPoints - hp < 0){
            this.hitPoints = 0;
        }
        else
            this.hitPoints = hp;
    }

    public int getHp(){
        return  this.hitPoints;
    }

    public boolean moving(){
        if(this.hitPoints > 0){
            this.rowUsing = ROW_DEAD;
            return true;
        }
        else
            return false;
    }

    public boolean isDead(){
        return this.isDead;
    }



}
