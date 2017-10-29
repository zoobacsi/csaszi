package hu.csaszi.gameengine.render.core.software;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.render.core.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class SoftwareWindow extends Window {

    private Thread loop;

    public SoftwareWindow(String title, int width, int height, int bufferSize, GameManager gameManager) {

        this.gameManager = gameManager;

        setName(title);
        TITLE = title;
        WIDTH = width;
        HEIGHT = height;
        BUFFER_SIZE = bufferSize;

        Dimension size = new Dimension(WIDTH, HEIGHT);
        setPreferredSize(size);
        setSize(size);
        setMinimumSize(size);

        setFocusable(true);

        FRAME = new JFrame(TITLE);
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setSize(size);
        FRAME.setPreferredSize(size);
        FRAME.setMinimumSize(size);
        FRAME.add(this);
        FRAME.pack();
        FRAME.setResizable(false);
        FRAME.setLocationRelativeTo(null);
    }

    public void setFullscreen(boolean fullscreen) {
        if (fullscreen && !isRunning) {
            System.out.println("miafasz");
            FRAME.dispose();
            FRAME.setUndecorated(true);
            FRAME.setExtendedState(JFrame.MAXIMIZED_BOTH);
            FRAME.setVisible(true);
        }
        WIDTH = FRAME.getWidth();
        HEIGHT = FRAME.getHeight();
    }

    public void close() {
        System.out.println("Closing");
        FRAME.dispose();
        loop.interrupt();
        System.exit(0);
    }

    public void clear(Color clearColor) {

        if (!isRunning()) {
            System.out.println("WINDOW NOT INITIALIZED!");
        }

        BufferStrategy bufferStrategy = this.getBufferStrategy();

        Graphics g = bufferStrategy.getDrawGraphics();
        g.setColor(clearColor);
        g.fillRect(0, 0, WIDTH, HEIGHT);

    }

    public void show() {

        this.createBufferStrategy(BUFFER_SIZE);

        isRunning = true;
        FRAME.setVisible(true);

        drawer = new SoftwareDrawer(this);

        startInputListeners();

        gameLoop();
    }

    private void startInputListeners() {
        this.addKeyListener(input);
        this.addMouseListener(mouse);
        this.addMouseMotionListener(mouse);
        this.addMouseWheelListener(mouse);

    }

    public void update() {
        if (!isRunning()) {
            System.out.println("WINDOW NOT INITIALIZED!");
        }
        this.getBufferStrategy().show();
    }

    @Override
    public void clear() {

        if (!isRunning()) {
            System.out.println("WINDOW NOT INITIALIZED!");
        }

        BufferStrategy bufferStrategy = this.getBufferStrategy();

        Graphics g = bufferStrategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, WIDTH, HEIGHT);

    }

    protected void gameLoop() {

        loop = new Thread(){

            public void run(){
                double lastTime = System.nanoTime();
                double delta = 0;
                final double ns = 1e9/UPDATE_SPEED;

                double start = System.currentTimeMillis();
                long next = 1L;

                while(isRunning()){

                    double nowTime = System.nanoTime();
                    double now = (System.currentTimeMillis() - start)/1000;
                    delta += (nowTime - lastTime)/ns;

                    lastTime = nowTime;

                    while(delta >= 1){

                        gameManager.update();

                        gameManager.render();
                        delta--;
                    }

                    if(now >= next){

                        next++;
                        time++;
                        lastFrames = frames;
                        lastTicks = ticks;
                        System.out.println("FPS: " + lastFrames + " UPS: " + lastTicks);
                        frames = 0;
                        ticks = 0;
                    }
                }
            }
        }; loop.start();
    }
}