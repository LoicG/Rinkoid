package fr.sport.rinkoid;

import java.util.ArrayList;
import java.util.List;

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
        actionBar.setDisplayShowTitleEnabled(false);
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
        AddTab(tabHost, tabHost.newTabSpec("Tab1").setIndicator("Tab1"));
        AddTab(tabHost, tabHost.newTabSpec("Tab2").setIndicator("Tab2"));
        AddTab(tabHost, tabHost.newTabSpec("Tab3").setIndicator("Tab3"));
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
        PageFragment f1 = PageFragment.newInstance("Sample Fragment 1");
        PageFragment f2 = PageFragment.newInstance("Sample Fragment 2");
        PageFragment f3 = PageFragment.newInstance("Sample Fragment 3");
        fList.add(f1);
        fList.add(f2);
        fList.add(f3);
        return fList;
    }

    @Override
    public boolean onNavigationItemSelected(int arg0, long arg1) {
        return false;
    }
}

