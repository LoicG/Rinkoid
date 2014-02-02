package fr.sport.rinkoid.kickers;

import java.util.ArrayList;

import fr.sport.rinkoid.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class KickersFragment extends Fragment {
    private static ListView listview;

    private ArrayList<Kicker> generateData(){
        ArrayList<Kicker> items = new ArrayList<Kicker>();
        items.add(new Kicker("1","buteur1"));
        items.add(new Kicker("2","buteur2"));
        items.add(new Kicker("3","buteur3"));
        return items;
    }

    public KickersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview, container, false);
        listview = (ListView) view.findViewById(R.id.listView);
        KickersAdapter adapter = new KickersAdapter(getActivity(), generateData());
        listview.setAdapter(adapter);
        return view;
    }
}
