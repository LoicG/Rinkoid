package fr.sport.rinkoid.kickers;

import fr.sport.rinkoid.DatabaseHelper;
import fr.sport.rinkoid.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

public class KickersFragment extends Fragment {
    private static ListView listview;
    
    public KickersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview, container, false);
        listview = (ListView) view.findViewById(R.id.listView);

        KickersAdapter adapter = new KickersAdapter(getActivity(),
                new DatabaseHelper(getActivity()).GetKickers("N1"));
        listview.setAdapter(adapter);
        return view;
    }

    public void Udpate(String championship) {
        if( listview != null ) {
            KickersAdapter adapter = (KickersAdapter) listview.getAdapter();
            if(adapter!=null)
                adapter.Update(new DatabaseHelper(getActivity()).GetKickers(championship));
        }
    }
}
