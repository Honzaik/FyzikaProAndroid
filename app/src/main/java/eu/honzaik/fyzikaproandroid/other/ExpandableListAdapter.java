package eu.honzaik.fyzikaproandroid.other;

import android.content.Context;
import android.graphics.Typeface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;

import java.util.HashMap;
import java.util.List;

import eu.honzaik.fyzikaproandroid.R;
import eu.honzaik.fyzikaproandroid.layouts.PrevodnikLayout;

public class ExpandableListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private List<String> headerTitles;
    private HashMap<String, Integer> childTitles;
    private PrevodnikLayout[] layouts = new PrevodnikLayout[9];
    private View[] views = new View[9];

    public ExpandableListAdapter(Context context, List<String> headerTitles, HashMap<String, Integer> childTitles){
        this.context = context;
        this.headerTitles = headerTitles;
        this.childTitles = childTitles;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        View v = convertView;
        String headerTitle = (String) getGroup(groupPosition);
        if(convertView == null){
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.prevody_list_group, null);
        }
        TextView headerTextView = (TextView) convertView.findViewById(R.id.prevody_list_group_text_view);
        headerTextView.setTypeface(null, Typeface.BOLD);
        headerTextView.setText(headerTitle);
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        int layoutType = (Integer) getChild(groupPosition, childPosition);
        if(views[layoutType] == null) {
            LayoutInflater inflater = (LayoutInflater) this.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            views[layoutType] = inflater.inflate(R.layout.prevody_prevodnik_layout, null);
        }
        if(layouts[layoutType] == null){
            layouts[layoutType] = (PrevodnikLayout) views[layoutType].findViewById(R.id.prevodnik_layout);
            layouts[layoutType].init(layoutType);
        }
        return views[layoutType];
    }

    @Override
    public int getGroupCount() {
        return this.headerTitles.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.headerTitles.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return this.childTitles.get(this.headerTitles.get(groupPosition)).intValue();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }
}
