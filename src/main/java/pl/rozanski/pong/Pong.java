package pl.rozanski.pong;

import pl.rozanski.view.GameField;

import javax.swing.*;
import java.awt.*;
import java.util.Random;


public class Pong extends JFrame {

    public final static float MAX_X = 100f;
    public final static float MIN_X = -100f;
    public final static int MAX_SCORE = 3;
    public static Random random = new Random();

    public Pong() {
        initUI();
    }

    private void initUI() {
        add(new GameField());
        setTitle("Pong");
        setSize(GameField.initialX, GameField.initialY);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }


    public static void main(String[] args) {
        EventQueue.invokeLater(() ->
        {
            Pong ex = new Pong();
            ex.setVisible(true);
        });

    }

}
