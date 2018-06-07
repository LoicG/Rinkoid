package application.rinkoid;

import java.util.ArrayList;
import java.util.LinkedList;

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
import application.rinkoid.bar.SpinnerNavItem;
import application.rinkoid.bar.TitleNavigationAdapter;
import application.rinkoid.download.DownloadManager;
import application.rinkoid.download.InitialDownloadManager;

public class MainActivity extends FragmentActivity implements ActionBar.OnNavigationListener,
    OnTabChangeListener, OnPageChangeListener {

    private ActionBar actionBar;
    private ArrayList<SpinnerNavItem> navSpinner;
    private TitleNavigationAdapter adapter;
    PageAdapter pageAdapter;
    private ViewPager viewPager;
    private TabHost tabHost;
    private DownloadManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        DatabaseHelper db = DatabaseHelper.getInstance(getApplicationContext());

        actionBar = getActionBar();
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

        Resources ressources = getResources();
        navSpinner = new ArrayList<SpinnerNavItem>();
        navSpinner.add(new SpinnerNavItem(ressources.getString(R.string.n1)));
        navSpinner.add(new SpinnerNavItem(ressources.getString(R.string.n2)));
        adapter = new TitleNavigationAdapter(getApplicationContext(), navSpinner);
        actionBar.setListNavigationCallbacks(adapter, this);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setOffscreenPageLimit(2);
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
        Initialize(db);
    }

    private void Initialize(DatabaseHelper db) {
        LinkedList<Integer> championship = db.NeedFirstUpdate();
        if( !championship.isEmpty() ) { 
            new InitialDownloadManager(this,pageAdapter).Initialize(championship);
        }
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
}

