import java.awt.*;

/**
 * Created by amyzhu on 16/6/20.
 */
public class Controller {
    private View view;
    private View2 view2;

    private Tool tool;

    public Controller(View view){
        this.view = view;
        tool = Tool.LINE;
    }

    class AddAction {
        View2 view2;
        Model model;

        public AddAction(View2 view2, Model model){
            this.view2 = view2;
            this.model = model;
        }

        public void execute(){
            view2.addModel(model);
        }
    }

    public void setTool(Tool tool){
        this.tool = tool;
    }

    public Tool getTool(){
        return tool;
    }

    public void initial(Dimension size){
        view2 = new View2(size);
        if(view != null){
            view.update();
        }
    }

    public void addOne(Model model){
        AddAction add = new AddAction(view2, model);
        add.execute();
    }

    public View2 getView2(){
        return view2;
    }
}
