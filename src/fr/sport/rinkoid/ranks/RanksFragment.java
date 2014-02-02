package fr.sport.rinkoid.ranks;

import java.util.ArrayList;

import fr.sport.rinkoid.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RanksFragment extends Fragment {
    private static ListView listview;

    private ArrayList<Rank> generateData(){
        ArrayList<Rank> items = new ArrayList<Rank>();
        items.add(new Rank("1","club1"));
        items.add(new Rank("2","club2"));
        items.add(new Rank("3","club3"));
        return items;
    }

    public RanksFragment(){
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview, container, false);
        listview = (ListView) view.findViewById(R.id.listView);
        RanksAdapter adapter = new RanksAdapter(getActivity(), generateData());
        listview.setAdapter(adapter);
        return view;
    }
}
