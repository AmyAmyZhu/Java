import java.awt.*;
import java.awt.event.MouseListener;

/**
 * Created by amyzhu on 16/6/20.
 */

public abstract class Model {
    protected Color color;
    protected double thick;

    protected Point p1;
    protected Point p2;

    public Model(Point p){
        color = Color.black;
        thick = 3;

        p1 = p;
        p2 = p;
    }

    public void setColor(Color c){
        color = c;
    }

    public void setP2(Point p){
        this.p2 = p;
    }

    public Point getSize(){
        return new Point(Math.abs(p2.x-p1.x), Math.abs(p2.y-p1.y));
    }

    public Point getPosition() {
        return new Point(Math.min(p1.x,p2.x), Math.min(p1.y,p2.y));
    }

    public abstract void drawModel(Graphics g);

    public void draw(Graphics g){
        g.setColor(color);
        drawModel(g);
    }

    public String toString() {
        String string = "" + p1.x;
        string += "," + p1.y;
        string += ";" + p2.x;
        string += "," + p2.y;
        string += ";" + color.getRGB();

        return string;
    }
}

class Line extends Model {
    public Line(int x, int y){
        super((new Point(x,y)));
    }
    public void drawModel(Graphics g){
        ((Graphics2D) g).setStroke(new BasicStroke((float) thick));
        g.drawLine(p1.x, p1.y, p2.x, p2.y);
    }
    public String toString(){
        return "line;"+super.toString();
    }
}

class Rectangle extends Model{
    public Rectangle(int x, int y){
        super(new Point(x, y));
    }
    public void drawModel(Graphics g){
        g.drawRect(getPosition().x,getPosition().y, getSize().x, getSize().y);
    }
    public String toString() {
        return "rect;"+super.toString();
    }
}

class Circle extends Model{
    public Circle(int x, int y){
        super(new Point(x, y));
    }
    public void drawModel(Graphics g){
        g.fillOval(getPosition().x,getPosition().y, getSize().x, getSize().y);
    }
    public String toString() {
        return "circ;"+super.toString();
    }
}
