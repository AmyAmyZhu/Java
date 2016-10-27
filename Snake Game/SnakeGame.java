import org.omg.CORBA.PUBLIC_MEMBER;

import javax.swing.*;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.Image;
import java.awt.Rectangle;

public class SnakeGame extends JFrame {
    private int score = 0;
    private boolean GameOver = false;
    private int fps = 30;
    private double averageFPS = 30;
    long startTime, URDTimeMillis, waitTime,totalTime;
    int frame = 0;

    PaintThread paintThread = new PaintThread();
    Snake snake = new Snake(this);
    Fruit fruit = new Fruit();

    public static final int Width = 30;
    public static final int Height = 28;
    public static final int block = 20;

    Rectangle rect = new Rectangle(0, 40, Width*(block+1)-10,Height*(block+1)-48);

    Image offScreenImage = null;

    public void launch() {
        this.setTitle("Snakes Game");
        this.setVisible(true);
        this.setResizable(false);
        this.setBounds(100,100,Width,Height);
        this.setSize(800, 600);
        this.addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_LEFT:
                        if(snake.getDirection() != Direction.R) {
                            snake.setDirection(Direction.L);
                        }
                        break;
                    case KeyEvent.VK_RIGHT:
                        if(snake.getDirection() != Direction.L) {
                            snake.setDirection(Direction.R);
                        }
                        break;
                    case KeyEvent.VK_UP:
                        if(snake.getDirection() != Direction.D) {
                            snake.setDirection(Direction.U);
                        }
                        break;
                    case KeyEvent.VK_DOWN:
                        if(snake.getDirection() != Direction.U) {
                            snake.setDirection(Direction.D);
                        }
                        break;
                    case KeyEvent.VK_Q:
                        paintThread.pause();
                        break;
                    case KeyEvent.VK_W:
                        if(!GameOver) {
                            paintThread.unPause();
                        }
                        break;
                    case KeyEvent.VK_E:
                        paintThread.reStart();
                        break;
                }
            }
        });
        new Thread(paintThread).start();
    }

    public void stop(){
        GameOver = true;
    }

    private class PaintThread implements Runnable {
        private boolean running = true;

        int maxFrame = 30;

        long targetTime = 100/fps;
        public void run() {
            while (running) {
                startTime = System.nanoTime();

                repaint();

                try {
                    Thread.sleep(SplashScreen.speed * 20);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

                URDTimeMillis = (System.nanoTime() - startTime) / 10000000;
                waitTime = targetTime - URDTimeMillis;

                try {
                    Thread.sleep(waitTime);
                } catch (Exception e) {
                }

                totalTime += System.nanoTime() - startTime;
                frame++;
                if (frame == maxFrame) {
                    averageFPS = (10 * SplashScreen.frameRate) / ((totalTime / frame) / 10000000);
                    frame = 0;
                    totalTime = 0;
                }
            }
        }

        public synchronized void pause() {
            snake.pause = true;
        }

        public synchronized void unPause(){
            GameOver = false;
            snake.pause = false;
            notifyAll();
        }

        public synchronized void reStart() {
            score = 0;
            snake.pause = false;
            snake = new Snake(SnakeGame.this);
            GameOver = false;
            notifyAll();
        }

        public void gameOver() {
            running = false;
        }

    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.GRAY);
        g.fillRect(0,0,800,600);
        g.setColor(Color.white);
        g.fillRect(0, 40, Width*(block+1)-10,Height*(block+1)-48);
        g.setColor(Color.black);
        g.drawRect(rect.x, rect.y, rect.width, rect.height);
        g.setColor(Color.black);
        g.drawString("score:" + score, 30, 60);
        g.setColor(Color.BLACK);
        g.drawString("FPS: " + averageFPS, 30, 80);
        g.setColor(Color.BLACK);
        g.drawString("speed: " + SplashScreen.speed, 30, 100);

        g.setColor(Color.white);
        g.drawString("Command:", 650, 60);
        g.setColor(Color.white);
        g.drawString("arrow keys means", 650, 80);
        g.setColor(Color.white);
        g.drawString("left/right/up/down", 650, 100);
        g.setColor(Color.white);
        g.drawString("Pause: Q", 650, 140);
        g.setColor(Color.white);
        g.drawString("Resume: W", 650, 160);
        g.setColor(Color.white);
        g.drawString("Restart: E", 650, 180);

        if(GameOver){
            paintThread.pause();
            g.setColor(Color.black);
            g.drawString("Game Over!", 200, 300);
            g.drawString("Your score is: " + score, 200, 320);
            g.drawString("Press E to restart game", 200, 340);
        }

        g.setColor(c);
        snake.eat(fruit);
        snake.draw(g);
        fruit.draw(g);
    }

    @Override
    public void update(Graphics g) {
        if(offScreenImage == null) {
            offScreenImage = this.createImage(Width * block, Height * block);
        }
        Graphics gOff = offScreenImage.getGraphics();
        paint(gOff);
        g.drawImage(offScreenImage, 0, 0,  null);
    }

    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
}
