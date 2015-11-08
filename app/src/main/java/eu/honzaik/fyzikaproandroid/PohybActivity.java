package eu.honzaik.fyzikaproandroid;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class PohybActivity extends AppCompatActivity {

    private CustomCanvasView customCanvas;
    private int framesPerSecond = 60;
    private long animationDuration = 10000; //calculated later
    private long startTime;
    private Button startButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pohyb);
        this.overridePendingTransition(R.anim.from_right, R.anim.to_left);

        Toolbar pohybToolbar = (Toolbar) findViewById(R.id.pohyb_activity_toolbar);
        setSupportActionBar(pohybToolbar);
        ActionBar ab = getSupportActionBar();
        if(ab != null) ab.setDisplayHomeAsUpEnabled(true);

        startTime = System.currentTimeMillis();

        customCanvas = (CustomCanvasView) findViewById(R.id.pohyb_activity_custom_canvas_view);
        startButton = (Button) findViewById(R.id.pohyb_activity_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                customCanvas.start(0, 50f, 100f, 10000);
            }
        });


    }
}
