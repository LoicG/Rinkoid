package fr.sport.rinkoid.ranks;

import java.util.ArrayList;

import fr.sport.rinkoid.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class RanksAdapter extends ArrayAdapter<Rank> {
    private final Context context;
    private final ArrayList<Rank> items;

    public RanksAdapter(Context context, ArrayList<Rank> items) {
        super(context, R.layout.rank_row, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.rank_row, parent, false);
        TextView rankView = (TextView) rowView.findViewById(R.id.rank);
        TextView nameView = (TextView) rowView.findViewById(R.id.name);
        rankView.setText(items.get(position).getRank());
        nameView.setText(items.get(position).getName());
        return rowView;
    }
}