package eu.honzaik.fyzikaproandroid.other;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class SvislyVzhuruVrh {

    private float pocatecniRychlost;
    private float celkovyCas;
    private float vyska;
    private float zrychleni;
    private int type; // type = 0 - rychlost/celkovyCas; type = 1 - y/x (trajektorie); type = 2 - zrychleni/celkovyCas
    private static final float TIME_RESOLUTION = 0.01f; // 0.01 s
    private static final float TIHOVE_ZRYCHLENI = 9.80665f;
    private static final int COLOR0 = Color.RED;
    private static final int COLOR1 = Color.BLUE;
    private static final int COLOR2 = Color.GREEN;

    public SvislyVzhuruVrh(float pocatecniRychlost){
        this.pocatecniRychlost = pocatecniRychlost;
        this.zrychleni = TIHOVE_ZRYCHLENI;
        this.celkovyCas = (pocatecniRychlost / zrychleni) * 2;
    }

    public LineData generateLineData(int type){
        ArrayList<String> XValues = generateXValues(type);
        ArrayList<Entry> YValues = generateYValues(type);
        ArrayList<LineDataSet> dataset = generateDataSet(YValues, type);
        return new LineData(XValues, dataset);
    }

    private ArrayList<String> generateXValues(int type){ //type = 0 celkovyCas | type = 1 svisly vrhuhu -10 az 10 | type 2 celkovyCas
        ArrayList<String> XValues = new ArrayList<String>();
        int pocetIteraci = (int) Math.ceil(celkovyCas /TIME_RESOLUTION);
        //if(type == 0 || type == 2){
            String value = "";
            for(int i = 0; i <= pocetIteraci; i++){
                value = String.valueOf(Math.ceil((celkovyCas / pocetIteraci)*i*100)/100);
                XValues.add(value);
            }
        //}
        /*
        else if(type == 1){
            String value = "";
            for(int i = 0; i <= pocetIteraci; i++){
                value = String.valueOf(0);
                XValues.add(value);
            }
        }*/

        return XValues;
    }

    private ArrayList<Entry> generateYValues(int type){
        ArrayList<Entry> YValues = new ArrayList<Entry>();
        int pocetIteraci = (int) Math.ceil(celkovyCas /TIME_RESOLUTION);
        float MAX_VYSKA = (float) (Math.pow(pocatecniRychlost, 2) / (2 * zrychleni));
        Entry entry = new Entry(0,0);
        for(int i = 0; i <= pocetIteraci; i++){
            if(type == 0){
                float rychlost = Math.abs(pocatecniRychlost - zrychleni * ((float) i / (float) pocetIteraci) * celkovyCas);
                entry = new Entry(rychlost, i);
            }
            if(type == 1){
                float cas = ((float) i / (float) pocetIteraci) * celkovyCas;
                float rychlost = pocatecniRychlost - zrychleni * cas;
                float draha = 0;
                if(rychlost > 0){  //letí vzhuru rychlost je kladná
                    draha = pocatecniRychlost * cas - 0.5f * zrychleni * (float) Math.pow(cas, 2);
                }else if(rychlost == 0){ // maximální výška výstupu = rychlost bude v ten moment 0
                    draha = MAX_VYSKA;
                }
                else{ // volny pad z vysky
                    draha = MAX_VYSKA + 0.5f * zrychleni * (float) Math.pow(cas - (celkovyCas / 2), 2);
                }

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

    public float getCelkovyCas(){
        return celkovyCas;
    }

}
