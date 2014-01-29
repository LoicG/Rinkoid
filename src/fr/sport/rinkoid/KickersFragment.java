package fr.sport.rinkoid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class KickersFragment extends Fragment {

    private static ListView listview;
    private String[] days = { "buteur1", "buteur2", "buteur3", "buteurs4" };

    public KickersFragment()
    {}

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
    	
        View view = inflater.inflate(R.layout.listview, container, false);
        listview = (ListView) view.findViewById(R.id.listView);
        listview.setAdapter( new ArrayAdapter<String>(getActivity(), R.layout.item, days));
        return view;
    }
}
