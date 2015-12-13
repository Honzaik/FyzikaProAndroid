package eu.honzaik.fyzikaproandroid.other;

import android.content.Context;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;

import eu.honzaik.fyzikaproandroid.R;

public class CustomMarkerView extends MarkerView{

    private TextView contentTextView;

    public CustomMarkerView(Context context, int layoutResource) {
        super(context, layoutResource);
        contentTextView = (TextView) findViewById(R.id.custom_marker_text_view);
    }

    @Override
    public void refreshContent(Entry e, Highlight highlight) {
        contentTextView.setText("" + e.getVal());
    }

    @Override
    public int getXOffset(float xpos) {
        return -(getWidth() / 2);
    }

    @Override
    public int getYOffset(float ypos) {
        return -getHeight();
    }
}
