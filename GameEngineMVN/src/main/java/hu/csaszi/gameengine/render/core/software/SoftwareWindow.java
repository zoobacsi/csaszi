package hu.csaszi.gameengine.render.core.software;

import hu.csaszi.gameengine.game.GameManager;
import hu.csaszi.gameengine.input.AWTInput;
import hu.csaszi.gameengine.render.core.Drawer;
import hu.csaszi.gameengine.render.core.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class SoftwareWindow extends Canvas implements Window {

    private Thread loop;

    private static int BUFFER_SIZE;
    private static JFrame FRAME;

    private String title;
    protected int width;
    protected int HEIGHT;

    private boolean shouldClose;

    protected GameManager gameManager;
    protected Drawer drawer;

    protected AWTInput AWTInput = new AWTInput();

    protected int frames, ticks, time;
    protected int lastFrames, lastTicks;

    protected final double UPDATE_SPEED = 60;
    protected static boolean isRunning;

    public SoftwareWindow(String title, int width, int height, int bufferSize, GameManager gameManager) {

        this.gameManager = gameManager;

        setName(title);
        this.title = title;
        this.width = width;
        HEIGHT = height;
        BUFFER_SIZE = bufferSize;

        Dimension size = new Dimension(this.width, HEIGHT);
        setPreferredSize(size);
        setSize(size);
        setMinimumSize(size);

        setFocusable(true);

        JPanel panel = new JPanel();
        panel.add(new JButton("valami"));

        FRAME = new JFrame(this.title);
        FRAME.setLayout(new BorderLayout());
        FRAME.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        FRAME.setSize(size);
        FRAME.setPreferredSize(size);
        FRAME.setMinimumSize(size);
//        FRAME.add(panel, BorderLayout.NORTH);
        FRAME.add(this, BorderLayout.CENTER);
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
        width = FRAME.getWidth();
        HEIGHT = FRAME.getHeight();
    }

    public void close() {
        shouldClose = true;
        System.out.println("Closing");
        FRAME.dispose();
        loop.interrupt();
        if(loop.isInterrupted()){
            System.exit(0);
        }
    }

    @Override
    public void run() {
        show();
    }

    @Override
    public String getTitle() {
        return title;
    }

    @Override
    public boolean shouldClose() {
        return shouldClose;
    }

    public void clear(Color clearColor) {

        if (!isRunning) {
            System.out.println("WINDOW NOT INITIALIZED!");
        }

        BufferStrategy bufferStrategy = this.getBufferStrategy();

        Graphics g = bufferStrategy.getDrawGraphics();
        g.setColor(clearColor);
        g.fillRect(0, 0, width, HEIGHT);

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
        this.addKeyListener(AWTInput);
        this.addMouseListener(AWTInput);
        this.addMouseMotionListener(AWTInput);
        this.addMouseWheelListener(AWTInput);

    }

    public void update() {
        if (!isRunning) {
            System.out.println("WINDOW NOT INITIALIZED!");
        }
        this.getBufferStrategy().show();
    }

    @Override
    public void clear() {

        if (!isRunning) {
            System.out.println("WINDOW NOT INITIALIZED!");
        }

        BufferStrategy bufferStrategy = this.getBufferStrategy();

        Graphics g = bufferStrategy.getDrawGraphics();
        g.setColor(Color.black);
        g.fillRect(0, 0, width, HEIGHT);

    }

    protected void gameLoop() {

        loop = new Thread(){

            public void run(){
                double lastTime = System.nanoTime();
                double delta = 0;
                final double ns = 1e9/UPDATE_SPEED;

                double start = System.currentTimeMillis();
                long next = 1L;

                while(isRunning){

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