package com.example.amyzhu.drawvectormobile;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Handler;
import android.util.*;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

public class View2 extends LinearLayout implements Observer {

    private Model model;
    private TextView view;


    private Bitmap bitmap = null;
    private int historyPointer = 0;

    private List<Path> pathLists = new ArrayList<Path>();
    private List<Paint> paintLists = new ArrayList<Paint>();

    private Tools tools;

    private boolean isDown = false;
    private boolean isfill = false;
    private boolean isSelect = false;

    private int color = Color.BLACK;

    private Paint.Style paintStyle = Paint.Style.STROKE;
    private float paintStrokeWidth = 3F;
    private int opacity = 255;
    private float blur = 0F;
    private Paint.Cap lineCap = Paint.Cap.ROUND;

    private float startX = 0F;
    private float startY = 0F;


    public View2(Context context, Model m) {
        super(context);
        super.setBackgroundColor(Color.WHITE);

        setWillNotDraw(false);
        invalidate();

        Log.d("MVC", "View2 constructor");

        // get the xml description of the view and "inflate" it
        // into the display (kind of like rendering it)
        View.inflate(context, R.layout.view2, this);

        view = (TextView)findViewById(R.id.view2_textview);

        // save the model reference
        model = m;
        // add this view to model's list of observers
        model.addObserver(this);
        this.tools = Tools.RECTANGLE;

        // get a reference to widgets to manipulate on update

        // this is a view only view, no controller
        // (unlike the mvc java swing example)
        this.pathLists.add(new Path());
        this.paintLists.add(this.createPaint());
    }

    private Path createPath(MotionEvent event){
        Path path = new Path();

        this.startX = event.getX();
        this.startY = event.getY();

        path.moveTo(this.startX, this.startY);

        return path;
    }

    private Paint createPaint() {
        Paint paint = new Paint();

        paint.setAntiAlias(true);
        paint.setStyle(this.paintStyle);
        paint.setStrokeWidth(this.paintStrokeWidth);
        paint.setStrokeCap(this.lineCap);
        paint.setStrokeJoin(Paint.Join.MITER);
        paint.setColor(this.color);
        paint.setShadowLayer(this.blur, 0F, 0F, this.color);
        paint.setAlpha(this.opacity);
        if(isfill) {
            paint.setStyle(Paint.Style.FILL);
        }

        return paint;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                this.updateHistory(this.createPath(event));
                this.isDown = true;
                break;
            case MotionEvent.ACTION_MOVE:
                this.onActionMove(event);
                break;
            case MotionEvent.ACTION_UP:
                if(isDown){
                    this.startX = 0F;
                    this.startY = 0F;
                    this.isDown = false;
                }
                break;
            default:
                break;
        }
        this.invalidate();
        return true;
    }

    private void updateHistory(Path path){
        if(this.historyPointer == this.pathLists.size()){
            this.pathLists.add(path);
            this.paintLists.add(this.createPaint());
            this.historyPointer++;
        } else {
            this.pathLists.set(this.historyPointer, path);
            this.paintLists.set(this.historyPointer, this.createPaint());
            this.historyPointer++;

            for(int i = this.historyPointer, size = this.paintLists.size(); i < size; i++){
                this.pathLists.remove(this.historyPointer);
                this.paintLists.remove(this.historyPointer);
            }
        }
    }

    private void onActionMove(MotionEvent event){
        float x = event.getX();
        float y = event.getY();

        if(!isDown){
            return;
        }

        Path path = this.pathLists.get(this.historyPointer-1);
        switch(this.tools){
            case LINE:
                path.reset();
                path.moveTo(this.startX, this.startY);
                path.lineTo(x,y);
                break;
            case RECTANGLE:
                path.reset();
                path.addRect(this.startX, this.startY, x, y, Path.Direction.CCW);
                break;
            case CIRCLE:
                double distanceX = Math.abs((double)(this.startX-x));
                double distanceY = Math.abs((double)(this.startY-y));
                double radius = Math.sqrt(Math.pow(distanceX,2.0)+Math.pow(distanceY,2.0));
                path.reset();
                path.addCircle(this.startX,this.startY, (float)radius, Path.Direction.CCW);
                break;
        }
    }

    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if(this.bitmap != null){
            canvas.drawBitmap(this.bitmap, 0F, 0F, new Paint());
        }

        for(int i = 0; i < this.historyPointer; i++){
            Path path = this.pathLists.get(i);
            Paint paint = this.paintLists.get(i);
            canvas.drawPath(path, paint);
        }
    }

    // the model call this to update the view
    public void update(Observable observable, Object data) {
        Log.d("MVC", "update View2");

        int n = model.getCounterValue();

        StringBuilder s = new StringBuilder(n);
        for (int i = 0; i < 100; i++) {
            s.append("                                  \n");
        }
        view.setText(s);

        Tools t = model.getTool();
        this.tools = t;

        boolean f = model.checkFill();
        this.isfill = f;

        int c = model.getColor();
        this.color = c;

        float b = model.getThick();
        this.paintStrokeWidth = b;

        boolean se = model.getSelect();
        this.isSelect = se;
    }
}
