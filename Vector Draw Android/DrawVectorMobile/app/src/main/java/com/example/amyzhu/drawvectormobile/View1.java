package com.example.amyzhu.drawvectormobile;

        import java.util.Observable;
        import java.util.Observer;

        import android.content.Context;
        import android.graphics.Color;
        import android.provider.ContactsContract;
        import android.util.*;
        import android.view.View;
        import android.widget.Button;
        import android.widget.ImageButton;
        import android.widget.LinearLayout;

public class View1 extends LinearLayout implements Observer {

    private Model model;

    private ImageButton selection;
    private ImageButton eraser;
    private ImageButton line;
    private ImageButton rectangle;
    private ImageButton circle;
    private ImageButton fill;

    private Button red;
    private Button blue;
    private Button green;

    private ImageButton thin;
    private ImageButton mid;
    private ImageButton thick;

    public View1(Context context, Model m) {
        super(context);

        Log.d("MVC", "View1 constructor");

        // get the xml description of the view and "inflate" it
        // into the display (kind of like rendering it)
        View.inflate(context, R.layout.view1, this);

        // save the model reference
        model = m;
        // add this view to model's list of observers
        model.addObserver(this);

        // get a reference to widgets to manipulate on update

        selection = (ImageButton) findViewById(R.id.selection);
        eraser = (ImageButton) findViewById(R.id.eraser);

        line = (ImageButton) findViewById(R.id.line);
        circle = (ImageButton) findViewById(R.id.circle);
        rectangle = (ImageButton) findViewById(R.id.rectangle);
        fill = (ImageButton) findViewById(R.id.fill);

        red = (Button) findViewById(R.id.red);
        blue = (Button) findViewById(R.id.blue);
        green = (Button) findViewById(R.id.green);

        thin = (ImageButton) findViewById(R.id.thin);
        mid = (ImageButton) findViewById(R.id.mid);
        thick = (ImageButton) findViewById(R.id.thick);

        selection.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.changeSelect(true);
            }
        });

        rectangle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.changeSelect(false);
                model.setTool(Tools.RECTANGLE);
            }
        });

        circle.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.changeSelect(false);
                model.setTool(Tools.CIRCLE);
            }
        });

        line.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.changeSelect(false);
                model.setTool(Tools.LINE);
            }
        });

        fill.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.changeSelect(false);
                model.setFill();
            }
        });

        red.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setColor(Color.RED);
            }
        });

        blue.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setColor(Color.BLUE);
            }
        });

        green.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setColor(Color.GREEN);
            }
        });

        thin.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setThick(0F);
            }
        });

        mid.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setThick(5F);
            }
        });

        thick.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View v) {
                model.setThick(10F);
            }
        });

    }

    // the model call this to update the view
    public void update(Observable observable, Object data) {
        Log.d("MVC", "update View1");

    }
}
