package application.rinkoid;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import application.rinkoid.kickers.KickersFragment;
import application.rinkoid.ranks.RanksFragment;
import application.rinkoid.shedule.ScheduleFragment;

public class PageAdapter extends FragmentPagerAdapter implements IStateChanged {
	
    private ScheduleFragment schedule;
    private RanksFragment ranks;
    private KickersFragment kickers;

    public PageAdapter(FragmentManager fm) {
        super(fm);
        schedule = new ScheduleFragment();
        ranks = new RanksFragment();
        kickers = new KickersFragment();
    }

    @Override
    public Fragment getItem(int position) {
        switch(position) {
        case Tools.SCHEDULE_PAGE:
            return schedule;
        case Tools.RANKS_PAGE:
            return ranks;
        case Tools.KICKERS_PAGE:
            return kickers;
        default:
            return null;
        }
    }

    @Override
    public int getCount() {
        return Tools.PAGES;
    }

    @Override
    public void onChampionshipChanged(int championship) {
        kickers.onChampionshipChanged(championship);
        ranks.onChampionshipChanged(championship);
        schedule.onChampionshipChanged(championship);
    }

    @Override
    public void onPageChanged(int page) {
    }

    public int getCurrentDay() {
        return schedule.getCurrentDay();
    }
}