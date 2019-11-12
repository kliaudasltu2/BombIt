package ktu.bombit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.widget.Switch;

import androidx.annotation.Nullable;

public class IndicatingView extends View {

    public static final int NOTEXECUTED = 0;
    public static final int INPROGRESS = 4;
    public static final int SUCCESS = 1;
    public static final int FAILED = 2;

    int state = NOTEXECUTED;

    public IndicatingView(Context context) {
        super(context);
    }

    public IndicatingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setStrokeWidth(20f);
        switch (state){
            case INPROGRESS:
                paint.setColor(Color.YELLOW);

                canvas.drawLine(getWidth()/2, 0, 0, getHeight(), paint);
                canvas.drawLine(getWidth()/2, 0, getWidth(), getHeight(), paint);
                canvas.drawLine(0, getHeight()-10, getWidth(), getHeight()-10, paint);
            break;
            case FAILED:
                paint.setColor(Color.RED);
                canvas.drawLine(0, 0, getWidth(), getHeight(), paint);
                canvas.drawLine(getWidth(), 0, 0, getHeight(), paint);
                break;
            case SUCCESS:
                paint.setColor(Color.GREEN);
                canvas.drawLine(0, getHeight()/2, getWidth()/2, getHeight(), paint);
                canvas.drawLine(getWidth()/2, getHeight(), getWidth(), 0, paint);
                paint.setColor(Color.GREEN);
                default:
                    break;

        }
    }

    public  int getState(){
        return state;
    }

    public void setState(int i){
        this.state = i;
    }
}
