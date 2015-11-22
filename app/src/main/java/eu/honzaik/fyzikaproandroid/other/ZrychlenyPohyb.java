package eu.honzaik.fyzikaproandroid.other;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class ZrychlenyPohyb {

    private float pocatecniRychlost;
    private float cas;
    private float draha;
    private float zrychleni;
    private int type; // type = 0 - rychlost/cas; type = 1 - draha/cas; type = 2 - zrychleni/cas
    private static final float TIME_RESOLUTION = 0.05f; // 0.05 s
    private static final int COLOR0 = Color.RED;
    private static final int COLOR1 = Color.BLUE;
    private static final int COLOR2 = Color.GREEN;

    public ZrychlenyPohyb(float pocatecniRychlost, float cas, float zrychleni){
        this.pocatecniRychlost = pocatecniRychlost;
        this.cas = cas;
        this.zrychleni = zrychleni;
        this.draha = pocatecniRychlost * cas +  0.5f * zrychleni * (float) Math.pow(cas, 2);
        Log.d("FYS", "pocatecniRychlost: " + pocatecniRychlost + " | cas: " + cas + " | draha: " + draha + " | zrychleni:" + zrychleni);
    }

    public LineData generateLineData(int type){
        ArrayList<String> XValues = generateXValues(type);
        ArrayList<Entry> YValues = generateYValues(type);
        ArrayList<LineDataSet> dataset = generateDataSet(YValues, type);
        return new LineData(XValues, dataset);
    }

    private ArrayList<String> generateXValues(int type){
        ArrayList<String> XValues = new ArrayList<String>();
        int pocetIteraci = (int) Math.ceil(cas/TIME_RESOLUTION);
        String value = "";
        for(int i = 0; i <= pocetIteraci; i++){
            value = String.valueOf(Math.ceil((cas / pocetIteraci)*i*100)/100);
            XValues.add(value);
        }
        return XValues;
    }

    private ArrayList<Entry> generateYValues(int type){
        ArrayList<Entry> YValues = new ArrayList<Entry>();
        int pocetIteraci = (int) Math.ceil(cas/TIME_RESOLUTION);
        Log.d("FYS", "pocetiteraci " + pocetIteraci);
        Entry entry = new Entry(0,0);
        for(int i = 0; i <= pocetIteraci; i++){
            if(type == 0){
                float rychlost = pocatecniRychlost + zrychleni * ((float)i / (float)pocetIteraci) * cas;
                entry = new Entry(rychlost, i);
            }
            if(type == 1){
                float draha = pocatecniRychlost * cas +  0.5f * zrychleni * (float) Math.pow(((float)i / (float)pocetIteraci) * cas, 2);
                entry = new Entry(draha, i); //draha
            }
            if(type == 2) entry = new Entry(zrychleni, i); //zrychleni
            YValues.add(entry);
        }
        return YValues;
    }

    private ArrayList<LineDataSet> generateDataSet(ArrayList<Entry> YValues, int type){
        ArrayList<LineDataSet> dataset = new ArrayList<LineDataSet>();
        String name = "";
        if(type == 0) name = "Rychlost na čase";
        if(type == 1) name = "Dráha na čase";
        if(type == 2) name = "Zrychlení na čase";
        LineDataSet set1 = new LineDataSet(YValues, name);
        set1.setDrawCircles(false); // bude to spojitej graf normalne
        set1.setDrawValues(false);
        if(type == 0) set1.setColor(COLOR0);
        if(type == 1) set1.setColor(COLOR1);
        if(type == 2) set1.setColor(COLOR2);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset.add(set1);
        return dataset;
    }
}
