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
    private ArrayList<Match> items;

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
        Match match = items.get(position);
        SetText(R.id.home, rowView, match.getHome());
        SetText(R.id.score, rowView, match.getScore());
        SetText(R.id.outside, rowView, match.getOutside());
        return rowView;
    }

    private void SetText( int id, View rowView, String text ) {
        TextView view = (TextView) rowView.findViewById(id);
        view.setText(text);
    }

    public void Update(ArrayList<Match> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
