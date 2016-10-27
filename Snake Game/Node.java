import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;

public class Node {
    int w = SnakeGame.block;
    int h = SnakeGame.block;
    int row , col;
    Direction dir = Direction.L;
    Node next = null;
    Node prev = null;

    Node(int row, int col, Direction dir) {
        this.row = row;
        this.col = col;
        this.dir = dir;
    }

    void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.drawRect(SnakeGame.block * col, SnakeGame.block * row, w, h);
    }
}