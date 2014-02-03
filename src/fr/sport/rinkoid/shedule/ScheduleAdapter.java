package fr.sport.rinkoid.shedule;

import java.util.ArrayList;

import fr.sport.rinkoid.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ScheduleAdapter extends ArrayAdapter<Match> {
    private final Context context;
    private final ArrayList<Match> items;

    public ScheduleAdapter(Context context, ArrayList<Match> items) {
        super(context, R.layout.schedule_row, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.schedule_row, parent, false);
        TextView nameView = (TextView) rowView.findViewById(R.id.name);
        nameView.setText(items.get(position).getName());
        return rowView;
    }
}