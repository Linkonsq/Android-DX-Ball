package dxball.linkon.siddique.dx_ball;

import android.graphics.Paint;

public class Bricks {
    Paint paint;
    int color;
    float left, top, right, bottom;

    Bricks(float left, float top, float right, float bottom, int color) {
        this.left = left;
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.color = color;
        paint = new Paint();
        paint.setColor(color);
    }

    public float getLeft() { return left; }
    public float getTop() { return top; }
    public float getRight() { return right; }
    public float getBottom() { return bottom; }
    public Paint getPaint() { return paint; }
}
