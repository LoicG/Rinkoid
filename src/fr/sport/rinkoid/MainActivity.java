package fr.sport.rinkoid;

import java.util.ArrayList;

import fr.sport.rinkoid.bar.SpinnerNavItem;
import fr.sport.rinkoid.bar.TitleNavigationAdapter;

import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.TabHost;
import android.widget.TabHost.OnTabChangeListener;

public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener,
    OnTabChangeListener, OnPageChangeListener {

    private ActionBar actionBar;
    private ArrayList<SpinnerNavItem> navSpinner;
    private TitleNavigationAdapter adapter;
    PageAdapter pageAdapter;
    private ViewPager viewPager;
    private TabHost tabHost;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(getApplicationContext());

        db.Clear();
        GenerateDataTest();

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

        pageAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        viewPager.setOnPageChangeListener(this);
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

    @Override
    public boolean onNavigationItemSelected(int arg0, long arg1) {
        pageAdapter.Update(Tools.ConvertChampionship(arg0));
        return false;
    }

    private void GenerateDataTest()
    {
        db.SaveKicker("Bernard", "N1", "eag", 10);
        db.SaveKicker("Albert", "N1", "eag", 10);
        db.SaveKicker("Zorro", "N1", "lorient",23);
        db.SaveKicker("Loic", "N1", "nantes", 5);
        db.SaveKicker("Jean", "N2N", "psg", 10);
        db.SaveKicker("Pierre", "N2N", "brest", 5);
        db.SaveKicker("Jacques", "N2S", "quimper", 1);

        db.SaveRank("equipe6", "N1", 1, 5, 5, 3, 3);
        db.SaveRank("equipe1", "N1", 12, 6, 5, 1, 12);
        db.SaveRank("equipe2", "N1", 12, 5, 5, 3, 24);
        db.SaveRank("equipe3", "N1", 12, 5, 5, 3, -25);
        db.SaveRank("equipe5", "N1", 2, 5, 5, 3, 10);
        db.SaveRank("equipe4", "N1", 2, 2, 1, 3, 10);
        db.SaveRank("equipeN2N", "N2N", 2, 2, 1, 3, 10);
        db.SaveRank("equipeN2S", "N2S", 2, 2, 1, 3, 10);

        db.SaveMatch(1, "N1", "2014-02-14", "Equipe1", "2-1", "Equipe2");
        db.SaveMatch(1, "N1", "2014-02-14", "Equipe3", "5-5", "Equipe4");
        db.SaveMatch(2, "N1", "2014-02-16", "Equipe1", "1-5", "Equipe3");
        db.SaveMatch(2, "N1", "2014-02-16", "Equipe2", "", "Equipe4");
        db.SaveMatch(3, "N1", "2014-02-18", "Equipe1", "", "Equipe2");
        db.SaveMatch(3, "N1", "2014-02-18", "Equipe3", "", "Equipe4");
        db.SaveMatch(1, "N2N", "2014-02-14", "EquipeA", "1-9", "EquipeB");
        db.SaveMatch(2, "N2N", "2014-02-22", "EquipeB", "5-9", "EquipeA");
        db.SaveMatch(1, "N2S", "2014-02-17", "EquipeD", "", "EquipeC");
        // close database
    }
}

