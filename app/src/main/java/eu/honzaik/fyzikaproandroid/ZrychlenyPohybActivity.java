package eu.honzaik.fyzikaproandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import eu.honzaik.fyzikaproandroid.other.CustomLineChart;
import eu.honzaik.fyzikaproandroid.other.CustomYAxisValueFormatter;
import eu.honzaik.fyzikaproandroid.other.ZrychlenyPohyb;

public class ZrychlenyPohybActivity extends PohybActivity {

    private CustomCanvasView customCanvas;
    private Button startButton;
    private CustomLineChart chartRychlost;
    private CustomLineChart chartDraha;
    private CustomLineChart chartZrychleni;
    private ZrychlenyPohyb pohyb;
    private SeekBar pocatecniRychlostSeekBar;
    private SeekBar casSeekBar;
    private SeekBar zrychleniSeekBar;
    private TextView pocatecniRychlostValueTextView;
    private TextView casValueTextView;
    private TextView zrychleniValueTextView;
    private float cas;
    private float pocatecniRychlost;
    private float zrychleni;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_zrychleny_pohyb);

        Toolbar pohybToolbar = (Toolbar) findViewById(R.id.zrychleny_pohyb_activity_toolbar);
        setSupportActionBar(pohybToolbar);

        ActionBar ab = getSupportActionBar();
        if(ab != null) ab.setDisplayHomeAsUpEnabled(true);

        pocatecniRychlostValueTextView = (TextView) findViewById(R.id.zrychleny_pohyb_activity_text_view_rychlost_value);
        casValueTextView = (TextView) findViewById(R.id.zrychleny_pohyb_activity_text_view_cas_value);
        zrychleniValueTextView = (TextView) findViewById(R.id.zrychleny_pohyb_activity_text_view_zrychleni_value);

        pocatecniRychlostSeekBar = (SeekBar) findViewById(R.id.zrychleny_pohyb_activity_seek_bar_rychlost);
        casSeekBar = (SeekBar) findViewById(R.id.zrychleny_pohyb_activity_seek_bar_cas);
        zrychleniSeekBar = (SeekBar) findViewById(R.id.zrychleny_pohyb_activity_seek_bar_zrychleni);

        pocatecniRychlostSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
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

        zrychleniSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                zrychleniValueTextView.setText(String.valueOf(progress));
                zrychleni = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        chartRychlost = (CustomLineChart) findViewById(R.id.zrychleny_pohyb_activity_chart_rychlost);
        chartRychlost.setDescription("Graf rychlosti na čase");
        chartRychlost.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m/s"));

        chartDraha = (CustomLineChart) findViewById(R.id.zrychleny_pohyb_activity_chart_draha);
        chartDraha.setDescription("Graf dráhy na čase");
        chartDraha.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m"));

        chartZrychleni = (CustomLineChart) findViewById(R.id.zrychleny_pohyb_activity_chart_zrychleni);
        chartZrychleni.setDescription("Graf zrychlení na čase");
        chartZrychleni.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m/s^2"));

        startButton = (Button) findViewById(R.id.zrychleny_pohyb_activity_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pohyb = new ZrychlenyPohyb(pocatecniRychlost, cas, zrychleni);
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
