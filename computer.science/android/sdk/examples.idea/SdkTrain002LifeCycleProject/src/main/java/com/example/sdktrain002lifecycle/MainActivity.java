package com.example.sdktrain002lifecycle;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;

public class MainActivity extends Activity {

    private static final String LOGTAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Log.i(LOGTAG, "onCreate");

        // 1. invoked once when the activity is created
        // 2. setup UI
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.i(LOGTAG, "onStart");

    }

    @Override
    protected void onRestart() {
        super.onRestart();

        // When the user leaves your activity, the system calls onStop() to stop the activity
        // (1). If the user returns while the activity is stopped, the system calls onRestart()
        // (2), quickly followed by onStart()
        // (3) and onResume()
        // (4). Notice that no matter what scenario causes the activity to stop,
        // the system always calls onPause() before calling onStop().


        // called only when the activity resumes from the stopped state
    }

    @Override
    protected void onStop() {
        // 1. the activity is completely hidden
        // 2. considered to be in the background
        // 3. While stopped, the activity instance and all its state information
        //      such as member variables is retained

        // counterpart of onStart

        // should:
        // 1. perform larger, more CPU intensive shut-down operations,
        //      such as writing information to a database
        // 2. release resources to avoid memory leak
        //      since onDestroy might not be invoked

        super.onStop();
        Log.i(LOGTAG, "onStop");
    }

    @Override
    protected void onResume() {
        // 1. The activity is in the foreground
        // 2. The user can interact with it
        // 3. counterpart of onPause

        // should:
        // 1. initialize components that you release during onPause()

        super.onResume();
        Log.i(LOGTAG, "onResume");
    }

    @Override
    protected void onPause() {
        // 1. The activity is partially obscured by another activity
        // 2. The paused activity does not receive user input and cannot execute any code.
        // 3. counterpart of onResume

        // In onPause, you should:
        // 1. Stop animations or other ongoing actions that could consume CPU.
        // 2. Commit unsaved changes, but only if users expect such changes
        //      to be permanently saved when they leave (such as a draft email).
        // 3. Release system resources, such as broadcast receivers, handles to sensors (like GPS),
        //      or any resources that may affect battery life while your activity is paused
        //      and the user does not need them
        // 4. stop video
        // 5. stop camera

        // 6.  avoid performing CPU-intensive work, like write database

        // Conclusion: Stop thing relative with UI

        super.onPause();
        Log.i(LOGTAG, "onPause");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.i(LOGTAG, "onDestroy");

        // 1. invoked before destruction of an activity
        // 2. invoked after onPause and onStop
        //  If you invoke finish() in onCreate, the system will invoke onDestroy directly
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        // 1. Save data before Activity will be killed when memory is low
        //      Your activity will be destroyed and recreated each time the user rotates the screen.
        // 2. save data to the "outState" bundle, and it will be passed to onRestoreInstanceState

        // FIXME: In order for the Android system to restore the state of the views in your activity,
        //      each view must have a unique ID, supplied by the android:id attribute.


        // As the system begins to stop your activity, it calls onSaveInstanceState() (1)
        // so you can specify additional state data you'd like to save in case the Activity instance must be recreated.
        // If the activity is destroyed and the same instance must be recreated,
        // the system passes the state data defined at (1) to both the onCreate() method (2)
        //                                      and the onRestoreInstanceState() method (3).
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        // 1. Both the onCreate() and onRestoreInstanceState() callback methods
        //      receive the same Bundle that containes the instance state information.
        // 2. need check whether the bundle parameter in onCreate is null
        // 3. onRestoreInstanceState invoked only when savedInstanceState is not null
        //

        // invoked after onStart
    }
}
