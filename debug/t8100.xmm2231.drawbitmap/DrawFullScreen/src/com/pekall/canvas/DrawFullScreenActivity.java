package com.pekall.canvas;

import java.text.DecimalFormat;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.os.Debug;
import android.view.View;
import android.view.WindowManager;


public class DrawFullScreenActivity extends Activity {

    @Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
	}

    @Override
    protected void onResume() {
        super.onResume();

        System.out.println("onResume");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

    }

	class MyView extends View {

		private Bitmap mMap;

		private int count = 0;

		private static final float MAX_COUNT = 20.0f;

		private int totle = 0;

		private DecimalFormat mFormat;

		public MyView(Context context) {
			super(context);
			mMap = BitmapFactory.decodeResource(getResources(), R.drawable.screen);
			mFormat = new DecimalFormat("#.000");
		}

		protected void onDraw(Canvas canvas) {

            // FileOutputStream fs = new FileOutputStream(new File("/data/local/dmtrace.txt"));
            // Debug.startMethodTracing(fs.getFD());

            // Debug.startMethodTracing();
			// invalidate();
			long timeBefore = System.currentTimeMillis();
            System.out.println("before canvas.drawBitmap: " + timeBefore);

            // Debug.startNativeTracing();
			canvas.drawBitmap(mMap, 0, 0, new Paint());
			// Debug.stopNativeTracing();

			long timeAfter = System.currentTimeMillis();
            System.out.println("after canvas.drawBitmap: " + timeAfter);
            System.out.println("canvas.drawBitmap duration: " + (timeAfter-timeBefore));

			// Debug.stopMethodTracing();

			/* long duration = System.currentTimeMillis() - time;
			if (count >= MAX_COUNT) {
				double average = totle / MAX_COUNT;
				int fps = (int) (1000 / average);
				System.out.println("~~~~draw_time = " + mFormat.format(average) + "    ### FPS = " + fps);
				count = 0;
				totle = 0;
			} else {
				count++;
				totle += duration;
			}
			*/
		}
	}
}
