import javax.lang.model.type.ArrayType;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by amyzhu on 16/6/20.
 */
public class View2 extends JPanel implements Iterable<Model> {
    private ArrayList<Model> model;

    public View2(Dimension size){
        this.setPreferredSize(size);
        setBorder(BorderFactory.createLineBorder(Color.cyan));
        setBackground(Color.WHITE);
        model = new ArrayList<Model>(0);
    }

    public void addModel(Model newModel){
        model.add(newModel);
    }

    public Iterator<Model> iterator(){
        return model.iterator();
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        for(Model newModel: model){
            newModel.draw(g);
        }
    }
}
