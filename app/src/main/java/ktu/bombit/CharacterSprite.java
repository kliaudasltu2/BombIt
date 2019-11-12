package ktu.bombit;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;

public class CharacterSprite extends GameObject{

  private static final int ROW_TOP_TO_BOTTOM = 0;
  private static final int ROW_RIGHT_TO_LEFT = 1;
  private static final int ROW_LEFT_TO_RIGHT = 2;
  private static final int ROW_BOTTOM_TO_TOP = 3;

  private boolean isMoving = false;


  private int rowUsing = ROW_LEFT_TO_RIGHT;

  private int colUsing;

  private Bitmap[] leftToRights;
  private Bitmap[] rightToLefts;
  private Bitmap[] topToBottoms;
  private Bitmap[] bottomToTops;

  public static final float Velocity = 0.5f;

  private int movingVectorX = 10;
  private int movingVectorY = 5;

  private long lastDrawNanoTime = -1;

    private int maxHp = 1000;
    private int hitPoints = maxHp;
    private Rect hitPointsRect = new Rect(5,6,7,8);
    private Rect hitPointsLeft = new Rect(5,6,7,8);
  //private GameView gameView;

  public CharacterSprite( Bitmap image, int x, int y) {
      super(image, 4,3,x,y);

      //this.gameView = gameView;

      this.topToBottoms = new Bitmap[colCount]; // 3
      this.rightToLefts = new Bitmap[colCount]; // 3
      this.leftToRights = new Bitmap[colCount]; // 3
      this.bottomToTops = new Bitmap[colCount]; // 3

      for(int col = 0; col < this.colCount; col++){
          this.topToBottoms[col] = this.createSubImageAt(ROW_TOP_TO_BOTTOM, col);
          this.rightToLefts[col]  = this.createSubImageAt(ROW_RIGHT_TO_LEFT, col);
          this.leftToRights[col] = this.createSubImageAt(ROW_LEFT_TO_RIGHT, col);
          this.bottomToTops[col]  = this.createSubImageAt(ROW_BOTTOM_TO_TOP, col);
      }
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

      if(isMoving) {
          this.colUsing++;
          if (colUsing >= this.colCount) {
              this.colUsing = 0;
          }
          // Current time in nanoseconds
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
              //this.movingVectorX = -this.movingVectorX;
          } else if (this.x > Constants.SCREEN_WIDTH - width) {
             this.x =Constants.SCREEN_WIDTH-width;
          }

          if (this.y < 0) {
              this.y = 0;
          } else if (this.y > Constants.SCREEN_HEIGHT - height) {
              this.y = Constants.SCREEN_HEIGHT - height;
              //this.movingVectorY = -this.movingVectorY;
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
    }

  public void draw(Canvas canvas){
      Bitmap bitmap = this.getCurrentMoveBitmap();
      Paint paint = new Paint();

      canvas.drawBitmap(bitmap,x,y, null);
      paint.setColor(Color.GREEN);
      canvas.drawRect(hitPointsRect, paint);
      paint.setColor(Color.RED);
      canvas.drawRect(hitPointsLeft, paint);

      this.lastDrawNanoTime= System.nanoTime();
  }

  public void setMovingVector(int movingVectorX, int movingVectorY){
      this.movingVectorX = movingVectorX;
      this.movingVectorY = movingVectorY;
  }

  public void setMoving( boolean moving){
      this.isMoving  = moving;
  }

  public Rect collider(){
      return new Rect(this.getX()+(this.width/2)+10, this.getY() + this.height/2+10, this.getX() +(this.width/2)+20, this.getY() + this.height/2+10);
  }

    public void setHP(int hp){

            this.hitPoints = hp;
    }

    public int getHp(){
        return  this.hitPoints;
    }


    public int getMaxp(){
      return this.maxHp;
    }


}
