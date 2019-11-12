package ktu.bombit;

import android.content.Context;
import android.graphics.Canvas;
import android.view.MotionEvent;
import android.view.SurfaceView;
import android.view.SurfaceHolder;

public class GameView extends SurfaceView implements  SurfaceHolder.Callback {

    private MainThread thread;

    private SceneManager manager;



    public GameView(Context context) {
        super(context);

        this.getHolder().addCallback(this);
        Constants.CURRENT_CONTEXT = context;

        manager = new SceneManager();

        this.setFocusable(true);

    }




    public void update(){

        manager.update();
    }



    @Override
    public void surfaceCreated(SurfaceHolder holder) {

        this.thread = new MainThread(this, holder);
        this.thread.setRunning(true);
        this.thread.start();
    }

    @Override
    public void surfaceChanged(SurfaceHolder holder, int format, int width, int height) {

    }

    @Override
    public void surfaceDestroyed(SurfaceHolder holder) {
        boolean retry = true;

        while(retry) {
            try {
                this.thread.setRunning(false);
                this.thread.join();
            } catch(InterruptedException e){
                e.printStackTrace();
            }
            retry = false;
        }

    }

    @Override
    public void draw(Canvas canvas) {
        super.draw(canvas);
        manager.draw(canvas);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        manager.receiveTouc(event);
        return true;
    }

}
