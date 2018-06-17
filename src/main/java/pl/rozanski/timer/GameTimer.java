package pl.rozanski.timer;

import javax.swing.*;
import java.awt.event.ActionListener;

public class GameTimer {

    private final int DELAY = 20;
    private final int INITIAL_DELAY = 150;
    private Timer timer;


    public void initTimer(ActionListener listener) {
        timer = new Timer(DELAY, listener);
        timer.setInitialDelay(INITIAL_DELAY);
        timer.start();
    }

}
