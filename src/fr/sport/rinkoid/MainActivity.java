package fr.sport.rinkoid;

import java.util.ArrayList;

import android.app.ActionBar;
import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends Activity implements ActionBar.OnNavigationListener {

    private ActionBar actionBar;
    private ArrayList<SpinnerNavItem> navSpinner;
    private TitleNavigationAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        Resources ressources = getResources();
        navSpinner = new ArrayList<SpinnerNavItem>();
        navSpinner.add(new SpinnerNavItem(ressources.getString(R.string.n1)));
        navSpinner.add(new SpinnerNavItem(ressources.getString(R.string.n2n)));
        navSpinner.add(new SpinnerNavItem(ressources.getString(R.string.n2s)));

        adapter = new TitleNavigationAdapter(getApplicationContext(), navSpinner);
        actionBar.setListNavigationCallbacks(adapter, this);
    }

    @Override
    public boolean onNavigationItemSelected(int itemPosition, long itemId) {
        Toast.makeText(getApplicationContext(),
        		String.valueOf(itemPosition), Toast.LENGTH_LONG).show();
        return false;
    }
}
