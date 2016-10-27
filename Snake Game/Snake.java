import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;

public class Snake {
    public boolean pause = false;

    public Node head = null;
    public Node tail = null;
    private int size = 0;

    private Node n = new Node(20, 30, Direction.L);
    private SnakeGame y;

    public Snake(SnakeGame y) {
        head = n;
        tail = n;
        size = 1;
        this.y = y;
        addToHead();
    }

    public void addToHead() {
        Node node = null;
        switch (head.dir) {
            case L:
                node = new Node(head.row, head.col - 1, head.dir);
                break;
            case U:
                node = new Node(head.row - 1, head.col, head.dir);
                break;
            case R:
                node = new Node(head.row, head.col + 1, head.dir);
                break;
            case D:
                node = new Node(head.row + 1, head.col, head.dir);
                break;
        }
        node.next = head;
        head.prev = node;
        head = node;
        size++;
    }

    public void draw(Graphics g) {
        if (size <= 0) return;
        move();
        for (Node n = head; n != null; n = n.next) {
            n.draw(g);
        }
    }

    private void move() {
        if(!pause){
            addToHead();
            deleteFromTail();
            checkDead();
        }
    }


    private void deleteFromTail() {
        if (size == 0) return;
        tail = tail.prev;
        tail.next = null;

    }

    public void setDirection(Direction dir){
        head.dir = dir;
    }

    public Direction getDirection(){
        return head.dir;
    }

    private void checkDead() {
        if (head.row < 2 || head.col < 0 || head.row > SnakeGame.Height || head.col > SnakeGame.Width) {
            y.stop();
        }

        for (Node n = head.next; n != null; n = n.next) {
            if (head.row == n.row && head.col == n.col) {
                y.stop();
            }
        }
    }

    public void eat(Fruit e) {
        if (this.getRect().intersects(e.getRect())) {
            e.reAppear();
            this.addToHead();
            y.setScore(y.getScore() + 5);
        }
    }

    private Rectangle getRect() {
        return new Rectangle(SnakeGame.block * head.col, SnakeGame.block * head.row, head.w, head.h);
    }
}