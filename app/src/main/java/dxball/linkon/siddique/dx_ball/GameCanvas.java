package dxball.linkon.siddique.dx_ball;

import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

public class GameCanvas extends View implements Runnable{
    Paint paint;
    Bar bar;
    Ball ball;
    boolean firstTime = true;
    float brickPosX = 0, brickPosY = 100;
    float canvasHeight = 0, canvasWidth = 0;
    int color, ballSpeed = 10;
    float barWidth = 200;
    static boolean gameOver = false, newLife = false,  newLevel = false;
    static int life = 3, score = 0, level = 1;
    ArrayList<Bricks> bricks = new ArrayList<Bricks>();

    public GameCanvas(Context context) {
        super(context);
        paint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvasHeight = canvas.getHeight();
        canvasWidth = canvas.getWidth();

        if (firstTime) {
            firstTime = false;
            //canvasHeight = canvas.getHeight();
            //canvasWidth = canvas.getWidth();

            //Bricks
            for (int i=0; i<9; i++) {
                if (brickPosX >= canvasWidth) { brickPosX = 0; brickPosY += 140; }

                if (i%2 == 0) { color = Color.GREEN; }
                else { color = Color.BLUE; }

                bricks.add(new Bricks(brickPosX, brickPosY, brickPosX+canvasWidth/3, brickPosY+140, color));
                brickPosX += canvasWidth / 3;
            }

            //Bar
            bar = new Bar(canvasWidth / 2 - (barWidth / 2), canvasHeight - 30, canvasWidth / 2 + (barWidth / 2), canvasHeight);

            //Ball
            ball = new Ball(canvas.getWidth()/2, canvas.getHeight()-65, 30);

            ball.setDx(ballSpeed);
            ball.setDy(-ballSpeed);
        }

        if (newLevel) {
            newLevel = false;
            brickPosX = 0; brickPosY = 100;
            //Bricks
            for (int i=0; i<15; i++) {
                if (brickPosX >= canvasWidth) { brickPosX = 0; brickPosY += 140; }

                if (i%2 == 0) { color = Color.GREEN; }
                else { color = Color.BLUE; }

                bricks.add(new Bricks(brickPosX, brickPosY, brickPosX+canvasWidth/5, brickPosY+140, color));
                brickPosX += canvasWidth / 5;
            }

            //Bar
            bar = new Bar(canvasWidth / 2 - (barWidth / 2), canvasHeight - 30, canvasWidth / 2 + (barWidth / 2), canvasHeight);

            //Ball
            ball = new Ball(canvas.getWidth()/2, canvas.getHeight()-65, 30);

            ball.setDx(ballSpeed + 3);
            ball.setDy(-ballSpeed + 3);
        }

        if (newLife) {
            newLife = false;
            //Ball
            ball = new Ball(canvas.getWidth()/2, canvas.getHeight()-65, 30);
            ball.setDx(ballSpeed);
            ball.setDy(-ballSpeed);
        }

        if (gameOver) {
            gameOver = false;
            //paint.setColor(Color.BLACK);
            //canvas.drawRect(0, 0, canvasWidth, canvasHeight, paint);
            paint.setColor(Color.RED);
            paint.setTextSize(100);
            canvas.drawText("Game Over", canvasWidth/2, canvasHeight/2, paint);
            canvas.drawText("Total Score: " + score, canvasWidth/2, canvasHeight/2+100, paint);

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            ((PlayGame)getContext()).finish();
        }

//        calculateNextPos();
//
//        canvas.drawRGB(255, 255, 255);
//        paint.setColor(Color.RED);
//        paint.setStyle(Paint.Style.FILL);
//        canvas.drawCircle(x, y, 40, paint);
//        canvas.drawRect(left, top, right, bottom, paint);

        //Text
        paint.setTextSize(70);
        paint.setColor(Color.BLACK);
        canvas.drawText("Life: " + life, 10, 60, paint);
        canvas.drawText("Level: " + level, canvasWidth/2-100, 60, paint);
        canvas.drawText("Score: " + score, canvasWidth-300, 60, paint);

        //Draw Bricks
        for (int i=0; i<bricks.size(); i++) {
            canvas.drawRect(bricks.get(i).getLeft(), bricks.get(i).getTop(), bricks.get(i).getRight(), bricks.get(i).getBottom(), bricks.get(i).getPaint());
        }

        //Draw Bar
        canvas.drawRect(bar.getLeft(), bar.getTop(), bar.getRight(), bar.getBottom(), bar.getPaint());

        //Draw Ball
        canvas.drawCircle(ball.getPosX(), ball.getPosY(), ball.getRadius(), ball.getPaint());

        ball.ballToBrickCollision(bricks);
        ball.ballToBarCollision(bar);
        ball.ballToBoundaryCollision(canvas);
        ball.moveBall();

        this.run();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (event.getX() > canvasWidth-barWidth) {
                bar.setLeft(canvasWidth - barWidth);
                bar.setRight(event.getX() + barWidth);
                return true;
            }
            else {
                bar.setLeft(event.getX());
                bar.setRight(event.getX() + barWidth);
                return true;
            }
        }

        if (event.getAction() == MotionEvent.ACTION_MOVE) {
            if (event.getX() > canvasWidth-barWidth) {
                bar.setLeft(canvasWidth - barWidth);
                bar.setRight(event.getX() + barWidth);
                return true;
            }
            else {
                bar.setLeft(event.getX());
                bar.setRight(event.getX() + 200);
                return true;
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void run() {
        invalidate();
    }
}
