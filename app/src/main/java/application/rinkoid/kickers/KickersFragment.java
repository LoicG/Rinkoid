package application.rinkoid.kickers;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import application.rinkoid.DatabaseHelper;
import application.rinkoid.IStateChanged;
import application.rinkoid.R;

public class KickersFragment extends Fragment implements IStateChanged {
    private static ListView listview;
    private static TextView warning;

    public KickersFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.kickers, container, false);
        warning = (TextView) view.findViewById(R.id.missing);
        listview = (ListView) view.findViewById(R.id.listView);
        listview.setAdapter(new KickersAdapter(getActivity()));
        return view;
    }

    @Override
    public void onChampionshipChanged(int championship) {
        if( listview != null ) {
            KickersAdapter adapter = (KickersAdapter) listview.getAdapter();
            if(adapter!=null && warning!=null) {
                ArrayList<Kicker> kickers = DatabaseHelper.getInstance(getActivity()).
                        GetKickers(championship);
                warning.setVisibility(kickers.isEmpty() ? View.VISIBLE : View.GONE);
                adapter.Update(kickers);
            }
               
        }
    }

    @Override
    public void onPageChanged(int page) {
    }
}
