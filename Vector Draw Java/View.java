import javax.swing.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

import static java.lang.System.out;

/**
 * Created by amyzhu on 16/6/20.
 */
public class View extends JFrame{
    private MainPanel mainPanel;
    private ToolPanel toolPanel;

    private JScrollPane scrollPane;
    private Controller controller;

    private MouseListener mouse;

    JFileChooser fileChooser; // for save and load file

    class MouseListener extends MouseAdapter{
        private ToolPanel toolPanel;
        private Controller controller;
        private Model model;
        private Point MP;

        private Point p1;
        private Point p2;

        boolean isDraw;

        public MouseListener(Controller controller, ToolPanel toolPanel){
            this.toolPanel = toolPanel;
            this.controller = controller;
            this.model = null;
            this.MP = new Point(0,0);
        }

        public void mouseMoved(MouseEvent e){
            p2 = e.getPoint();
        }

        public void mouseReleased(MouseEvent e){
            isDraw = false;
            model = null;
        }

        public void mousePressed(MouseEvent e){
            isDraw = true;
            p1 = p2;
            Tool tool = controller.getTool();

            if (tool == Tool.CIRCLE) {
                model = new Circle(p1.x, p1.y);
            }

            if (tool == Tool.LINE) {
                model = new Line(p1.x, p1.y);
            }

            if (tool == Tool.RECTANGLE) {
                model = new Rectangle(p1.x, p1.y);
            }

            if (model != null) {
                model.setColor(toolPanel.getColor());
                controller.addOne(model);
            }

        }

        public void mouseDragged(MouseEvent e){
            MP.x = e.getPoint().x-p2.x;
            MP.y = e.getPoint().y-p2.y;
            if(model != null && isDraw){
                model.setP2(p2);
            }
            controller.getView2().repaint();
            p2 = e.getPoint();
        }
    }

    class MainPanel extends JPanel{
        public MainPanel(){
            super(new GridBagLayout());
        }
        public void newDraw(View2 d){
            this.removeAll();
            this.add(d);
            mouse = new MouseListener(controller, toolPanel);
            d.addMouseListener(mouse);
            d.addMouseMotionListener(mouse);
            setPreferredSize(d.getPreferredSize());
            pack();
        }
    }

    public View(){
        //basic information of the view
        this.setVisible(true);
        this.setTitle("Vector Drawing");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create menu
        JMenuBar menubar = new JMenuBar();
        this.setJMenuBar(menubar);

        JMenu file = new JMenu("File");
        menubar.add(file);
        JMenuItem newFile = new JMenuItem("File-New");
        file.add(newFile);
        JMenuItem loadFile = new JMenuItem("File-Load");
        file.add(loadFile);
        JMenuItem saveFile = new JMenuItem("File-Save");
        file.add(saveFile);

        JMenu MenuView = new JMenu("View");
        menubar.add(MenuView);
        JMenuItem fullSize = new JMenuItem("View-Full-Size");
        MenuView.add(fullSize);
        JMenuItem fitWindow = new JMenuItem("View-Fit-to-Window");
        MenuView.add(fitWindow);

        class newFileAction implements ActionListener {
            public void actionPerformed(ActionEvent e){
                controller.initial(new Dimension(579, 432));
            }
        }
        class loadFileAction implements ActionListener{
            public void actionPerformed(ActionEvent e){
                fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                FileFilter fileFilter = new FileNameExtensionFilter("Vector-Drawing", "Drawing");
                fileChooser.addChoosableFileFilter(fileFilter);
                fileChooser.setFileFilter(fileFilter);
                fileChooser.showOpenDialog(null);
                File file = fileChooser.getSelectedFile();
                if(file != null){
                    load(file, controller);
                }
            }
        }
        class saveFileAction implements ActionListener {
            public void actionPerformed(ActionEvent e) {
                fileChooser = new JFileChooser();
                fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
                fileChooser.setSelectedFile(new File("draw1.Drawing"));
                FileFilter fileFilter = new FileNameExtensionFilter("Vector-Drawing", "Drawing");
                fileChooser.addChoosableFileFilter(fileFilter);
                fileChooser.setFileFilter(fileFilter);
                fileChooser.showSaveDialog(null);
                File file = fileChooser.getSelectedFile();
                if(file != null){
                    save(file, controller);
                }
            }
        }
        class fullSizeAction implements ActionListener{
            public void actionPerformed(ActionEvent e){

            }
        }
        class fitWindowAction implements ActionListener{
            public void actionPerformed(ActionEvent e){

            }
        }
        newFile.addActionListener(new newFileAction());
        loadFile.addActionListener(new loadFileAction());
        saveFile.addActionListener(new saveFileAction());

        fullSize.addActionListener(new fullSizeAction());
        fitWindow.addActionListener(new fitWindowAction());

            // create drawing panel and tool panel
        mainPanel = new MainPanel();
        scrollPane = new JScrollPane(mainPanel);
        controller = new Controller(this);
        toolPanel = new ToolPanel(controller);
        controller.initial(new Dimension(579, 432));

        getContentPane().add(scrollPane, BorderLayout.CENTER);
        getContentPane().add(toolPanel, BorderLayout.WEST);

        pack();
    }

    public Point getPoint(String str) {
        String[] p = str.split(",");
        return new Point(Integer.parseInt(p[0].trim()), Integer.parseInt(p[1].trim()));
    }

