package fr.sport.rinkoid.shedule;

import java.util.ArrayList;

import fr.sport.rinkoid.DatabaseHelper;
import fr.sport.rinkoid.IStateChanged;
import fr.sport.rinkoid.R;
import fr.sport.rinkoid.Tools;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.Spinner;

public class ScheduleFragment  extends Fragment implements OnClickListener,
        OnItemSelectedListener, IStateChanged {
    private static ListView listview;
    private static Spinner spinner;
    private int currentChampionship_;

    private ArrayList<String> generateDays(int championship){
        ArrayList<String> items = new ArrayList<String>();
        for(int i = 0; i < Tools.GetDaysCount(championship); ++i) {
            if( i== 0) {
                items.add("1ère journée");
            }
            else
                items.add(String.valueOf(i+1) + "e journée");
        }
        return items;
    }

    public ScheduleFragment() {
        this.currentChampionship_ = -1;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState) {
        DatabaseHelper db = DatabaseHelper.getInstance(getActivity());
        View view = inflater.inflate(R.layout.schedule, container, false);

        listview = (ListView) view.findViewById(R.id.listView);
        ScheduleAdapter adapter = new ScheduleAdapter(getActivity(),
                db.GetMatchs(currentChampionship_, 1));
        listview.setAdapter(adapter);

        spinner = (Spinner) view.findViewById(R.id.days);
        ArrayAdapter<String> daysAdapter = new ArrayAdapter<String>(
                getActivity(),
                R.layout.spinner_center_item,
                generateDays(currentChampionship_));
                daysAdapter.setDropDownViewResource(R.layout.spinner_center_item);
        spinner.setAdapter(daysAdapter);
        spinner.setOnItemSelectedListener(this);

        ImageButton nextButton = (ImageButton) view.findViewById(R.id.next);
        nextButton.setOnClickListener(this);
        ImageButton prevButton = (ImageButton) view.findViewById(R.id.prev);
        prevButton.setOnClickListener(this);
        return view;
    }

    private void UpdateListView(int day) {
        if( listview != null ) {
            ScheduleAdapter adapter = (ScheduleAdapter) listview.getAdapter();
            if(adapter!=null)
                adapter.Update(DatabaseHelper.getInstance(getActivity()).
                        GetMatchs(currentChampionship_,day));
        }
    }

    @Override
    public void onClick(View view) {
        if(spinner != null) {
            int position = spinner.getSelectedItemPosition();
            switch(view.getId()) {
                case R.id.next:
                    if((spinner.getCount() - 1 - position) > 0)
                        spinner.setSelection(position+1);
                break;
                case R.id.prev:
                    if(position - 1 >= 0)
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

    @Override
    public void onChampionshipChanged(int championship) {
        boolean changed = currentChampionship_ != championship;
        currentChampionship_ = championship;
        if(spinner != null) {
            ArrayAdapter<String> adapter = (ArrayAdapter<String>) spinner.getAdapter();
            if( adapter != null ) {
                adapter.clear();
                adapter.addAll(generateDays(currentChampionship_));
                adapter.notifyDataSetChanged();
            }

            int count = ( changed ? DatabaseHelper.getInstance(getActivity()).
                    GetCurrentDay(currentChampionship_) : getCurrentDay() ) - 1;
            if( count == spinner.getSelectedItemPosition() )
                UpdateListView(count+1);
            else
                spinner.setSelection(count);
       }
    }

    @Override
    public void onPageChanged(int page) {
    }

    public int getCurrentDay() {
        return spinner == null ? 1 : spinner.getSelectedItemPosition() + 1;
    }
}

