package com.example.amyzhu.drawvectormobile;


        import android.graphics.Color;
        import android.util.Log;
        import java.util.Observable;
        import java.util.Observer;

public class Model extends Observable {
    private Tools checkTool = Tools.LINE;
    private int counter;
    private boolean checkfill = false;
    private int checkColor = Color.BLACK;
    private float checkThick = 0F;
    private boolean isSelect = false;

    Model() {
        counter = 0;
    }

    // Data methods
    public int getCounterValue() {
        return counter;
    }

    public void setCounterValue(int i) {
        counter = i;
        Log.d("MVC", "Model: set counter to " + counter);
        setChanged();
        notifyObservers();
    }

    public void incrementCounter() {
        counter++;
        Log.d("MVC", "Model: increment counter to " + counter);
        setChanged();
        notifyObservers();
    }

    public void setTool(Tools t){
        checkTool = t;
        setChanged();
        notifyObservers();
    }

    public Tools getTool(){
        return checkTool;
    }

    public void setFill(){
        checkfill = !checkfill;
        setChanged();
        notifyObservers();
    }

    public boolean checkFill(){
        return checkfill;
    }

    public void setColor(int c){
        checkColor = c;
        setChanged();
        notifyObservers();
    }

    public int getColor(){
        return checkColor;
    }

    public void setThick(float f){
        checkThick = f;
        setChanged();
        notifyObservers();
    }

    public float getThick(){
        return checkThick;
    }

    public void changeSelect(boolean b){
        isSelect = b;
        setChanged();
        notifyObservers();
    }

    public boolean getSelect(){
        return isSelect;
    }

    // Observer methods
    @Override
    public void addObserver(Observer observer) {
        Log.d("MVC", "Model: Observer added");
        super.addObserver(observer);
    }

    @Override
    public synchronized void deleteObservers() {
        super.deleteObservers();
    }

    @Override
    public void notifyObservers() {
        Log.d("MVC", "Model: Observers notified");
        super.notifyObservers();
    }

    @Override
    protected void setChanged() {
        super.setChanged();
    }

    @Override
    protected void clearChanged() {
        super.clearChanged();
    }
}