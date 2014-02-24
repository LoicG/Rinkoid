package fr.sport.rinkoid.shedule;

import java.util.ArrayList;

import fr.sport.rinkoid.R;

import android.content.Context;
import android.graphics.Typeface;
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
        int diff = Split(match.getScore());
        SetText(R.id.home, rowView, match.getHome(), diff > 0);
        SetText(R.id.score, rowView, match.getScore(), false);
        SetText(R.id.outside, rowView, match.getOutside(), diff < 0);
        return rowView;
    }

    private void SetText(int id, View rowView, String text, boolean bold) {
        TextView view = (TextView) rowView.findViewById(id);
        view.setText(text);
        if(bold)
            view.setTypeface(null, Typeface.BOLD);
    }

    public void Update(ArrayList<Match> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    private int Split(String score) {
        score = score.replace(" ", "");
        if(score.contains("-")) {
            String[] splits = score.split("-");
            if(splits.length == 2) {
                return Integer.parseInt(splits[0]) -
                       Integer.parseInt(splits[1]);
            }
        }
        return 0;
    }
}
