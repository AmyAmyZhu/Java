import java.awt.*;
import java.awt.Graphics;
import java.util.Random;

/**
 * Created by amyzhu on 16/5/12.
 */
public class Fruit {
    int row, col;
    int w = SnakeGame.block;
    int h = SnakeGame.block;
    private static Random r = new Random();

    public Fruit(int row, int col){
        this.row = row;
        this.col = col;
    }

    public Fruit(){
        this(r.nextInt(SnakeGame.Width-2)+2, r.nextInt(SnakeGame.Height));
    }

    public void reAppear(){
        this.row = r.nextInt(SnakeGame.Height-2)+2;
        this.col = r.nextInt(SnakeGame.Width);
    }

    public Rectangle getRect(){
        return new Rectangle(SnakeGame.block * this.col, SnakeGame.block * this.row, this.w, this.h);
    }

    public void draw(Graphics g){
        g.setColor(Color.blue);
        g.fillOval(SnakeGame.block * col, SnakeGame.block * row, w, h);
    }

    public void setCol(int col){
        this.col = col;
    }

    public int getCol(){
        return col;
    }

    public void setRow(int row){
        this.row = row;
    }

    public int getRow(){
        return row;
    }
}
