package eu.honzaik.fyzikaproandroid.other;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.formatter.YAxisValueFormatter;

public class CustomYAxisValueFormatter implements YAxisValueFormatter{

    private String jednotka;

    public CustomYAxisValueFormatter(String jednotka){
        this.jednotka = jednotka;
    }

    @Override
    public String getFormattedValue(float value, YAxis yAxis) {
        return String.valueOf(value) + " " + jednotka;
    }
}
