package fr.sport.rinkoid;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class PageFragment extends Fragment {

    private static View view;

    public static final PageFragment newInstance(String sampleText) {
        PageFragment f = new PageFragment();
        Bundle b = new Bundle();
        b.putString("bString", sampleText);
        f.setArguments(b);
    return f;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.page_fragment, container, false);
        String sampleText = getArguments().getString("bString");
        TextView txtSampleText = (TextView) view.findViewById(R.id.txtViewSample);
        txtSampleText.setText(sampleText);
        return view;
    }
}
