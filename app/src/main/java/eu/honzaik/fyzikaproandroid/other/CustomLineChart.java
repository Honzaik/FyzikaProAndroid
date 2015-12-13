package eu.honzaik.fyzikaproandroid.other;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import eu.honzaik.fyzikaproandroid.R;

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
        this.setHardwareAccelerationEnabled(true);
        this.setMarkerView(new CustomMarkerView(context, R.layout.custom_marker_view_layout));

        XAxis xAxis = this.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setAvoidFirstLastClipping(true);
        xAxis.setValueFormatter(new CustomXAxisValueFormatter("s"));

        this.getAxisRight().setEnabled(false);
    }
}
