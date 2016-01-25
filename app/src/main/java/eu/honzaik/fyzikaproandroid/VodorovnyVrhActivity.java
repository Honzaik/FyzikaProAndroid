package eu.honzaik.fyzikaproandroid;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.data.LineData;

import eu.honzaik.fyzikaproandroid.other.CustomLineChart;
import eu.honzaik.fyzikaproandroid.other.CustomYAxisValueFormatter;
import eu.honzaik.fyzikaproandroid.other.VodorovnyVrh;

public class VodorovnyVrhActivity extends PohybActivity {

    private Button startButton;
    private CustomLineChart chartRychlost;
    private CustomLineChart chartTrajektorie;
    private CustomLineChart chartZrychleni;
    private SeekBar pocatecniRychlostSeekbar;
    private TextView pocatecniRychlostValueTextView;
    private SeekBar pocatecniVyskaSeekbar;
    private TextView pocatecniVyskaValueTextView;
    private float pocatecniRychlost;
    private float pocatecniVyska;
    private VodorovnyVrh vrh;
    private float chartTrajektorieHeight = -1f;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vodorovny_vrh);

        Toolbar pohybToolbar = (Toolbar) findViewById(R.id.vodorovny_vrh_activity_toolbar);
        setSupportActionBar(pohybToolbar);

        ActionBar ab = getSupportActionBar();
        if(ab != null) ab.setDisplayHomeAsUpEnabled(true);

        pocatecniRychlostValueTextView = (TextView) findViewById(R.id.vodorovny_vrh_activity_seek_bar_rychlost_value);

        pocatecniRychlostSeekbar = (SeekBar) findViewById(R.id.vodorovny_vrh_activity_seek_bar_rychlost);
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

        pocatecniVyskaValueTextView = (TextView) findViewById(R.id.vodorovny_vrh_activity_seek_bar_vyska_value);

        pocatecniVyskaSeekbar = (SeekBar) findViewById(R.id.vodorovny_vrh_activity_seek_bar_vyska);
        pocatecniVyskaSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pocatecniVyskaValueTextView.setText(String.valueOf(progress));
                pocatecniVyska = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        chartRychlost = (CustomLineChart) findViewById(R.id.vodorovny_vrh_activity_chart_rychlost);
        chartRychlost.setDescription("Graf rychlosti na čase");
        chartRychlost.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m/s"));

        chartTrajektorie = (CustomLineChart) findViewById(R.id.vodorovny_vrh_activity_chart_trajektorie);
        chartTrajektorie.setDescription("Trajektorie pohybu.");
        chartTrajektorie.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m"));
        chartTrajektorie.setXAxisValue("m");

        chartZrychleni = (CustomLineChart) findViewById(R.id.vodorovny_vrh_activity_chart_zrychleni);
        chartZrychleni.setDescription("Graf zrychlení na čase");
        chartZrychleni.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m/s^2"));

        startButton = (Button) findViewById(R.id.vodorovny_vrh_activity_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vrh = new VodorovnyVrh(pocatecniRychlost, pocatecniVyska);
                LineData trajektorieData = vrh.generateLineData(1);
                chartRychlost.setData(vrh.generateLineData(0));
                chartTrajektorie.setData(trajektorieData);
                chartZrychleni.setData(vrh.generateLineData(2));

                String maxS = trajektorieData.getXVals().get(trajektorieData.getXVals().size() - 1);
                float maxX = Float.parseFloat(maxS);
                Log.d("FYS", "maxX: " + maxX + " maxY: " + trajektorieData.getYMax());
                float chartW = chartTrajektorie.getViewPortHandler().getChartWidth();
                float chartH = chartTrajektorie.getViewPortHandler().getChartHeight();
                ViewGroup.LayoutParams params = chartTrajektorie.getLayoutParams();
                if(chartTrajektorieHeight == -1f) chartTrajektorieHeight = params.height;
                if (trajektorieData.getYMax() <= maxX) {
                    chartTrajektorie.getAxisLeft().setAxisMaxValue(maxX);
                    chartTrajektorie.getViewPortHandler().setChartDimens(chartW, chartW);
                    params.height = (int) chartTrajektorieHeight;
                    chartTrajektorie.setLayoutParams(params);
                }else{
                    float pomer = (trajektorieData.getYMax() + 1) / maxX;
                    chartTrajektorie.getAxisLeft().setAxisMaxValue(trajektorieData.getYMax() + 5);
                    chartTrajektorie.getViewPortHandler().setChartDimens(chartW, chartH * pomer);
                    Log.d("FYS", "w: "+ params.width + " h: " + params.height);
                    params.height = (int) (chartTrajektorieHeight * pomer);
                    chartTrajektorie.setLayoutParams(params);
                }


                chartRychlost.invalidate();
                chartTrajektorie.invalidate();
                chartZrychleni.invalidate();

                chartRychlost.animateX(((int) vrh.getCelkovyCas()) * 1000);
                chartTrajektorie.animateX(((int) vrh.getCelkovyCas()) * 1000);
                chartZrychleni.animateX(((int) vrh.getCelkovyCas()) * 1000);
            }
        });
    }

}
