package fr.sport.rinkoid;

import fr.sport.rinkoid.kickers.KickersFragment;
import fr.sport.rinkoid.ranks.RanksFragment;
import fr.sport.rinkoid.shedule.ScheduleFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class PageAdapter extends FragmentPagerAdapter {
	
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

    public void Update(String championship) {
        kickers.Udpate(championship);
        ranks.Udpate(championship);
        schedule.Update(championship);
    }
}