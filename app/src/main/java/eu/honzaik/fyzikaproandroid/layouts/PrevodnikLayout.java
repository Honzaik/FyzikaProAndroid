package eu.honzaik.fyzikaproandroid.layouts;

import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.math.BigDecimal;
import java.math.MathContext;

import eu.honzaik.fyzikaproandroid.R;

public class PrevodnikLayout extends LinearLayout {

    private EditText zadaniEditText;
    private Spinner zadaniSpinner;
    private TextView vysledekTextView;
    private Spinner vysledekSpinner;
    private int layoutType;
    private Context context;
    private double jednotkaZadani = 1d;
    private double jednotkaVysledek = 1d;
    private int arrayValueNumber;

    public PrevodnikLayout(Context context) {
        super(context);
        this.context = context;
    }

    public PrevodnikLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.context = context;
    }

    public PrevodnikLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.context = context;
    }

    public void init(int layoutType){
        this.layoutType = layoutType;
        zadaniEditText = (EditText) this.findViewById(R.id.prevodnik_zadani_edit_text);
        zadaniSpinner = (Spinner) this.findViewById(R.id.prevodnik_zadani_spinner);
        vysledekTextView = (TextView) this.findViewById(R.id.prevodnik_vysledek_text_view);
        vysledekSpinner = (Spinner) this.findViewById(R.id.prevodnik_vysledek_spinner);

        int arrayLabelNumber = 0;

        switch (layoutType){
            case 0: arrayLabelNumber = R.array.cas_array_labels;
                break;
            case 1: arrayLabelNumber = R.array.delka_array_labels;
                break;
            case 2: arrayLabelNumber = R.array.objem_array_labels;
                break;
            case 3: arrayLabelNumber = R.array.obsah_array_labels;
                break;
            case 4: arrayLabelNumber = R.array.prace_array_labels;
                break;
            case 5: arrayLabelNumber = R.array.rychlost_array_labels;
                break;
            case 6: arrayLabelNumber = R.array.teplota_array_labels;
                break;
            case 7: arrayLabelNumber = R.array.tlak_array_labels;
                break;
            case 8: arrayLabelNumber = R.array.vykon_array_labels;
                break;
            default: arrayLabelNumber = R.array.cas_array_labels;
                break;
        }

        switch (layoutType){
            case 0: arrayValueNumber = R.array.cas_array_values;
                break;
            case 1: arrayValueNumber = R.array.delka_array_values;
                break;
            case 2: arrayValueNumber = R.array.objem_array_values;
                break;
            case 3: arrayValueNumber = R.array.obsah_array_values;
                break;
            case 4: arrayValueNumber = R.array.prace_array_values;
                break;
            case 5: arrayValueNumber = R.array.rychlost_array_values;
                break;
            case 6: arrayValueNumber = R.array.teplota_array_values;
                break;
            case 7: arrayValueNumber = R.array.tlak_array_values;
                break;
            case 8: arrayValueNumber = R.array.vykon_array_values;
                break;
            default: arrayValueNumber = R.array.cas_array_values;
                break;
        }


        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context, arrayLabelNumber, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        zadaniSpinner.setAdapter(adapter);
        vysledekSpinner.setAdapter(adapter);

        zadaniSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("FYS", "ZADANI J: " + getResources().getStringArray(arrayValueNumber)[zadaniSpinner.getSelectedItemPosition()]);
                jednotkaZadani = Double.parseDouble(getResources().getStringArray(arrayValueNumber)[zadaniSpinner.getSelectedItemPosition()]);
                recalculate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                jednotkaZadani = 1d;
                recalculate();
            }
        });

        vysledekSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                //Log.d("FYS", "VYSLEDEK J: " + getResources().getStringArray(arrayValueNumber)[vysledekSpinner.getSelectedItemPosition()]);
                jednotkaVysledek = Double.parseDouble(getResources().getStringArray(arrayValueNumber)[vysledekSpinner.getSelectedItemPosition()]);
                recalculate();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                jednotkaVysledek = 1d;
                recalculate();
            }
        });

        zadaniEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                recalculate();
            }
        });

    }

    private void recalculate(){
        BigDecimal zadani = BigDecimal.ZERO;
        try{
            if(String.valueOf(zadaniEditText.getText()) == null || String.valueOf(zadaniEditText.getText()).isEmpty()){
                zadani = BigDecimal.ZERO;
            }else{
                zadani = new BigDecimal(String.valueOf(zadaniEditText.getText()));
            }
        }catch(Exception e){
            //Log.d("FYS", e.getMessage());
        }

        BigDecimal vysledek = BigDecimal.ZERO;
        if(layoutType != 6){
            vysledek = calculate(zadani, this.jednotkaZadani, this.jednotkaVysledek);
        }else{
            vysledek = calculateTeplota(zadani, this.jednotkaZadani, this.jednotkaVysledek);
        }
        vysledekTextView.setText(String.valueOf(vysledek.doubleValue()));
    }

    private BigDecimal calculate(BigDecimal zadani, double jednotkaZadani, double jednotkaVysledek){
        BigDecimal vysledek = BigDecimal.ZERO;
        if(jednotkaZadani == jednotkaVysledek) vysledek = zadani;
        else vysledek = zadani.multiply(BigDecimal.valueOf(jednotkaZadani)).divide(BigDecimal.valueOf(jednotkaVysledek), MathContext.DECIMAL128);
        return vysledek;
    }

    private BigDecimal calculateTeplota(BigDecimal zadani, double jednotkaZadani, double jednotkaVysledek){
        BigDecimal vysledek = BigDecimal.ZERO;
        BigDecimal zakladniJednotka = BigDecimal.ZERO;
        if(jednotkaZadani == jednotkaVysledek) vysledek = zadani;
        if(jednotkaZadani == 1) zakladniJednotka = zadani;
        if(jednotkaZadani == 2) zakladniJednotka = zadani.add(new BigDecimal("273.15"));
        if(jednotkaZadani == 3) zakladniJednotka = zadani.subtract(new BigDecimal("32")).divide(new BigDecimal("1.8"), MathContext.DECIMAL128).add(new BigDecimal("273.15"));
        if(jednotkaVysledek == 1) vysledek = zakladniJednotka;
        if(jednotkaVysledek == 2) vysledek = zakladniJednotka.subtract(new BigDecimal("273.15"));
        if(jednotkaVysledek == 3) vysledek = zakladniJednotka.subtract(new BigDecimal("273.15")).multiply(new BigDecimal("1.8")).add(new BigDecimal("32"));
        return vysledek;
    }

}
