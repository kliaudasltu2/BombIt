package ktu.bombit;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.media.SoundPool;
import android.view.MotionEvent;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class GamePlayScene implements  Scene {

    private boolean gameOver = false;
    private long gameOvertime;

    private int enemyCount = 0;

    private int maxBombCount = 1;
    private int damage = 3;
    private int explosionXSize = 0;
    private int explosionYSize = 0;
    private Bitmap[] abilityBitmap = new Bitmap[4];
    private int abilityNumber = 0;



    private int waveCount = 0;
    private  CharacterSprite mainPlayer;
    private  List<Explosion> explosionList = new ArrayList<>();
    private  List<Bomb> bmb = new ArrayList<>();
    private  List<Spider> spiders = new ArrayList<>();
    private  List<Imp> imps = new ArrayList<>();

    private Controller moveController = new Controller(0,0);
    private ShootController bButton = new ShootController();
    private Abillity ability;
    private Box box;


    private static final int max_streams = 100;
    private int soundIdExplosion;
    private int soundIdBackground;

    private boolean soundPoolLoaded;
    private SoundPool soundPool;
    private boolean showController = false;

    private Random ran = new Random();


    public GamePlayScene(){

        Bitmap chibiBitmap1 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(),R.drawable.character);
        mainPlayer = new CharacterSprite(chibiBitmap1,100,100);

        Bitmap img1 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.increasex);
        Bitmap img2 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.increasey);
        Bitmap img3 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.boom);
        Bitmap img4 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.strength);


        abilityBitmap[0] = img1;
        abilityBitmap[1] = img2;
        abilityBitmap[2] = img3;
        abilityBitmap[3] = img4;

    }

    @Override
    public void update() {

        if (gameOver == false) {
            wave();
            mainPlayer.update();
            //Bombs update
            List<Bomb> tempBombList = new ArrayList<Bomb>();
            for (Bomb b : bmb) {
                if (b.isFinish()) {
                    Bitmap bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.explosion);
                    explosionList.add(new Explosion(bitmap, b.getX() - b.width / 2, b.getY() - b.height / 2, explosionXSize, explosionYSize));
                    // explosionList.add(new Explosion(bitmap, b.getX()-b.width/2+b.width,b.getY()-b.height/2));
                    // explosionList.add(new Explosion(bitmap, b.getX()-b.width/2-b.width,b.getY()-b.height/2));

                    tempBombList.add(b);
                }
            }

            for (Bomb tB : tempBombList) {
                bmb.remove(tB);
            }

            for (Bomb b : bmb) {
                b.update();
            }

            List<Spider> tempSpirderList = new ArrayList<Spider>();
            List<Imp> tempImpList = new ArrayList<Imp>();
            for (Explosion expl : explosionList) {
                for (Spider spider : spiders) {
                    if (expl.collider(spider.collider())) {
                        spider.setHP(spider.getHp() - damage);
                    }
                    if (spider.isDead()) {
                        enemyCount++;
                        tempSpirderList.add(spider);
                    }
                }
                if (box != null) {
                    if (expl.collider(box.collider())) {
                        abilityNumber = ran.nextInt(4);
                        ability = new Abillity(abilityBitmap[abilityNumber], box.getX(), box.getY());
                        box = null;

                    }
                }

                for (Imp imp : imps) {
                    if (expl.collider(imp.collider())) {
                        imp.setHP(imp.getHp() - damage);
                    }
                    if (imp.getHp() <= 0) {
                        enemyCount++;
                        tempImpList.add(imp);
                    }
                }
            }

            if (box != null) {
                box.update();
            }

            if (ability != null) {
                if (mainPlayer.collider().intersect(ability.collider())) {
                    Upgrade();
                    ability = null;
                }
            }

            for (Spider spdr : tempSpirderList) {
                spiders.remove(spdr);
            }

            for (Spider spider : spiders) {
                spider.update();
            }

            for (Imp imp : tempImpList) {
                imps.remove(imp);
            }

            for (Imp imp : imps) {
                imp.setMovingVector(mainPlayer.getX(), mainPlayer.getY());
                //imp.isMoving(mainPlayer.collider());
                imp.update();
            }


            List<Explosion> tempExplosionList = new ArrayList<Explosion>();
            for (Explosion exp : explosionList) {
                if (exp.isFinish()) {
                    tempExplosionList.add(exp);
                }
            }

            for (Explosion exp : tempExplosionList) {
                explosionList.remove(exp);
            }

            for (Explosion explosion : this.explosionList) {
                explosion.update();
            }


            for (Imp imp : imps) {
                if (mainPlayer.collider().intersect(imp.collider())) {
                    mainPlayer.setHP(mainPlayer.getHp() - 3);
                }
            }

            for (Spider spider : spiders) {
                if (mainPlayer.collider().intersect(spider.collider())) {
                    mainPlayer.setHP(mainPlayer.getHp()-30);
                }
            }

            if (mainPlayer.getHp() <= 0) {
                gameOver = true;
                gameOvertime = System.currentTimeMillis();
            }

        }
       // System.out.print(gameOver);
    }

    @Override
    public void draw(Canvas canvas) {
        canvas.drawColor(Color.YELLOW);
        bButton.draw(canvas);

        for(Bomb b: bmb){
            b.draw(canvas);
        }

        if(box != null){
            box.draw(canvas);
        }

        if(ability != null){
            ability.draw(canvas);
        }

        for(Explosion explosion: this.explosionList)  {
            explosion.draw(canvas);
        }

        for(Spider spider: spiders){

            spider.draw(canvas);
        }

        for(Imp imp: imps){
            imp.draw(canvas);
        }

        mainPlayer.draw(canvas);

        if(showController) {
            moveController.draw(canvas);
        }

        if(gameOver){
            drawGameOver(canvas, "Game Over", 300,canvas.getWidth()/2, canvas.getHeight()/2);
        }

        drawGameOver(canvas, Integer.toString(enemyCount), 100, 100,100
        );



    }

    @Override
    public void terminate() {
        SceneManager.ACTIVE_SCENE = 0;
    }

    @Override
    public void receiveTouch(MotionEvent event) {

        //int id = event.getPointerId(0);
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                moveController = new Controller((int) event.getX(), (int) event.getY());

                int x = (int) event.getX();
                int y = (int) event.getY();

                if (event.getPointerCount() == 1 && bButton.Collider((int) event.getX(), (int) event.getY()) && explosionList.size() < maxBombCount && bmb.size() < maxBombCount) {
                    Bitmap bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.bomb);
                    Bomb bbmb = new Bomb(bitmap, mainPlayer.getX(), mainPlayer.getY());
                    this.bmb.add(bbmb);
                }

                if (gameOver && System.currentTimeMillis() - gameOvertime >= 2000) {
                    this.gameOver = false;
                    Reset();
                }
                break;

            case MotionEvent.ACTION_MOVE:
                if (bButton.Collider((int) event.getX(), (int) event.getY()) == false) {
                    showController = true;

                    mainPlayer.setMoving(true);

                    moveController.update(event);

                    int movingVectorX = (int) event.getX() - (int) (moveController.getObstacle().centerX());
                    int movingVectorY = (int) event.getY() - (int) (moveController.getObstacle().centerY());
                    mainPlayer.setMovingVector(movingVectorX, movingVectorY);
                }

                break;

            case MotionEvent.ACTION_UP:

                showController = false;
                mainPlayer.setMoving(false);
                break;

            case MotionEvent.ACTION_POINTER_DOWN:
                if (bButton.Collider((int) event.getX(event.getPointerId(1)), (int) event.getY(event.getPointerId(1))) && explosionList.size() < maxBombCount && bmb.size() < maxBombCount) {
                    Bitmap bitmap = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.bomb);
                    Bomb bbmb = new Bomb(bitmap, mainPlayer.getX(), mainPlayer.getY());
                    this.bmb.add(bbmb);
                }
                break;
        }
    }

    private void wave() {
        if (spiders.size() == 0 && explosionList.size() == 0) {
            waveCount++;
            Bitmap img = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.spider01);
            Bitmap img1 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.spider08);
            Bitmap img2 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.spider09);
            Bitmap img3 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.impsword);
            Bitmap img4 = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.impattacksword);
            for (int i = 0; i < waveCount; i++) {
                Spider spider = new Spider(img, ran.nextInt(2000), ran.nextInt(1500));
                spider.setMovingVector(ran.nextInt(2000), ran.nextInt(1500));
                spiders.add(spider);

              /*  Spider spider1 = new Spider(img1, ran.nextInt(2000), ran.nextInt(1500));
                spider1.setMovingVector(ran.nextInt(2000), ran.nextInt(1500));
                spiders.add(spider1);

                Spider spider2 = new Spider(img2, ran.nextInt(2000), ran.nextInt(1500));
                spider2.setMovingVector(ran.nextInt(2000), ran.nextInt(1500));
                spiders.add(spider2);*/
//
                Imp imp = new Imp(img3, img4, ran.nextInt(1800), ran.nextInt(800));
                imps.add(imp);
            }
            Bitmap imgBox = BitmapFactory.decodeResource(Constants.CURRENT_CONTEXT.getResources(), R.drawable.box);
            box = new Box(imgBox);
        }
    }

    private void Upgrade(){
        switch (abilityNumber){
            case 0:
                explosionXSize++;
                break;
            case 1:
                explosionYSize++;
                break;
            case 2:
                maxBombCount++;
                break;
            case 3:
                damage = damage + 1;
        }
    }

    private void Reset(){

         maxBombCount = 1;
         damage = 3;
         explosionXSize = 0;
         explosionYSize = 0;
         waveCount = 0;
         enemyCount = 0;

         mainPlayer.setHP(mainPlayer.getMaxp());
         spiders = new ArrayList<Spider>();
         imps = new ArrayList<>();
         bmb = new ArrayList<>();
         explosionList = new ArrayList<>();

    }

    private void drawGameOver(Canvas canvas, String text, int size, int x, int y){
        Paint textPaint = new Paint();
        textPaint.setARGB(200, 254, 0, 0);
        textPaint.setTextAlign(Paint.Align.CENTER);
        textPaint.setTextSize(size);
        canvas.drawText(text, x, y  , textPaint);
    }
}

