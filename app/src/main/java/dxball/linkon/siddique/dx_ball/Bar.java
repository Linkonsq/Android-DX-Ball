package dxball.linkon.siddique.dx_ball;

import android.graphics.Color;
import android.graphics.Paint;

public class Bar {
    Paint paint;
    float left, top, right, bottom;

    Bar(float left, float top, float right, float bottom) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        paint = new Paint();
        paint.setColor(Color.BLUE);
    }

    public void setLeft(float left) { this.left = left; }
    public void setTop(float top) { this.top = top; }
    public void setRight(float right) { this.right = right; }
    public void setBottom(float bottom) { this.bottom = bottom; }
    public float getLeft() { return left; }
    public float getTop() { return top; }
    public float getRight() { return right; }
    public float getBottom() { return bottom; }
    public Paint getPaint() { return paint; }
}
