package eu.honzaik.fyzikaproandroid.other;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

public class CustomLineChart extends LineChart {

    private static final int BACKGROUND_COLOR = Color.LTGRAY;

    public CustomLineChart(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.setBackgroundColor(BACKGROUND_COLOR);
        this.setDrawGridBackground(true);
        this.setDrawBorders(true);
        this.setTouchEnabled(true);
        this.setScaleEnabled(true);
        this.setAutoScaleMinMaxEnabled(true);

        XAxis xAxis = this.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(true);

        this.getAxisRight().setEnabled(false);
    }
}

        /*chartRychlost.setOnChartValueSelectedListener(new OnChartValueSelectedListener() {
            @Override
            public void onValueSelected(Entry e, int dataSetIndex, Highlight h) {
                Log.d("FYS", "value: " + e.getVal() + " | index: " + dataSetIndex);
            }

            @Override
            public void onNothingSelected() {

            }
        });
        */