package pl.rozanski.pong;

public class Paddle {

    private float px = 0;
    protected float x;
    protected float y;
    protected float height;


    protected void initPaddle(boolean isLeft) {

        if (isLeft) {
            y = Pong.MAX_X / 2 - 20;
            x = Pong.MIN_X + 9;
            height = 30;
        } else {
            y = Pong.MAX_X / 2 - 20;
            x = Pong.MAX_X - 9;
            height = 30;
        }

    }


    protected void movePaddle(boolean isLeft) {
        this.y += this.px * Ball.LINEAR_VELOCITY * 2;

        if (y < Pong.MIN_X) y = Pong.MIN_X;
        if (y + height > Pong.MAX_X) y = Pong.MAX_X - height;

    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getHeight() {
        return height;
    }

    public void setPx(float px) {
        this.px = px;
    }

    public void setY(float y) {
        this.y = y;
    }
}
