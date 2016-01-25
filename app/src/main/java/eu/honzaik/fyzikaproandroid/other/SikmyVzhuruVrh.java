package eu.honzaik.fyzikaproandroid.other;

import android.graphics.Color;
import android.util.Log;

import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class SikmyVzhuruVrh {

    private float pocatecniRychlost;
    private float pocatecniUhel; // v radianech
    private float celkovyCas;
    private float vyska;
    private float delka;
    private float zrychleni;
    private int type; // type = 0 - rychlost/celkovyCas; type = 1 - y/x (trajektorie); type = 2 - zrychleni/celkovyCas
    private static final float TIME_RESOLUTION = 0.01f; // 0.05 s
    private static final float TIHOVE_ZRYCHLENI = 9.80665f;
    private static final int COLOR0 = Color.RED;
    private static final int COLOR1 = Color.BLUE;
    private static final int COLOR2 = Color.GREEN;

    public SikmyVzhuruVrh(float pocatecniRychlost, float pocatecniUhel){
        this.pocatecniRychlost = pocatecniRychlost;
        this.pocatecniUhel = (float) Math.toRadians(pocatecniUhel);
        this.zrychleni = TIHOVE_ZRYCHLENI;
        this.celkovyCas = (float) ((pocatecniRychlost / zrychleni) * Math.sin(this.pocatecniUhel)) * 2;
        Log.d("FYS", "pocatecniRychlost: " + pocatecniRychlost + " | celkovyCas: " + celkovyCas + " | zrychleni:" + zrychleni + " | pocatecni uhel:" + pocatecniUhel + " " + this.pocatecniUhel);
    }

    public LineData generateLineData(int type){
        ArrayList<String> XValues = generateXValues(type);
        if(type == 0){
            ArrayList<Entry> rychlost = generateYValues(0);
            ArrayList<Entry> rychlostX = generateYValues(3);
            ArrayList<Entry> rychlostY = generateYValues(4);
            ArrayList<LineDataSet> datasets = generateRychlostDataSets(rychlost, rychlostX, rychlostY);
            return new LineData(XValues, datasets);
        }else{
            ArrayList<Entry> YValues = generateYValues(type);
            ArrayList<LineDataSet> dataset = generateDataSet(YValues, type);
            return new LineData(XValues, dataset);
        }


    }

    private ArrayList<String> generateXValues(int type){
        ArrayList<String> XValues = new ArrayList<String>();
        int pocetIteraci = (int) Math.ceil(celkovyCas /TIME_RESOLUTION);
        if(type == 0 || type == 2){
            String value = "";
            for(int i = 0; i <= pocetIteraci; i++){
                value = String.valueOf(Math.ceil((celkovyCas / pocetIteraci)*i*100)/100);
                XValues.add(value);
            }
        }
        else if(type == 1){
            String value = "";
            for(int i = 0; i <= pocetIteraci; i++){
                float x = (float) (pocatecniRychlost *  (((float) i / (float) pocetIteraci) * celkovyCas) * Math.cos(pocatecniUhel));
                value = String.valueOf(Math.ceil((x * 100)/100));
                XValues.add(value);
            }
        }

        return XValues;
    }

    private ArrayList<Entry> generateYValues(int type){
        ArrayList<Entry> YValues = new ArrayList<Entry>();
        int pocetIteraci = (int) Math.ceil(celkovyCas /TIME_RESOLUTION);
        float MAX_VYSKA = (float) (Math.pow(pocatecniRychlost, 2) / (2 * zrychleni));
        Entry entry = new Entry(0,0);
        for(int i = 0; i <= pocetIteraci; i++){
            /////////////
            if(type == 0){
                float rychlostX = (float) (pocatecniRychlost * Math.cos(pocatecniUhel));
                float rychlostY = (float) (pocatecniRychlost * Math.sin(pocatecniUhel) - zrychleni * (((float) i / (float) pocetIteraci) * celkovyCas));
                float rychlost = (float) Math.sqrt(Math.pow(rychlostX, 2) + Math.pow(rychlostY, 2));
                entry = new Entry(rychlost, i);
            }
            if(type == 3){
                float rychlostX = (float) (pocatecniRychlost * Math.cos(pocatecniUhel));
                entry = new Entry(rychlostX, i);
            }
            if(type == 4){
                float rychlostY = (float) (pocatecniRychlost * Math.sin(pocatecniUhel) - zrychleni * (((float) i / (float) pocetIteraci) * celkovyCas));
                entry = new Entry(rychlostY, i);
            }
            /////////////////////////
            if(type == 1){ // trajektorie
                float cas = (((float) i / (float) pocetIteraci) * celkovyCas);
                float y = (float) (pocatecniRychlost * cas * Math.sin(pocatecniUhel) - 0.5f * zrychleni * (float) Math.pow(cas, 2));
                if (y < 0) y = 0;
                entry = new Entry(y, i);
            }
            if(type == 2) entry = new Entry(zrychleni, i); //zrychleni
            YValues.add(entry);
        }
        return YValues;
    }

    private ArrayList<LineDataSet> generateDataSet(ArrayList<Entry> YValues, int type){
        ArrayList<LineDataSet> dataset = new ArrayList<LineDataSet>();
        String name = "";
        //if(type == 0) name = "Rychlost na čase";
        if(type == 1) name = "Trajektorie pohybu";
        if(type == 2) name = "Zrychlení na čase";
        LineDataSet set1 = new LineDataSet(YValues, name);
        set1.setDrawCircles(false); // bude to spojitej graf normalne
        set1.setDrawValues(false);
        //if(type == 0) set1.setColor(COLOR0);
        if(type == 1) set1.setColor(COLOR1);
        if(type == 2) set1.setColor(COLOR2);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        dataset.add(set1);
        return dataset;
    }
    private ArrayList<LineDataSet> generateRychlostDataSets(ArrayList<Entry> rychlost, ArrayList<Entry> rychlostX, ArrayList<Entry> rychlostY){
        ArrayList<LineDataSet> dataset = new ArrayList<LineDataSet>();
        LineDataSet set1 = new LineDataSet(rychlost, "Rychlost na čase");
        set1.setDrawCircles(false);
        set1.setDrawValues(false);

        LineDataSet set2 = new LineDataSet(rychlostX, "RychlostX na čase");
        set2.setDrawCircles(false);
        set2.setDrawValues(false);

        LineDataSet set3 = new LineDataSet(rychlostY, "RychlostY na čase");
        set3.setDrawCircles(false);
        set3.setDrawValues(false);

        set1.setColor(COLOR0);
        set2.setColor(COLOR1);
        set3.setColor(COLOR2);
        set1.setAxisDependency(YAxis.AxisDependency.LEFT);
        set2.setAxisDependency(YAxis.AxisDependency.LEFT);
        set3.setAxisDependency(YAxis.AxisDependency.LEFT);

        dataset.add(set1);
        dataset.add(set2);
        dataset.add(set3);
        return dataset;
    }

    public float getCelkovyCas(){
        return celkovyCas;
    }

}
