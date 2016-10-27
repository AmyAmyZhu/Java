package com.example.amyzhu.drawvectormobile;


        import android.os.Bundle;
        import android.app.Activity;
        import android.util.Log;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.LinearLayout;
        import android.widget.TextView;

public class MainActivity extends Activity {
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("MVC", "onCreate");

        // load the base UI (has places for the views)
        setContentView(R.layout.mainactivity);

        // Setup model
        model = new Model();
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        Log.d("MVC", "onPostCreate");
        // can only get widgets by id in onPostCreate for activity xml res

        // create the views and add them to the main activity
        View1 view1 = new View1(this, model);
        ViewGroup v1 = (ViewGroup) findViewById(R.id.mainactivity_1);
        v1.addView(view1);

        View2 view2 = new View2(this, model);
        ViewGroup v2 = (ViewGroup) findViewById(R.id.mainactivity_2);
        v2.addView(view2);

        // initialize views
        model.setChanged();
        model.notifyObservers();

    }

    // save and restore state (need to do this to support orientation change)

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        Log.d("MVC", "save state");
        outState.putInt("Counter", model.getCounterValue());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        Log.d("MVC", "restore state");
        super.onRestoreInstanceState(savedInstanceState);
        model.setCounterValue(savedInstanceState.getInt("Counter"));
    }

}
