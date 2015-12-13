package eu.honzaik.fyzikaproandroid.other;

import com.github.mikephil.charting.formatter.XAxisValueFormatter;
import com.github.mikephil.charting.utils.ViewPortHandler;

public class CustomXAxisValueFormatter implements XAxisValueFormatter {

    private String jednotka;

    public CustomXAxisValueFormatter(String jednotka){
        this.jednotka = jednotka;
    }

    @Override
    public String getXValue(String original, int index, ViewPortHandler viewPortHandler) {
        return original + " " + jednotka;
    }
}
