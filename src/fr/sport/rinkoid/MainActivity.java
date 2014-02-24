package fr.sport.rinkoid;

import java.util.ArrayList;

import download.DownloadManager;

import fr.sport.rinkoid.bar.SpinnerNavItem;
import fr.sport.rinkoid.bar.TitleNavigationAdapter;
import fr.sport.rinkoid.kickers.Kicker;
import fr.sport.rinkoid.ranks.Rank;
import fr.sport.rinkoid.shedule.Match;

import android.app.ActionBar;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
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
    private DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        db = new DatabaseHelper(getApplicationContext());
        db.Clear();
        GenerateDataTest();

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
        AddTab(tabHost, tabHost.newTabSpec("scheduleTab").setIndicator(
            null, ressources.getDrawable(R.drawable.ic_action_go_to_today)));
        AddTab(tabHost, tabHost.newTabSpec("rankingTab").setIndicator(
            null, ressources.getDrawable(R.drawable.ic_action_import_export)));
        AddTab(tabHost, tabHost.newTabSpec("kickersTab").setIndicator(
            null, ressources.getDrawable(R.drawable.ic_action_not_important)));
        tabHost.setOnTabChangedListener(this);

        pageAdapter = new PageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(pageAdapter);
        viewPager.setOnPageChangeListener(this);
        manager = new DownloadManager(db,pageAdapter);
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
        manager.onPageChanged(pos);
    }

    @Override
    public void onPageSelected(int arg0) {
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.update_button, menu);
        return true;
    }

    @Override
    public boolean onNavigationItemSelected(int championship, long arg1) {
        pageAdapter.onChampionshipChanged(championship);
        manager.onChampionshipChanged(championship);
        return false;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
      switch (item.getItemId()) {
      case R.id.menu_load:
        manager.Update(item);
      default:
        break;
      }
      return true;
    }

    private void GenerateDataTest() {
        ArrayList<Kicker> kickers = new ArrayList<Kicker>();
        kickers.add(new Kicker("Bernard", 10, "eag"));
        kickers.add(new Kicker("Albert", 10, "eag"));
        kickers.add(new Kicker("Zorro",23 , "lorient"));
        kickers.add(new Kicker("Loic", 5, "nantes"));
        db.SaveKicker(kickers, Tools.N1);
        kickers.clear();
        kickers.add(new Kicker("Jean", 10, "psg"));
        kickers.add(new Kicker("Pierre", 10, "brest"));
        db.SaveKicker(kickers, Tools.N2N);
        kickers.clear();
        kickers.add(new Kicker("Jacques", 10, "quimper"));
        db.SaveKicker(kickers, Tools.N2S);

        ArrayList<Rank> ranks = new ArrayList<Rank>();
        ranks.add(new Rank("equipe6", 9, 1, 5, 5, 3, 3));
        ranks.add(new Rank("equipe1", 9, 12, 6, 5, 1, 12));
        ranks.add(new Rank("equipe2", 9, 12, 5, 5, 3, 24));
        ranks.add(new Rank("equipe3", 9, 12, 5, 5, 3, -25));
        ranks.add(new Rank("equipe5", 9, 2, 5, 5, 3, 10));
        ranks.add(new Rank("equipe4", 9, 2, 2, 1, 3, 10));
        db.SaveRanks(ranks, Tools.N1);
        ranks.clear();
        ranks.add(new Rank("equipeN2N", 9, 2, 1, 3, 4, -6));
        db.SaveRanks(ranks, Tools.N2N);
        ranks.clear();
        ranks.add(new Rank("equipeN2S", 9, 32, 1, 10, 5, 96));
        db.SaveRanks(ranks, Tools.N2S);

        ArrayList<Match> matchs = new ArrayList<Match>();
        matchs.add(new Match("Equipe1", "2-1", "Equipe2", "2014-02-22"));
        matchs.add(new Match("Equipe3", "5-5", "Equipe4", "2014-02-22"));
        db.SaveMatchs(matchs, Tools.N1, 1);
        matchs.clear();
        matchs.add(new Match("Equipe1", "1-5", "Equipe3", "2014-02-22"));
        matchs.add(new Match("Equipe2", "", "Equipe4", "2014-02-22"));
        db.SaveMatchs(matchs, Tools.N1, 2);
        matchs.clear();
        matchs.add(new Match("Equipe1", "", "Equipe2", "2014-02-22"));
        matchs.add(new Match("Equipe3", "", "Equipe4", "2014-02-22"));
        db.SaveMatchs(matchs, Tools.N1, 3);
        matchs.clear();
        matchs.add(new Match("EquipeA", "1-9", "EquipeB", "2014-02-22"));
        db.SaveMatchs(matchs, Tools.N2N, 1);
        matchs.clear();
        matchs.add(new Match("EquipeB", "5-9", "EquipeA", "2014-02-22"));
        db.SaveMatchs(matchs, Tools.N2N, 2);
        matchs.clear();
        matchs.add(new Match("EquipeD", "", "EquipeC","2014-02-17"));
        db.SaveMatchs(matchs, Tools.N2S, 1);
        matchs.clear();
        // close database
    }
}

