package ktu.bombit;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

public class MainThread extends Thread {

    public static final int MAX_FPS = 25;
    private SurfaceHolder surfaceHolder;
    private GameView gameView;

    private double averageFPS;

    private boolean running;
    public static Canvas canvas;


    public MainThread(GameView gameView, SurfaceHolder surfaceHolder){
        this.surfaceHolder = surfaceHolder;
        this.gameView = gameView;
    }

    public void setRunning(boolean isRunning) {
        running = isRunning;
    }

    @Override
    public void run() {
        long startTime;
        long timeMillis = 1000/MAX_FPS;
        long waitTime;
        int framCount = 0;
        long totalTime = 0;
        long targettime = 1000/MAX_FPS;

        while (running){
            canvas = null;
            startTime = System.nanoTime();
            //System.out.print(startTime);
            try {
                canvas = this.surfaceHolder.lockCanvas();
                synchronized (canvas) {
                    this.gameView.update();
                    this.gameView.draw(canvas);
                }
            }
                catch(Exception e){
            }
            finally {
                if(canvas!=null)
                {
                    this.surfaceHolder.unlockCanvasAndPost(canvas);
                }
            }
            long now = System.nanoTime();

            waitTime=(now - startTime)/1000000;

            if(waitTime < 10){
                waitTime = 10;
            }
            //System.out.print(" Wait Time="+ waitTime);

            try{
                this.sleep(waitTime);
            }catch (InterruptedException e){

            }
           timeMillis = (System.nanoTime() - startTime)/1000000;
            waitTime = targettime - timeMillis;
            System.out.print(".");
            try{
                if(waitTime > 0)
                    this.sleep(waitTime);
            }catch(Exception e ) {
                e.printStackTrace();
            }
            totalTime += System.nanoTime() - startTime;
            framCount++;

            if(framCount == MAX_FPS){
                averageFPS = 1000/((totalTime/framCount)/1000000);
                framCount = 0;
                totalTime = 0;
               System.out.println(averageFPS);
            }
        }
    }
}
