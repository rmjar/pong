package pl.rozanski.pong;

public class Ball {

    protected final static float LINEAR_VELOCITY = 1.8f;
    protected float x;
    protected float y;
    protected float d;
    protected float vx;
    protected float vy;


    protected void initBall() {
        x = 0f;
        y = 0f;
        d = 5f;
        int rnd = Pong.random.nextInt(6);
        double angle = 0;
        switch (rnd) {
            case 0:
                angle = Math.PI / 4;
                break;
            case 1:
                angle = Math.PI / 6;
                break;
            case 2:
                angle = Math.PI / 12;
                break;
            case 3:
                angle = 3 * Math.PI / 4;
                break;
            case 4:
                angle = 5 * Math.PI / 6;
                break;
            case 5:
                angle = 11 * Math.PI / 12;
        }
        int tmp = Pong.random.nextInt(2);
        if (tmp == 1) angle = -angle;

        vx = (float) (Math.cos(angle) * LINEAR_VELOCITY);
        vy = (float) (Math.sin(angle) * LINEAR_VELOCITY);
    }

    protected void setVelocity(double angle) {
        double linearVelocity = Math.sqrt(vx * vx + vy * vy);

        vx = -(float) (Math.cos(angle) * linearVelocity);
        vy = -(float) (Math.sin(angle) * linearVelocity);
    }

    protected boolean moveBall() {
        boolean colission = false;

        x += vx;
        y += vy;


        if (y <= Pong.MIN_X || y >= Pong.MAX_X) {
            vy = -vy;
            colission = true;
        }

        return colission;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getD() {
        return d;
    }
}
