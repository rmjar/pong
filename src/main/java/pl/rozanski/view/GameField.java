package pl.rozanski.view;

import pl.rozanski.enums.GameState;
import pl.rozanski.pong.*;
import pl.rozanski.scaler.Scale;
import pl.rozanski.timer.GameTimer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;

import static pl.rozanski.pong.GameInstance.GAME_INSTANCE;


public class GameField extends JPanel
        implements ActionListener, KeyListener, MouseMotionListener, ComponentListener {

    public static final int initialX = 800;
    public static final int initialY = 600;


    private final static BasicStroke basicStroke =
            new BasicStroke(2.0f);


    private GameTimer timer = new GameTimer();

    private Ellipse2D.Float ballDraw = new Ellipse2D.Float();
    private Rectangle2D.Float paddleDrawLeft = new Rectangle2D.Float();
    private Rectangle2D.Float paddleDrawRight = new Rectangle2D.Float();

    private Paddle gamePaddleLeft = null;
    private Paddle gamePaddleRight = null;

    private float mouseY;

    private Font stringFont = new FontLoader().setFont("score");

    private Greetings greetings;
    private Ball ball;

    public GameField() {
        initSurface();
        addKeyListener(this);
        addMouseMotionListener(this);
        addComponentListener(this);
        timer.initTimer(this);
        initGameInstance();
        setDrawables();
        setFocusable(true);
        greetings = new Greetings(this);
    }

    private void initGameInstance() {
        GAME_INSTANCE.initGame();
        gamePaddleLeft = GAME_INSTANCE.getPaddle(true);
        gamePaddleRight = GAME_INSTANCE.getPaddle(false);
        ball = GAME_INSTANCE.getBall();
    }


    private void initSurface() {
        setBackground(new Color(0, 0, 0, 156));
    }


    private void setDrawables() {
        ballDraw.width = ballDraw.height = Scale.scale(ball.getD(), 0, Pong.MAX_X - Pong.MIN_X, 0, getWidth() - 1);
        ballDraw.x = Scale.scale(ball.getX(), Pong.MIN_X, Pong.MAX_X, 0, getWidth() - 1) - ballDraw.width / 2;
        ballDraw.y = Scale.scale(ball.getY(), Pong.MIN_X, Pong.MAX_X, 0, getHeight() - 1) - ballDraw.height / 2;

        paddleDrawLeft.x = Scale.scale(gamePaddleLeft.getX(), Pong.MIN_X, Pong.MAX_X, 0, getWidth() - 1);
        paddleDrawLeft.y = Scale.scale(gamePaddleLeft.getY(), Pong.MIN_X, Pong.MAX_X, 0, getHeight() - 1);
        paddleDrawLeft.height = Scale.scale(gamePaddleLeft.getHeight(), 0, Pong.MAX_X - Pong.MIN_X, 0, getHeight());
        paddleDrawLeft.width = 10;

        paddleDrawRight.x = Scale.scale(gamePaddleRight.getX(), Pong.MIN_X, Pong.MAX_X, 0, getWidth() - 1);
        paddleDrawRight.y = Scale.scale(gamePaddleRight.getY(), Pong.MIN_X, Pong.MAX_X, 0, getHeight() - 1);
        paddleDrawRight.height = Scale.scale(gamePaddleRight.getHeight(), 0, Pong.MAX_X - Pong.MIN_X, 0, getHeight());
        paddleDrawRight.width = 10;

    }


    public void render(Graphics2D g2d) {

        g2d.setColor(Color.WHITE);
        drawPlayground(g2d);
        g2d.fill(ballDraw);
        g2d.setStroke(new BasicStroke(1));
        g2d.fill(paddleDrawLeft);
        g2d.fill(paddleDrawRight);

    }

    private void drawPlayground(Graphics2D g2d) {
        final float dash1[] = {16f};
        final BasicStroke dashed =
                new BasicStroke(4.0f,
                        BasicStroke.CAP_SQUARE,
                        BasicStroke.JOIN_MITER,
                        30.0f, dash1, 0.0f);


        g2d.setStroke(dashed);
        g2d.drawLine(getWidth() / 2 - 2, 6, getWidth() / 2 - 2, getHeight() - 3);
        g2d.setStroke(basicStroke);
        g2d.drawRect(2, 2, getWidth() - 4, getHeight() - 4);
        float width = g2d.getFontMetrics().stringWidth(GAME_INSTANCE.scoreToString());
        g2d.drawString(GAME_INSTANCE.scoreToString(), (getWidth() - width) / 2 - 2, 70);

    }

    private void doDrawing(Graphics g) {
        Graphics2D g2d = (Graphics2D) g;
        g2d.setBackground(this.getBackground());
        g2d.setColor(Color.WHITE);
        g2d.setFont(stringFont);
        if (GAME_INSTANCE.getGameState() == GameState.GREETINGS) {
            greetings.renderGreetings(g2d, "PONG");
        }

        if (GAME_INSTANCE.getGameState() == GameState.GAME_OVER) {
            greetings.renderGreetings(g2d, "END");
        }

        if (GAME_INSTANCE.getGameState() == GameState.MATCH) {
            GAME_INSTANCE.step();
            setDrawables();
            render(g2d);
        }
    }


    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        doDrawing(g);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public synchronized void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            gamePaddleRight.setPx(-1);
        }
        if (e.getKeyCode() == KeyEvent.VK_DOWN) {
            gamePaddleRight.setPx(1);
        }

    }

    @Override
    public synchronized void keyReleased(KeyEvent e) {
        if (GAME_INSTANCE.getGameState() == GameState.GREETINGS) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                initGameInstance();
                GAME_INSTANCE.setGameState(GameState.MATCH);
            }
        }
        if (GAME_INSTANCE.getGameState() == GameState.GAME_OVER) {
            if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                GAME_INSTANCE.setGameState(GameState.GREETINGS);
                Greetings.needsUpdate = true;
                greetings.initGreetings("PONG");
            }
        }

        if (GAME_INSTANCE.getGameState() == GameState.MATCH) {
            if (e.getKeyCode() == KeyEvent.VK_UP || e.getKeyCode() == KeyEvent.VK_DOWN) {
                gamePaddleRight.setPx(0);
            }
        }
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            System.exit(0);
        }
    }

    @Override
    public synchronized void mouseDragged(MouseEvent e) {

        mouseY = e.getY();
        gamePaddleLeft.setY(Scale.scale(mouseY, 0, getHeight(), Pong.MIN_X, Pong.MAX_X));

    }

    @Override
    public synchronized void mouseMoved(MouseEvent e) {

    }

    @Override
    public void componentResized(ComponentEvent e) {
        switch (GAME_INSTANCE.getGameState()) {
            case GREETINGS:
            case GAME_OVER:
                Greetings.needsUpdate = true;

        }

    }

    @Override
    public void componentMoved(ComponentEvent e) {

    }

    @Override
    public void componentShown(ComponentEvent e) {
        switch (GAME_INSTANCE.getGameState()) {
            case GREETINGS:
            case GAME_OVER:
                Greetings.needsUpdate = true;

        }

    }

    @Override
    public void componentHidden(ComponentEvent e) {

    }
}
