package main;

import java.awt.*;
import java.awt.image.BufferStrategy;

public class Game extends Canvas implements Runnable {

    // Setting the size of the window with a 16:9 ratio
    public static final int WIDTH = 640, HEIGHT = WIDTH / 12 * 9;

    private Thread thread;
    private boolean running = false;
    private Handler handler;

    public Game(){

        handler = new Handler();
        this.addKeyListener(new KeyInput(handler));

        new Window(WIDTH,HEIGHT, "A Friggin Game", this);

        handler.addObject(new Player(WIDTH/2-32,HEIGHT/2-32,ID.Player));
        handler.addObject(new Player(WIDTH/2+64,HEIGHT/2-32,ID.Player2));

    }


    //Start the game
    public synchronized void start(){
        thread = new Thread(this);
        thread.start();
        running = true;
    }
    //Stop the game
    public synchronized void stop(){
        try{
            thread.join();
            running = false;
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    //Run the game. The below code sets the frames per second (FPS) and effectively refreshes the screen at that rate.
    public void run() {

        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1000000000 / amountOfTicks;
        double delta = 0;
        long timer = System.currentTimeMillis();
        int frames = 0;
        while (running) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;
            while (delta >= 1) {
                tick();
                delta--;
            }
            if (running)
                render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                System.out.println("FPS: " + frames);
                frames = 0;
            }
        }
        stop();
    }

    private void tick(){
        handler.tick();

    }

    // Buffer the FPS so most machines can handle it. About 250 FPS.
    private void render(){
        BufferStrategy bs = this.getBufferStrategy();
        if (bs==null){
            this.createBufferStrategy(3);
            return;
        }

        Graphics g = bs.getDrawGraphics();
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH,HEIGHT );

        handler.render(g);

        g.dispose();
        bs.show();

    }

    public static void main(String[] args) {

        new Game();

    }
}
