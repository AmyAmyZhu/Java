import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created by amyzhu on 16/5/26.
 */
public class SplashScreen extends JFrame {
    public static final int viewWidth = 40;
    public static final int viewHeight = 30;
    public static final int block = 20;

    public static int frameRate = 30;
    public static int speed = 5;

    public void launch(){
        this.setTitle("Snakes Game");
        this.setVisible(true);
        this.setResizable(false);
        this.setBounds(100,100,viewWidth,viewHeight);
        this.setSize(viewWidth*block,viewHeight*block);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch(e.getKeyCode()){
                    case KeyEvent.VK_ENTER:
                        new SnakeGame().launch();
                        break;
                }
            }
        });
    }

    public static void main(String[] args){
        /*frameRate = Integer.parseInt(args[0]);
        speed = Integer.parseInt(args[1]);
        if(frameRate > 100 || frameRate < 1 || speed < 1 || speed > 10){
            System.out.println("Out of Range: Please re-enter frame-rate(1-100) and speed(1-10).");

        } else {*/
            new SplashScreen().launch();
        //}
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.black);
        g.drawString("Welcome to Snakes Game!", 100, 60);
        g.drawString("Name: Zefang Zhu", 100, 100);
        g.drawString("Userid: z57zhu", 100, 120);
        g.drawString("Command:", 100, 160);
        g.drawString("arrow keys means", 100, 180);
        g.drawString("left/right/up/down", 100, 200);
        g.drawString("Pause: Q", 100, 240);
        g.drawString("Resume: W", 100, 260);
        g.drawString("Restart: E", 100, 280);

        g.setColor(Color.blue);
        g.drawString("Current speed is: "+speed, 100, 320);
        if(speed < 3) {
            g.drawString("the speed is fast.", 100,340);
        } else if(speed < 7) {
            g.drawString("the speed is median.", 100,340);
        } else {
            g.drawString("the speed is slow.", 100,340);
        }
        g.setColor(Color.RED);
        g.drawString("Press Enter to start Game!", 100, 380);
    }
}
