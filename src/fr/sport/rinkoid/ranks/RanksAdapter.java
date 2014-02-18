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
    private ArrayList<Rank> items;

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
        Rank rank = items.get(position);
        SetText(R.id.rank, rowView, String.valueOf(position+1));
        SetText(R.id.club, rowView, rank.getClub());
        SetText(R.id.points, rowView, String.valueOf(rank.getPoints()));
        SetText(R.id.win, rowView, String.valueOf(rank.getWin()));
        SetText(R.id.draw, rowView, String.valueOf(rank.getDraw()));
        SetText(R.id.lost, rowView, String.valueOf(rank.getLost()));
        SetText(R.id.diff, rowView, String.valueOf(rank.getDiff()));
        return rowView;
    }

    private void SetText( int id, View rowView, String text ) {
        TextView view = (TextView) rowView.findViewById(id);
        view.setText(text);
    }

    public void Update(ArrayList<Rank> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }
}

