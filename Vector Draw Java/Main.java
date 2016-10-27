import javax.swing.*;

/**
 * Created by amyzhu on 16/6/20.
 */

// a classic MVC main function

public class Main {
    public static void main(String[] args){
        View view = new View();
        Controller controller = new Controller(view);

        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setSize(640, 480);
        view.setVisible(true);
    }
}
