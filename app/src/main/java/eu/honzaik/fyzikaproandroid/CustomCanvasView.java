package eu.honzaik.fyzikaproandroid;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Looper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

public class CustomCanvasView extends View {

    private final Context context;
    private int framesPerSecond = 60;
    private long animationDuration = 5000; //calculated later
    private long startTime;
    private Matrix matrix;
    private Path linePath;
    private Paint linePaint;
    private Bitmap bitmap;
    private Canvas mCanvas;
    private float[] coords;
    private static final float FPS = 1000 / 60; //1000 ms = 1s 60x za sekundu = ms mezi jednotlivymi snimky
    private int moveType;
    private boolean running = false;
    private int coordsDrawn = 0;

    private Paint coordsPaint;
    private Path xPath;
    private Path yPath;


    public CustomCanvasView(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
        matrix = new Matrix();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        bitmap = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888);
        mCanvas = new Canvas(bitmap);
        drawCoordSystem(0, 0, 0, 0);
    }

    public void start(int moveType, float startX, float startY, long duration){
        coords = new float[]{startX, startY};
        this.animationDuration = duration;
        linePath = new Path();
        linePath.moveTo(coords[0], coords[1]);
        linePaint = new Paint();
        linePaint.setAntiAlias(true);
        linePaint.setColor(Color.WHITE);
        linePaint.setStyle(Paint.Style.STROKE);
        linePaint.setStrokeWidth(4f);
        running = true;
        this.startTime = System.currentTimeMillis();
        moveRunnable.run();
    }


    @Override
    protected void onDraw(Canvas canvas){
        super.onDraw(canvas);

        if(running){
            linePath.lineTo(coords[0], coords[1]);
            linePath.moveTo(coords[0], coords[1]);
            Log.d("FYS", "x: " + coords[0] + " | y: " + coords[1]);
            canvas.drawPath(linePath, linePaint);
        }
        if (coordsDrawn < 2) {
            canvas.drawPath(xPath, coordsPaint);
            canvas.drawPath(yPath, coordsPaint);
            coordsDrawn++;
        }

    }

    Handler handler = new Handler(Looper.getMainLooper());
    Runnable moveRunnable = new Runnable() {
        @Override
        public void run() {
            long timeElapsed = System.currentTimeMillis() - startTime;
            Log.d("FYS", "timeelapsed: " + timeElapsed);
            updateCoords(moveType);
            invalidate();
            if(timeElapsed > animationDuration){
                handler.removeCallbacks(moveRunnable);
            }else{
                handler.postDelayed(this, 1000/60);
            }
        }
    };

    private void drawCoordSystem(float xMax, float xMin, float yMax, float yMin){
        int width = this.getWidth();
        int height = this.getHeight();
        Log.d("FYS", "w: " + width + " h: " + height);
        int padding = 50;

        coordsPaint = new Paint();
        coordsPaint.setAntiAlias(true);
        coordsPaint.setColor(Color.GREEN);
        coordsPaint.setStyle(Paint.Style.STROKE);
        coordsPaint.setStrokeWidth(2f);

        xPath = new Path();
        xPath.moveTo(padding, height / 2);
        xPath.lineTo(width - padding, height / 2);
        yPath = new Path();
        yPath.moveTo(padding, padding);
        yPath.lineTo(padding, height - padding);

    }

    private void updateCoords(int moveType){
        // movetype = 0 konstatní fce
        if(moveType == 0){
            coords[0] = coords[0] + (1000/60)/10;
            // if coords[0] + xxx > konec coords[0] = konec
        }
    }
}
