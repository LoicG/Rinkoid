package fr.sport.rinkoid.shedule;

import java.util.ArrayList;

import fr.sport.rinkoid.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class ScheduleFragment  extends Fragment {
    private static ListView listview;

    private ArrayList<Match> generateData(){
        ArrayList<Match> items = new ArrayList<Match>();
        items.add(new Match("match1"));
        items.add(new Match("match2"));
        items.add(new Match("match3"));
        return items;
    }

    private ArrayList<String> generateDays(){
        ArrayList<String> items = new ArrayList<String>();
        items.add("journee1");
        items.add("journee2");
        items.add("journee3");
        return items;
    }

    public ScheduleFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.schedule, container, false);
        listview = (ListView) view.findViewById(R.id.listView);
        ScheduleAdapter adapter = new ScheduleAdapter(getActivity(), generateData());
        listview.setAdapter(adapter);
        Spinner spinner = (Spinner) view.findViewById(R.id.days);
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(
        getActivity(), 
        android.R.layout.simple_spinner_item,
        generateDays());
        daysAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item); 
        spinner.setAdapter(daysAdapter);
        return view;
    }
}
