package dxball.linkon.siddique.dx_ball;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

import java.util.ArrayList;

public class Ball {
    Paint paint;
    int posX, posY, radius, dx, dy;

    Ball(int posX, int posY, int radius) {
        this.posX = posX;
        this.posY = posY;
        this.radius = radius;
        paint = new Paint();
        paint.setColor(Color.RED);
    }

    public void setDx(int dx) { this.dx = dx; }
    public void setDy(int dy) { this.dy = dy; }
    public int getDx() { return dx; }
    public int getDy() { return dy; }
    public int getPosX() { return posX; }
    public int getPosY() { return posY; }
    public int getRadius() { return radius; }
    public Paint getPaint() { return paint; }

    public void moveBall() {
        posX += dx;
        posY += dy;
    }

    public void ballToBoundaryCollision(Canvas canvas) {
        //left right boundary
        if ((this.posX + this.radius) >= canvas.getWidth() || (this.posX - this.radius) <= 0) {
            this.dx = -this.dx;
        }

        //up boundary
        if (this.posY - this.radius <= 0) {
            this.dy = -this.dy;
        }

        //bottom boundary
        if ((this.posY - this.radius) >= canvas.getHeight()) {
            GameCanvas.life -= 1;
            GameCanvas.newLife = true;
        }

        if (GameCanvas.life == 0) {
            GameCanvas.gameOver = true;
        }
    }

    public void ballToBarCollision(Bar bar) {
        if (((getPosY()+getRadius()) >= bar.getTop()) && ((getPosY()+getRadius()) <= bar.getBottom()) && (getPosX() >= bar.getLeft()) && (getPosX() <= bar.getRight())) {
            setDy(-(getDy()));
        }
    }

    public void ballToBrickCollision(ArrayList<Bricks> bricks) {
        for (int i=0;i<bricks.size();i++) {
            if (((getPosY()-getRadius()) <= bricks.get(i).getBottom()) && ((getPosY()+getRadius()) >= bricks.get(i).getTop()) && ((getPosX()) >= bricks.get(i).getLeft()) && ((getPosX()) <= bricks.get(i).getRight())) {
                bricks.remove(i);
                GameCanvas.score += 1;
                setDy(-(getDy()));
                if (bricks.size()==0 && GameCanvas.level < 2) {
                    GameCanvas.newLevel = true; GameCanvas.level += 1;
                }
                else if (bricks.size()==0 && GameCanvas.level > 1) {
                    GameCanvas.gameOver = true;
                }
            }
        }
    }
}
