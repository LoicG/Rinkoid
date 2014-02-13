package fr.sport.rinkoid.shedule;

import java.util.ArrayList;

import fr.sport.rinkoid.DatabaseHelper;
import fr.sport.rinkoid.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;

public class ScheduleFragment  extends Fragment implements OnClickListener, OnItemSelectedListener {
    private static ListView listview;
    private static Spinner spinner;
    private String currentChampionship_;

    private ArrayList<String> generateDays(int count){
        ArrayList<String> items = new ArrayList<String>();
        for(int i = 0; i < count; ++i)
            items.add("Journée " + String.valueOf(i+1));
        return items;
    }

    public ScheduleFragment() {
        this.currentChampionship_ = "N1";
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        DatabaseHelper db = new DatabaseHelper(getActivity());
        View view = inflater.inflate(R.layout.schedule, container, false);

        listview = (ListView) view.findViewById(R.id.listView);
        ScheduleAdapter adapter = new ScheduleAdapter(getActivity(),
                db.GetMatchs(currentChampionship_, 1));
        listview.setAdapter(adapter);

        spinner = (Spinner) view.findViewById(R.id.days);
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(
                getActivity(), 
                android.R.layout.simple_spinner_item,
                generateDays(db.GetScheduleCount("N1")));
                daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        spinner.setAdapter(daysAdapter);
        spinner.setOnItemSelectedListener(this);

        Button nextButton = (Button) view.findViewById(R.id.next);
        nextButton.setOnClickListener(this);
        Button prevButton = (Button) view.findViewById(R.id.prev);
        prevButton.setOnClickListener(this);
        return view;
    }

    public void Update(String championship) {
        currentChampionship_ = championship;
        if( spinner != null ) {
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
            if( adapter != null ) {
                adapter.clear();
                adapter.addAll(generateDays(new DatabaseHelper(getActivity()).GetScheduleCount(currentChampionship_)));
                adapter.notifyDataSetChanged();
            }
        }
        UpdateListView(1);
    }

    private void UpdateListView(int day) {
        if( listview != null ) {
            ScheduleAdapter adapter = (ScheduleAdapter) listview.getAdapter();
            if(adapter!=null)
                adapter.Update(new DatabaseHelper(getActivity()).GetMatchs(currentChampionship_,day));
        }
    }

    @Override
    public void onClick(View view) {
        if(spinner != null) {
            int position = spinner.getSelectedItemPosition();
            switch(view.getId()) {
                case R.id.next:
                    if( ( spinner.getCount() - 1 - position ) > 0 )
                        spinner.setSelection(position+1);
                break;
                case R.id.prev:
                    if( position - 1 >= 0 )
                        spinner.setSelection(position-1);
                break;
            }
        }
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position,
            long id) {
        UpdateListView(position+1);
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
    }
}

