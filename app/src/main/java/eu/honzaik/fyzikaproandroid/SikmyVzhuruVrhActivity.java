package eu.honzaik.fyzikaproandroid;

import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.TextView;

import com.github.mikephil.charting.data.LineData;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import eu.honzaik.fyzikaproandroid.other.CustomLineChart;
import eu.honzaik.fyzikaproandroid.other.CustomYAxisValueFormatter;
import eu.honzaik.fyzikaproandroid.other.SikmyVzhuruVrh;
import eu.honzaik.fyzikaproandroid.other.VodorovnyVrh;

public class SikmyVzhuruVrhActivity extends PohybActivity {

    private CustomCanvasView customCanvas;
    private Button startButton;
    private CustomLineChart chartRychlost;
    private CustomLineChart chartTrajektorie;
    private CustomLineChart chartZrychleni;
    private SeekBar pocatecniRychlostSeekbar;
    private TextView pocatecniRychlostValueTextView;
    private SeekBar pocatecniUhelSeekBar;
    private TextView pocatecniUhelValueTextView;
    private float pocatecniRychlost;
    private float pocatecniUhel;
    private SikmyVzhuruVrh vrh;
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sikmy_vzhuru_vrh);

        Toolbar pohybToolbar = (Toolbar) findViewById(R.id.sikmy_vzhuru_vrh_activity_toolbar);
        setSupportActionBar(pohybToolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) ab.setDisplayHomeAsUpEnabled(true);

        pocatecniRychlostValueTextView = (TextView) findViewById(R.id.sikmy_vzhuru_vrh_activity_seek_bar_rychlost_value);

        pocatecniRychlostSeekbar = (SeekBar) findViewById(R.id.sikmy_vzhuru_vrh_activity_seek_bar_rychlost);
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

        pocatecniUhelValueTextView = (TextView) findViewById(R.id.sikmy_vzhuru_activity_seek_bar_uhel_value);

        pocatecniUhelSeekBar = (SeekBar) findViewById(R.id.sikmy_vzhuru_activity_seek_bar_uhel);
        pocatecniUhelSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                pocatecniUhelValueTextView.setText(String.valueOf(progress));
                pocatecniUhel = progress;
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });


        chartRychlost = (CustomLineChart) findViewById(R.id.sikmy_vzhuru_activity_chart_rychlost);
        chartRychlost.setDescription("Graf rychlosti na čase");
        chartRychlost.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m/s"));

        chartTrajektorie = (CustomLineChart) findViewById(R.id.sikmy_vzhuru_vrh_activity_chart_trajektorie);
        chartTrajektorie.setDescription("Trajektorie pohybu.");
        chartTrajektorie.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m"));
        chartTrajektorie.setXAxisValue("m");

        chartZrychleni = (CustomLineChart) findViewById(R.id.sikmy_vzhuru_vrh_activity_chart_zrychleni);
        chartZrychleni.setDescription("Graf zrychlení na čase");
        chartZrychleni.getAxisLeft().setValueFormatter(new CustomYAxisValueFormatter("m/s^2"));

        startButton = (Button) findViewById(R.id.sikmy_vzhuru_activity_start_button);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                vrh = new SikmyVzhuruVrh(pocatecniRychlost, pocatecniUhel);
                LineData trajektorieData = vrh.generateLineData(1);
                chartRychlost.setData(vrh.generateLineData(0));
                chartTrajektorie.setData(trajektorieData);
                chartZrychleni.setData(vrh.generateLineData(2));

                String maxS = trajektorieData.getXVals().get(trajektorieData.getXVals().size() - 1);
                float maxX = Float.parseFloat(maxS);

                if (trajektorieData.getYMax() < maxX) {
                    chartTrajektorie.getAxisLeft().setAxisMaxValue(maxX);
                }

                float chartW = chartTrajektorie.getViewPortHandler().getChartWidth();
                chartTrajektorie.getViewPortHandler().setChartDimens(chartW, chartW);

                chartRychlost.invalidate();
                chartTrajektorie.invalidate();
                chartZrychleni.invalidate();

                chartRychlost.animateX(((int) vrh.getCelkovyCas()) * 1000);
                chartTrajektorie.animateX(((int) vrh.getCelkovyCas()) * 1000);
                chartZrychleni.animateX(((int) vrh.getCelkovyCas()) * 1000);
            }
        });
        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

    @Override
    public void onStart() {
        super.onStart();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client.connect();
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SikmyVzhuruVrh Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://eu.honzaik.fyzikaproandroid/http/host/path")
        );
        AppIndex.AppIndexApi.start(client, viewAction);
    }

    @Override
    public void onStop() {
        super.onStop();

        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        Action viewAction = Action.newAction(
                Action.TYPE_VIEW, // TODO: choose an action type.
                "SikmyVzhuruVrh Page", // TODO: Define a title for the content shown.
                // TODO: If you have web page content that matches this app activity's content,
                // make sure this auto-generated web page URL is correct.
                // Otherwise, set the URL to null.
                Uri.parse("http://host/path"),
                // TODO: Make sure this auto-generated app deep link URI is correct.
                Uri.parse("android-app://eu.honzaik.fyzikaproandroid/http/host/path")
        );
        AppIndex.AppIndexApi.end(client, viewAction);
        client.disconnect();
    }
}
