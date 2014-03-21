package application.rinkoid.kickers;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import application.rinkoid.DatabaseHelper;
import application.rinkoid.IStateChanged;
import application.rinkoid.R;

public class KickersFragment extends Fragment implements IStateChanged {
    private static ListView listview;

    public KickersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.listview, container, false);
        listview = (ListView) view.findViewById(R.id.listView);
        listview.setAdapter(new KickersAdapter(getActivity()));
        return view;
    }

    @Override
    public void onChampionshipChanged(int championship) {
        if( listview != null ) {
            KickersAdapter adapter = (KickersAdapter) listview.getAdapter();
            if(adapter!=null)
                adapter.Update(DatabaseHelper.getInstance(getActivity()).
                        GetKickers(championship));
        }
    }

    @Override
    public void onPageChanged(int page) {
    }
}
