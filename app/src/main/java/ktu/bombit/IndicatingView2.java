package ktu.bombit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class IndicatingView2 extends View {

    int number = -1;
    public IndicatingView2(Context context) {
        super(context);
    }

    public IndicatingView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatingView2(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public int getNumber() {
        return this.number;
    }

    public void setNumber(int n) {
        this.number = n;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if (number != -1) {
            Paint paint = new Paint();
            paint.setColor(Color.GREEN);
            paint.setTextSize(100);
            canvas.drawText(Integer.toString(number), 0, getHeight() / 2, paint);
        }
    }
}


