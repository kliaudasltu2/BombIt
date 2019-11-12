package ktu.bombit;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

public class IndicatorProggresBar extends View {

    private int width;
    private int height;

    public IndicatorProggresBar(Context context) {
        super(context);
    }

    public IndicatorProggresBar(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public IndicatorProggresBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        width = getWidth();
        height = getHeight();

        Paint paint = new Paint();

        int squareWidth = width / 5;

        paint.setColor(Color.rgb(179,205,224));
        canvas.drawRect(0,0, squareWidth, height, paint);

        paint.setColor(Color.rgb(100,151,177));
        canvas.drawRect(squareWidth,0, 2*squareWidth, height, paint);

        paint.setColor(Color.rgb(0,91,150));
        canvas.drawRect(2*squareWidth,0, 3*squareWidth, height, paint);

        paint.setColor(Color.rgb(3,57,108));
        canvas.drawRect(3*squareWidth,0, 4*squareWidth, height, paint);

        paint.setColor(Color.rgb(1,31,75));
        canvas.drawRect(4*squareWidth,0, 5*squareWidth, height, paint);

    }
}
