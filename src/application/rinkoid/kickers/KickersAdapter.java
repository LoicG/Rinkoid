package application.rinkoid.kickers;

import java.util.ArrayList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import application.rinkoid.R;

public class KickersAdapter extends ArrayAdapter<Kicker> {
    private final Context context;
    private ArrayList<Kicker> items;

    public KickersAdapter(Context context) {
        super(context, R.layout.kicker_row);
        this.context = context;
        this.items = new ArrayList<Kicker>();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
            .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        if (position == 0) {
            return inflater.inflate(R.layout.kicker_header, parent, false);
        } else {
            View rowView = inflater.inflate(R.layout.kicker_row, parent, false);
            Kicker kicker = items.get(position);
            SetText(R.id.rank, rowView, String.valueOf(position));
            SetText(R.id.name, rowView, kicker.getName());
            SetText(R.id.club, rowView, kicker.getClub());
            SetText(R.id.goals, rowView, String.valueOf(kicker.getGoals()));
            return rowView;
        }
    }

    private void SetText( int id, View rowView, String text ) {
        TextView view = (TextView) rowView.findViewById(id);
        view.setText(text);
    }

    public void Update(ArrayList<Kicker> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }
}