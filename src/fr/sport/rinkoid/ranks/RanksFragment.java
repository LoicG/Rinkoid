package fr.sport.rinkoid.ranks;

import fr.sport.rinkoid.DatabaseHelper;
import fr.sport.rinkoid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class RanksFragment extends Fragment {
    private static ListView listview;

    public RanksFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview, container, false);
        listview = (ListView) view.findViewById(R.id.listView);
        RanksAdapter adapter = new RanksAdapter(getActivity(),
                new DatabaseHelper(getActivity()).GetRanks("N1"));
        listview.setAdapter(adapter);
        return view;
    }

    public void Udpate(String championship) {
        if( listview != null ) {
            RanksAdapter adapter = (RanksAdapter) listview.getAdapter();
            if(adapter!=null)
                adapter.Update(new DatabaseHelper(getActivity()).GetRanks(championship));
        }
    }
}

