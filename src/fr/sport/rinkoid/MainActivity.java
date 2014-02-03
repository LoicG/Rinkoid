package fr.sport.rinkoid;

import java.util.ArrayList;
import java.util.List;

import fr.sport.rinkoid.bar.SpinnerNavItem;
import fr.sport.rinkoid.bar.TitleNavigationAdapter;
import fr.sport.rinkoid.kickers.KickersFragment;
import fr.sport.rinkoid.ranks.RanksFragment;
import fr.sport.rinkoid.shedule.ScheduleFragment;

import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends FragmentActivity  implements ActionBar.OnNavigationListener, OnTabChangeListener, OnPageChangeListener {

    private ActionBar actionBar;
    private ArrayList<SpinnerNavItem> navSpinner;
    private TitleNavigationAdapter adapter;
    PageAdapter pageAdapter;
    private ViewPager viewPager;
    private TabHost tabHost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        Resources ressources = getResources();
        navSpinner = new ArrayList<SpinnerNavItem>();
        navSpinner.add(new SpinnerNavItem(ressources.getString(R.string.n1)));
        navSpinner.add(new SpinnerNavItem(ressources.getString(R.string.n2n)));
        navSpinner.add(new SpinnerNavItem(ressources.getString(R.string.n2s)));
        adapter = new TitleNavigationAdapter(getApplicationContext(), navSpinner);
        actionBar.setListNavigationCallbacks(adapter, this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        tabHost = (TabHost) findViewById(android.R.id.tabhost);
        tabHost.setup();
        AddTab(tabHost, tabHost.newTabSpec("scheduleTab").setIndicator(
            ressources.getString(R.string.schedule)));
        AddTab(tabHost, tabHost.newTabSpec("rankingTab").setIndicator(
            ressources.getString(R.string.ranking)));
        AddTab(tabHost, tabHost.newTabSpec("kickersTab").setIndicator(
            ressources.getString(R.string.kickers)));
        tabHost.setOnTabChangedListener(this);

        List<Fragment> fragments = CreatePageFragments();
        pageAdapter = new PageAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(pageAdapter);
        viewPager.setOnPageChangeListener(MainActivity.this);
    }

    private void AddTab(TabHost tabHost, TabHost.TabSpec tabSpec) {
        tabSpec.setContent(new PageTabFactory(this));
        tabHost.addTab(tabSpec);
    }

    public void onTabChanged(String tag) {
        int pos = this.tabHost.getCurrentTab();
        this.viewPager.setCurrentItem(pos);
    }

    @Override
    public void onPageScrollStateChanged(int arg0) {
    }

    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {
        int pos = this.viewPager.getCurrentItem();
        this.tabHost.setCurrentTab(pos);
    }

    @Override
        public void onPageSelected(int arg0) {
    }

    private List<Fragment> CreatePageFragments() {
        List<Fragment> fList = new ArrayList<Fragment>();
        fList.add(new ScheduleFragment());
        fList.add(new RanksFragment());
        fList.add(new KickersFragment());
        return fList;
    }

    @Override
    public boolean onNavigationItemSelected(int arg0, long arg1) {
        return false;
    }
}

