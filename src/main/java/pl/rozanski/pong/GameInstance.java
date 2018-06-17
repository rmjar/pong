package pl.rozanski.pong;


import pl.rozanski.audio.Audio;
import pl.rozanski.enums.GameState;
import pl.rozanski.scaler.Scale;
import pl.rozanski.view.Greetings;

public enum GameInstance {
    GAME_INSTANCE;

    private Ball ball= new Ball();

    private int[] score = {0, 0};
    private GameState gameState = GameState.GREETINGS;
    private String won = "";


    private Paddle paddleLeft = new Paddle();
    private Paddle paddleRight = new Paddle();


    private Audio effect1 = new Audio("audio/oldschool14.au");
    private Audio effect2 = new Audio("audio/oldschool37.au");
    private Audio effect3 = new Audio("audio/oldschool42.au");

    public void initGame() {
        score[0] = score[1] = 0;
        setBallAndPaddles();
    }


    private boolean detectCollision() {

        if (ball.x >= (paddleRight.x - ball.d / 2) &&
                ball.x <= (paddleRight.x + 2 * ball.d / 3) &&
                (ball.y + ball.d / 2) >= paddleRight.y &&
                (ball.y - ball.d / 2) <= (paddleRight.y + paddleRight.height) &&
                ball.vx > 0) {

            double angle = (double) Scale.scale(ball.y - paddleRight.y,
                    0,
                    paddleRight.height,
                    (float) (2 * Math.PI / 6),
                    (float) (-2 * Math.PI / 6));

            ball.setVelocity(angle);
            return true;
        }

        if (ball.x <= (paddleLeft.x + ball.d / 2) &&
                ball.x >= (paddleLeft.x - 2 * ball.d / 3) &&
                (ball.y + ball.d / 2) >= paddleLeft.y &&
                (ball.y - ball.d / 2) <= (paddleLeft.y + paddleLeft.height) &&
                ball.vx < 0) {

            double angle = (double) Scale.scale(ball.y - paddleLeft.y,
                    0,
                    paddleLeft.height,
                    (float) (4 * Math.PI / 6),
                    (float) (8 * Math.PI / 6));

            ball.setVelocity(angle);
            return true;
        }

        return false;
    }


    public void step() {
        if (ball.moveBall()) {
            effect1.play();
        }

        if (detectCollision()) {
            effect2.play();
        }


        if (ball.x >= Pong.MAX_X || ball.x <= Pong.MIN_X) {
            increaseScore();
            changeGameOver();
            effect3.play();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
            }
            setBallAndPaddles();
        }


        paddleLeft.movePaddle(true);
        paddleRight.movePaddle(false);
    }

    private void setBallAndPaddles() {
        ball.initBall();
        paddleLeft.initPaddle(true);
        paddleRight.initPaddle(false);
    }

    private void changeGameOver() {
        if (score[0] > Pong.MAX_SCORE || score[1] > Pong.MAX_SCORE) {
            if (score[0] > Pong.MAX_SCORE) {
                won = "LEFT";
            } else {
                won = "RIGHT";
            }
            gameState = GameState.GAME_OVER;
            Greetings.needsUpdate = true;
        }
    }

    private void increaseScore() {
        if (ball.x >= Pong.MAX_X) {
            score[0]++;
        } else {
            score[1]++;
        }
    }

    public String scoreToString() {
        return score[0] + "     " + score[1];
    }

    public Paddle getPaddle(boolean isLeft) {
        if (isLeft) return paddleLeft;
        return paddleRight;
    }

    public GameState getGameState() {
        return gameState;
    }

    public int[] getScore() {
        return score;
    }

    public void setGameState(GameState gameState) {
        if (gameState != GameState.GAME_OVER) {
            this.gameState = gameState;
        }
    }

    public Ball getBall() {
        return ball;
    }

    public String getWon() {
        return won;
    }
}