    public void load(File file, Controller controller){
        int read = 1;
        try{
            BufferedReader in = new BufferedReader(new FileReader(file));
            Point p = getPoint(in.readLine());
            controller.initial(new Dimension(579, 432));
            String string;
            while ((string = in.readLine()) != null) {
                String temp = "dd";

                try {
                    read++;
                    if (string.length() == 0) continue;
                    String[] strSplit = string.split(";");
                    strSplit[0] = strSplit[0].trim();
                    temp = strSplit[0];
                    Point p1 = getPoint(strSplit[1]);
                    Point p2 = getPoint(strSplit[2]);
                    Model model;
                    if (strSplit[0].equals("rect")) {
                        model = new Rectangle(p1.x, p1.y);
                    }
                    else if (strSplit[0].equals("circ")) {
                        model = new Circle(p1.x, p1.y);
                    }
                    else if (strSplit[0].equals("line")) {
                        model = new Line(p1.x, p1.y);
                    }
                    else {
                        throw new ArrayIndexOutOfBoundsException();
                    }
                    if (model != null) {
                        model.setP2(p2);
                        model.setColor(new Color(Integer.parseInt(strSplit[3].trim())));
                        controller.getView2().addModel(model);
                    }
                } catch (ArrayIndexOutOfBoundsException e) {
                    System.out.println(temp);
                } catch (NumberFormatException e) {
                    System.out.println("bad");
                }
            }
            in.close();
        } catch(IOException e){
            e.printStackTrace(out);
        }
    }

    public void save(File file, Controller controller){
        View2 view2 = controller.getView2();
        try{
            BufferedWriter out = new BufferedWriter(new FileWriter(file));
            out.write(view2.getPreferredSize().width + ","+view2.getPreferredSize().height +"\n");
            for (Model m : controller.getView2()) {
                out.write(m.toString() + "\n");
            }
            out.close();
        } catch(IOException e){
            JOptionPane.showMessageDialog(null, "can't save", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    public class ToolPanel extends JToolBar implements ActionListener {
        private JToggleButton circle;
        private JToggleButton line;
        private JToggleButton select;
        private JToggleButton eraser;
        private JToggleButton rectangle;
        private JToggleButton fillTool;
        private JToggleButton colorTool;
        private JToggleButton lineThick;

        private Controller c;
        public Color color;

        public ToolPanel(Controller c) {
            super("Tools", VERTICAL);
            this.c = c;
            color = Color.BLACK;

            select = new JToggleButton(new ImageIcon("img/selection.png"));
            select.setToolTipText("selection");
            circle = new JToggleButton(new ImageIcon("img/oval.png"));
            circle.setToolTipText("Draw circles");
            line = new JToggleButton(new ImageIcon("img/diagonal-line.png"));
            line.setToolTipText("Draw lines");
            eraser = new JToggleButton(new ImageIcon("img/eraser.png"));
            eraser.setToolTipText("Eraser");
            rectangle = new JToggleButton(new ImageIcon("img/rectangle.png"));
            rectangle.setToolTipText("Draw Rectangle");
            fillTool = new JToggleButton(new ImageIcon("img/fill.png"));
            fillTool.setToolTipText("Fill Areas");
            colorTool = new JToggleButton(new ImageIcon("img/palette.png"));
            colorTool.setToolTipText("Fill Areas");

            select.addActionListener(this);
            eraser.addActionListener(this);
            line.addActionListener(this);
            circle.addActionListener(this);
            rectangle.addActionListener(this);
            fillTool.addActionListener(this);
            colorTool.addActionListener(this);

            add(new JLabel("Tools"));
            add(select);
            add(eraser);
            add(line);
            add(rectangle);
            add(circle);
            add(fillTool);
            add(colorTool);
        }

        public Color getColor() {
            return color;
        }

        public void setColor(Color c) {
            this.color = c;
        }

        class ColorWindow extends JDialog {
            private JColorChooser colorChooser = new JColorChooser();
            private JButton choosed = new JButton("Choosed Color");
            private JButton notChoose = new JButton("Not choose Color");

            public ColorWindow(final ToolPanel toolPanel) {
                setTitle("Color Dialog");
                setLayout(new BorderLayout());
                add(colorChooser, BorderLayout.NORTH);
                JPanel panel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
                panel.add(choosed);
                panel.add(notChoose);
                add(panel, BorderLayout.SOUTH);
                pack();
                setVisible(true);
                choosed.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent a) {
                        setVisible(true);
                        toolPanel.setColor(colorChooser.getColor());
                        c.getView2().repaint();
                    }
                });
                notChoose.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent a) {
                        setVisible(false);
                    }
                });
            }
        }

        public void actionPerformed(ActionEvent e){
            Object type = e.getSource();
            if(type.equals(circle)){
                c.setTool(Tool.CIRCLE);
            }
            if (type.equals(line)) {
                c.setTool(Tool.LINE);
            }
            if (type.equals(rectangle)) {
                c.setTool(Tool.RECTANGLE);
            }
            if (type.equals(colorTool)) {
                new ColorWindow(this);
            }
        }
    }



    public void update(){
        mainPanel.newDraw(controller.getView2());
        pack();
        repaint();
    }
}
