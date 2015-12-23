package eu.honzaik.fyzikaproandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.animation.EasingFunction;

import eu.honzaik.fyzikaproandroid.other.CustomLineChart;
import eu.honzaik.fyzikaproandroid.other.CustomYAxisValueFormatter;
import eu.honzaik.fyzikaproandroid.other.SvislyVzhuruVrh;

public class SvislyVzhuruVrhActivity extends PohybActivity {

    private CustomCanvasView customCanvas;
    private Button startButton;
    private CustomLineChart chartRychlost;
    private CustomLineChart chartDraha;
    private CustomLineChart chartZrychleni;
    private SeekBar pocatecniRychlostSeekbar;
    private TextView pocatecniRychlostValueTextView;
    private float pocatecniRychlost;
    private SvislyVzhuruVrh vrh;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svisly_vzhuru_vrh);

        Toolbar pohybToolbar = (Toolbar) findViewById(R.id.svisly_vzhuru_vrh_activity_toolbar);
        setSupportActionBar(pohybToolbar);

        ActionBar ab = getSupportActionBar();
        if(ab != null) ab.setDisplayHomeAsUpEnabled(true);

        pocatecniRychlostValueTextView = (TextView) findViewById(R.id.svisly_vzhuru_vrh_activity_seek_bar_rychlost_value);

        pocatecniRychlostSeekbar = (SeekBar) findViewById(R.id.svisly_vzhuru_vrh_activity_seek_bar_rychlost);
        pocatecniRychlostSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pocatecniRychlostValueTextView.setText(String.valueOf(progress));
                pocatecniRychlost = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        chartRychlost = (CustomLineChart) findViewById(R.id.svisly_vzhuru_vrh_activity_chart_rychlost);
        chartRychlost.setDescription("Graf rychlosti na čase");
        chartRychlost.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m/s"));

        chartDraha = (CustomLineChart) findViewById(R.id.svisly_vzhuru_vrh_activity_chart_draha);
        chartDraha.setDescription("Graf dráhy na čase.");
        chartDraha.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m"));

        chartZrychleni = (CustomLineChart) findViewById(R.id.svisly_vzhuru_vrh_activity_chart_zrychleni);
        chartZrychleni.setDescription("Graf zrychlení na čase");
        chartZrychleni.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m/s^2"));

        startButton = (Button) findViewById(R.id.svisly_vzhuru_vrh_activity_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vrh = new SvislyVzhuruVrh(pocatecniRychlost);
                chartRychlost.setData(vrh.generateLineData(0));
                chartDraha.setData(vrh.generateLineData(1));
                chartZrychleni.setData(vrh.generateLineData(2));

                chartRychlost.invalidate();
                chartDraha.invalidate();
                chartZrychleni.invalidate();

                chartRychlost.animateX(((int) vrh.getCelkovyCas()) * 1000);
                chartDraha.animateX(((int) vrh.getCelkovyCas()) * 1000);
                chartZrychleni.animateX(((int) vrh.getCelkovyCas()) * 1000);
            }
        });
    }

    public static final EasingFunction MyInOutQuad = new EasingFunction() {
        @Override
        public float getInterpolation(float input) {
            float position = input / 0.5f;

            if (position < 1.f) {
                //return 0.5f * position * position;
                return (float) (Math.sqrt(2) * Math.sqrt(position));
            }

            //return -0.5f * ((--position) * (position - 2.f) - 1.f);
            return (float) (-1.5f - 0.5f * Math.sqrt(5-8*position));
        }
    };

}
