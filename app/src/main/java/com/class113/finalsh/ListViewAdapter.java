package com.class113.finalsh;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ListViewAdapter extends ArrayAdapter<WorkInfo> {
    private Context context;
    private int resource;

    public ListViewAdapter(@NonNull Context context, int resource, @NonNull List<WorkInfo> objects) {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        if( convertView == null )
            convertView = layoutInflater.inflate(resource,parent,false);
        if(position % 2 == 0)
            convertView.setBackgroundColor(0xFFB0DDCB);

        TextView workNameTV = convertView.findViewById(R.id.work_name);
        TextView workDescTV = convertView.findViewById(R.id.work_description);
        WorkInfo info = getItem(position);
        workNameTV.setText(info.getWorkName());
        workDescTV.setText(info.getWorkDescription());


        return  convertView;
    }
}
