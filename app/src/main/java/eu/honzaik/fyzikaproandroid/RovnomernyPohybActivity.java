package eu.honzaik.fyzikaproandroid;

import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.components.YAxis;

import eu.honzaik.fyzikaproandroid.other.CustomLineChart;
import eu.honzaik.fyzikaproandroid.other.CustomYAxisValueFormatter;
import eu.honzaik.fyzikaproandroid.other.RovnomernyPohyb;

public class RovnomernyPohybActivity extends PohybActivity {

    private Button startButton;
    private CustomLineChart chartRychlost;
    private CustomLineChart chartDraha;
    private CustomLineChart chartZrychleni;
    private RovnomernyPohyb pohyb;
    private SeekBar rychlostSeekbar;
    private SeekBar casSeekBar;
    private TextView rychlostValueTextView;
    private TextView casValueTextView;
    private float cas;
    private float rychlost;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rovnomerny_pohyb);

        Toolbar pohybToolbar = (Toolbar) findViewById(R.id.rovnomerny_pohyb_activity_toolbar);
        setSupportActionBar(pohybToolbar);

        ActionBar ab = getSupportActionBar();
        if(ab != null) ab.setDisplayHomeAsUpEnabled(true);

        rychlostValueTextView = (TextView) findViewById(R.id.rovnomerny_pohyb_activity_text_view_rychlost_value);
        casValueTextView = (TextView) findViewById(R.id.rovnomerny_pohyb_activity_text_view_cas_value);

        rychlostSeekbar = (SeekBar) findViewById(R.id.rovnomerny_pohyb_activity_seek_bar_rychlost);
        casSeekBar = (SeekBar) findViewById(R.id.rovnomerny_pohyb_activity_seek_bar_cas);

        rychlostSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                rychlostValueTextView.setText(String.valueOf(progress));
                rychlost = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        casSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                casValueTextView.setText(String.valueOf(progress));
                cas = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });



        chartRychlost = (CustomLineChart) findViewById(R.id.rovnomerny_pohyb_activity_chart_rychlost);
        chartRychlost.setDescription("Graf rychlosti na čase");
        chartRychlost.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m/s"));

        chartDraha = (CustomLineChart) findViewById(R.id.rovnomerny_pohyb_activity_chart_draha);
        chartDraha.setDescription("Graf dráhy na čase");
        chartDraha.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m"));

        chartZrychleni = (CustomLineChart) findViewById(R.id.rovnomerny_pohyb_activity_chart_zrychleni);
        chartZrychleni.setDescription("Graf zrychlení na čase");
        chartZrychleni.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m/s^2"));

        startButton = (Button) findViewById(R.id.rovnomerny_pohyb_activity_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pohyb = new RovnomernyPohyb(rychlost, cas);
                chartRychlost.setData(pohyb.generateLineData(0));
                chartDraha.setData(pohyb.generateLineData(1));
                chartZrychleni.setData(pohyb.generateLineData(2));

                chartRychlost.invalidate();
                chartDraha.invalidate();
                chartZrychleni.invalidate();

                chartRychlost.animateX(((int) cas) * 1000);
                chartDraha.animateX(((int) cas)*1000);
                chartZrychleni.animateX(((int) cas)*1000);
            }
        });
    }

}
